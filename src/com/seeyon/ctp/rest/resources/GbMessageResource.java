package com.seeyon.ctp.rest.resources;


import com.seeyon.apps.sygb.manager.GbMessageManager;
import com.seeyon.ctp.common.AppContext;
import com.seeyon.ctp.common.exceptions.BusinessException;
import com.seeyon.ctp.organization.bo.V3xOrgMember;
import com.seeyon.ctp.organization.manager.OrgManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Title: 推送综合平台消息</p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: seeyon.com</p>
 */
@Consumes({"application/json", "application/xml"})
@Produces({"application/json"})
@Path("/guobo/message")
public class GbMessageResource extends BaseResource {
    /**
     * 组织架构基础接口
     */
    private OrgManager orgManager = (OrgManager) AppContext.getBean("orgManager");
    private GbMessageManager gbGetMessageManager = (GbMessageManager) AppContext.getBean("gbMessageManager");

    /**
     * 分页查询推送消息
     * @param params receiverAccount（用户名），pageNum（当前页），pageSize（每页最大条数），isRead(是否已读)
     * @return  map集合
     * @throws BusinessException
     */
    @GET
    @Path("/getPageMessage")
    public Response getPageMessage(Map<String, Object> params) throws BusinessException {
        //判断是否传参
        if (params != null) {
            //判断登录名是否正确
            Object thirdLoginName = params.get("receiverAccount");
            if (null == thirdLoginName || String.valueOf(thirdLoginName).isEmpty()) {
                return fail("输入的登录名信息有误，请核实后查询");
            }
            //根据登录名查询memberId
            if (null == orgManager) {
                orgManager = (OrgManager) AppContext.getBean("orgManager");
            }
            V3xOrgMember receiverMember = orgManager.getMemberByLoginName(thirdLoginName.toString());
            if (null == receiverMember) {
                return fail("输入的登录名信息有误，请核实后查询");
            }
            Long memberId = receiverMember.getId();
            //调用manaegr层处理业务
            HashMap<String, Object> message = gbGetMessageManager.getMessage(params, memberId);
            return success(message);
        }
            return fail("所传参数不能为空");
    }
}

