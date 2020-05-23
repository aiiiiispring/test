package com.seeyon.ctp.rest.resources;

import com.seeyon.apps.govdoc.bo.SendGovdocResult;
import com.seeyon.apps.sygb.bo.GbBpDataBo;
import com.seeyon.apps.sygb.bo.GbBpResultDataBo;
import com.seeyon.apps.sygb.bo.GbFileBo;
import com.seeyon.apps.sygb.bo.GbFileIdBo;
import com.seeyon.apps.sygb.constants.GbEnum;
import com.seeyon.apps.sygb.manager.AuthenticationManager;
import com.seeyon.apps.sygb.manager.GbDocManager;
import com.seeyon.apps.sygb.manager.GbEdocLogManager;
import com.seeyon.apps.sygb.mapping.GbFieldMapping;
import com.seeyon.apps.sygb.util.GbUtils;
import com.seeyon.apps.sygb.util.ThreadTaskUtils;
import com.seeyon.apps.sygb.vo.AuthenticationVo;
import com.seeyon.ctp.common.AppContext;
import com.seeyon.ctp.common.exceptions.BusinessException;
import com.seeyon.ctp.organization.bo.V3xOrgMember;
import com.seeyon.ctp.organization.manager.OrgManager;
import com.seeyon.ctp.util.DateUtil;
import com.seeyon.ctp.util.Strings;
import com.seeyon.ctp.util.UUIDLong;
import com.seeyon.ctp.util.json.JSONUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/***
 * 项目：国家博物馆客开
 * <p> Package: com.seeyon.ctp.rest.resources </p>
 * <p> File: GbResource.java </p>
 * <p> Module: 公文发起接口 </p>
 * <p> Description: [定制化接口类] </p>
 * <p> Date: 2020-3-30 13:51</p>
 * @author ML
 * @email <a href="mailto:malei12257@163.com">ML</a>
 * @date 2020-3-30 13:51
 * @since jdk1.8
 * @version 1.0
 */
@Path("guobo")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class GbDocResource extends BaseResource {
    private final static Log logger = LogFactory.getLog(GbDocResource.class);

    private GbDocManager gbDocManager = (GbDocManager) AppContext.getBean("gbDocManager");
    private OrgManager orgManager = (OrgManager) AppContext.getBean("orgManager");
    private GbEdocLogManager gbEdocLogManager = (GbEdocLogManager) AppContext.getBean("gbEdocLogManager");
    private AuthenticationManager authenticationManager = (AuthenticationManager) AppContext.getBean("authenticationManager");
    /**
     * 发起报批件公文流程接口
     *
     * @param jsonData 所有数据
     * @return javax.ws.rs.core.Response
     * @throws BusinessException 异常捕获
     * @author ML
     * @version 1.0
     * @Time 2020-3-30 13:53
     */
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/bpj/sendEdoc")
    public Response sendEdoc(String jsonData) throws BusinessException {
        //主体数据
        GbBpResultDataBo gbBpResultDataBo;
        try {
            gbBpResultDataBo = JSONUtil.parseJSONString(jsonData, GbBpResultDataBo.class);
        }catch (Exception e){
            return fail("JSON数据格式转换错误，检查传输内容！");
        }
        //校验传参是否合法
        //其他系统定制参数写入
        String systemCode = gbBpResultDataBo.getSystemCode();
        if (Strings.isBlank(systemCode)) {
            return fail("系统编码（systemCode）不能为空！");
        }
        String thirdBusinessCode = gbBpResultDataBo.getThirdBusinessCode();
        // 检查系统编码是否在平台系统注册，校验此系统编码是否有权限调用此接口：如无权限直接返回
        AuthenticationVo authenticationVo = authenticationManager.getAuthenticationVoByCodeAndModuleType(systemCode, GbEnum.MODULE_TYPE.BP_EDOC);
        if (null == authenticationVo){
            return fail("系统编码（systemCode）未在综合工作平台系统注册！请联系综合工作平台管理员进行注册后重试！");
        }
        //获取发起公文所需参数
        GbBpDataBo gbBpDataBo = gbBpResultDataBo.getData();
        if (null == gbBpDataBo){
            return fail("发起公文的相关数据（data）不能为空！");
        }
        //发起者
        String drafterMemberLoginName = gbBpDataBo.getDrafterMember();
        if (Strings.isEmpty(drafterMemberLoginName)) {
            return fail("起草人（drafterMember）账号不能为空！");
        }
        String title = gbBpDataBo.getTitle();
        if (Strings.isBlank(title)) {
            return fail("标题（title）不能为空！");
        }
        V3xOrgMember member;
        try {
            member = orgManager.getMemberByLoginName(drafterMemberLoginName);
        } catch (Exception e) {
            return fail("起草人（drafterMember）查询失败：" + e.getMessage());
        }
        if (member == null) {
            return fail("起草人（drafterMember）不存在！");
        }
        gbBpDataBo.setDrafterMember(member.getId().toString());
        gbBpDataBo.setDrafterDept(member.getOrgDepartmentId().toString());
        //设置报批附件关联ID
        Long subReferenceId = UUIDLong.longUUID();
        gbBpDataBo.setApprovalFileId(subReferenceId.toString());
        // 组装发起公文所需要的参数格式
        Map<String, Object> params = new HashMap<>();
        params.put("transfertype", "json");
        params.put("senderLoginName", member.getLoginName());
        //表单参数
        params.put("data", GbUtils.fillBillData(gbBpDataBo, GbFieldMapping.gb_bp_map));
        // 获取附件信息
        List<GbFileBo> fileBos = gbBpDataBo.getFiles();
        // 1.从FTP下载文件
        GbFileIdBo gbFileIdBo = null;
        try {
            gbFileIdBo = gbDocManager.downloadFileByFtp(fileBos);
        } catch (BusinessException e) {
            logger.error("从FTP下载文件失败：" + e.getMessage());
            return fail("从FTP下载文件失败：" + e.getMessage());
        }
        // 2.发起公文流程
        SendGovdocResult result;
        try {
            Long contentFileId = null;
            if (null != gbFileIdBo && null != gbFileIdBo.getContentFileId()) {
                contentFileId = gbFileIdBo.getContentFileId();
            }
            result = gbDocManager.sendEDoc(GbFieldMapping.GB_BP_TEMPLATE_CODE, params, contentFileId,gbBpDataBo.getContent());
        } catch (BusinessException e) {
            logger.error("发起国博报批单流程失败：" + e.getMessage());
            return fail("发起国博报批单流程失败：" + e.getMessage());
        }
        Long summaryId = result.getSummary().getId();
        // 3.附件处理
        try {
            gbDocManager.addEDocAttachment(result.getSummary().getId(), subReferenceId, gbFileIdBo);
        } catch (BusinessException e) {
            logger.error("发起国博报批单，附件处理失败：" + e.getMessage(), e);
        }

        // 保存系统与当前公文流程的关系
        authenticationManager.saveSummaryAuthentication(summaryId,authenticationVo.getId(),thirdBusinessCode);
        // 记录发起公文日志接口，查询第三方系统注册信息，以及是否发起成功的结果。
        StringBuffer requestURL = AppContext.getRawRequest().getRequestURL();
        if (summaryId != null){
            ThreadTaskUtils.run(() -> {
                try {
                    gbEdocLogManager.saveGbLog(authenticationVo.getRegisterId(),member.getId(),requestURL.toString(), GbEnum.MODULE_TYPE.BP_EDOC, GbEnum.ACTION_TYPE.IN, GbEnum.HANDLE_RESULT.SUCCESS,"",jsonData);
                } catch (BusinessException e) {
                    logger.error("===国博发起报批件记录日志失败："+e.getMessage());
                }
            });
        }
        // 返回结果处理
        Map<String, Object> res = new HashMap<>(3);
        res.put("summaryId", summaryId);
        res.put("title", gbBpDataBo.getTitle());
        res.put("sendTime", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        return success(res, "发起报批件公文流程成功！");
    }
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/test/edoc")
    public Response edoc(String jsonData) throws BusinessException {
        System.out.println("===接收值："+jsonData);
        // 返回结果处理
        return fail("处理失败失败");
    }


}
