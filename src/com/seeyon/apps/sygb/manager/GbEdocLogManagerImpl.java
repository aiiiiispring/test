package com.seeyon.apps.sygb.manager;

import com.seeyon.apps.cip.manager.RegisterManager;
import com.seeyon.apps.cip.po.RegisterPO;
import com.seeyon.apps.sygb.constants.GbEnum;
import com.seeyon.apps.sygb.dao.GbEdocLogDao;
import com.seeyon.apps.sygb.po.GbEdocLogPo;
import com.seeyon.apps.sygb.vo.GbEdocLogVo;
import com.seeyon.ctp.common.AppContext;
import com.seeyon.ctp.common.exceptions.BusinessException;
import com.seeyon.ctp.common.log.CtpLogFactory;
import com.seeyon.ctp.organization.bo.V3xOrgMember;
import com.seeyon.ctp.organization.manager.OrgManager;
import com.seeyon.ctp.util.BeanUtils;
import com.seeyon.ctp.util.DBAgent;
import com.seeyon.ctp.util.FlipInfo;
import com.seeyon.ctp.util.Strings;
import com.seeyon.ctp.util.annotation.AjaxAccess;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 项目：国家博物馆客开
 * <p> Package: com.seeyon.apps.sygb.manager </p>
 * <p> File: AuthenticationManagerImpl.java </p>
 * <p> Module: 日志记录 </p>
 * <p> Date: 2020-4-13 16:53</p>
 *
 * @author ML
 * @version 1.0
 * @email <a href="mailto:malei12257@163.com">ML</a>
 * @date 2020-4-13 16:53
 * @since jdk1.8
 */
public class GbEdocLogManagerImpl implements GbEdocLogManager {
    private static Log LOG = CtpLogFactory.getLog(GbEdocLogManagerImpl.class);
    private GbEdocLogDao gbEdocLogDao;
    private OrgManager orgManager;

    public void setOrgManager(OrgManager orgManager) {
        this.orgManager = orgManager;
    }

    public void setGbEdocLogDao(GbEdocLogDao gbEdocLogDao) {
        this.gbEdocLogDao = gbEdocLogDao;
    }

    /**
     * 查询接入系统地址
     *
     * @param flipInfo
     * @param map
     * @return
     * @throws BusinessException
     */
    @Override
    @AjaxAccess
    public FlipInfo getGbLogList(FlipInfo flipInfo, Map<String, Object> map)
            throws BusinessException {
        List<Map<String, Object>> fiData = gbEdocLogDao.getGbEdocLogList(flipInfo, map);
        if (CollectionUtils.isEmpty(fiData)) {
            return flipInfo;
        } else {
            List<GbEdocLogVo> gbEdocLogVos = this.convertEdocLogToVo(fiData);
            flipInfo.setData(gbEdocLogVos);
            return flipInfo;
        }
    }

    /**
     * PO 转 VO
     *
     * @param logList
     * @return
     * @throws BusinessException
     */
    private List<GbEdocLogVo> convertEdocLogToVo(List<Map<String, Object>> logList) throws BusinessException {
        List<Long> auIds = new ArrayList<>();
        List<GbEdocLogVo> gbEdocLogVos = new ArrayList<>();
        if (Strings.isNotEmpty(logList)) {
            for (Map<String, Object> map : logList) {
                GbEdocLogVo gbEdocLogVo = new GbEdocLogVo();
                if (map.get("id") != null) {
                    Long auId = Long.valueOf(map.get("id").toString());
                    if (!auIds.contains(auId)) {
                        auIds.add(auId);
                        gbEdocLogVo.setId(auId);
                    } else {
                        continue;
                    }
                }
                if (map.get("memberId") != null) {
                    String memberId = map.get("memberId").toString();
                    V3xOrgMember member = orgManager.getMemberById(Long.parseLong(memberId));
                    if (member != null){
                        gbEdocLogVo.setMemberId(Long.parseLong(memberId));
                        gbEdocLogVo.setMemberName(member.getName());
                    }
                }
                if (map.get("actionType") != null) {
                    Integer actionType = Integer.parseInt(map.get("actionType").toString());
                    gbEdocLogVo.setActionType(actionType);
                    gbEdocLogVo.setActionName(GbEnum.ACTION_TYPE.getValue(actionType).getText());
                }
                if (map.get("moduleType") != null) {
                    Integer moduleType = Integer.parseInt(map.get("moduleType").toString());
                    gbEdocLogVo.setModuleType(moduleType);
                    gbEdocLogVo.setModuleTypeName(GbEnum.MODULE_TYPE.getValue(moduleType).getText());
                }
                if (map.get("resultType") != null) {
                    Integer resultType = Integer.parseInt(map.get("resultType").toString());
                    gbEdocLogVo.setResultType(resultType);
                    gbEdocLogVo.setResultTypeName(GbEnum.HANDLE_RESULT.getValue(resultType).getText());
                }
                if (map.get("resultInfo") != null) {
                    gbEdocLogVo.setResultInfo(map.get("resultInfo").toString());
                }
                if (map.get("url") != null) {
                    gbEdocLogVo.setUrl(map.get("url").toString());
                }
                if (map.get("systemCode") != null) {
                    gbEdocLogVo.setSystemCode(map.get("systemCode").toString());
                }
                if (map.get("systemName") != null) {
                    gbEdocLogVo.setSystemName(map.get("systemName").toString());
                }
                if(map.get("createTime") != null){
                    Timestamp ts = (Timestamp) map.get("createTime");
                    gbEdocLogVo.setCreateTime(new Date(ts.getTime()));
                }
                gbEdocLogVos.add(gbEdocLogVo);
            }
        }
        return gbEdocLogVos;
    }


    /**
     * 保存国博日志记录
     * @param registerId 系统注册ID
     * @param memberId 人员id
     * @param url 接口地址
     * @param moduleTypeEnum 模块类型
     * @param actionTypeEnum 动作
     * @param handleResultEnum 处理结果类型
     * @param resultInfo 处理结果详情
     * @param dataJson json数据串
     * @return void
     * @throws BusinessException 异常捕获
     * @author ML
     * @version 1.0
     * @Time 2020-4-23 18:36
     */
    @Override
    public void saveGbLog(Long registerId,
                          Long memberId,
                          String url,
                          GbEnum.MODULE_TYPE moduleTypeEnum,
                          GbEnum.ACTION_TYPE actionTypeEnum,
                          GbEnum.HANDLE_RESULT handleResultEnum,
                          String resultInfo,
                          String dataJson) throws BusinessException {
        if (null == registerId || memberId == null || null == moduleTypeEnum || null == actionTypeEnum || null == handleResultEnum) {
            return;
        }
        RegisterManager registerManager = (RegisterManager) AppContext.getBean("registerManager");
        RegisterPO registerPO = registerManager.getRegisterById(registerId);
        if (null == registerPO){
            return;
        }
        GbEdocLogPo po = new GbEdocLogPo();
        po.setIdIfNew();
        po.setSystemCode(String.valueOf(registerPO.getAppCode()));
        po.setSystemName(registerPO.getAppName());
        po.setActionType(actionTypeEnum.getKey());
        po.setModuleType(moduleTypeEnum.getKey());
        po.setResultType(handleResultEnum.getKey());
        po.setResultInfo(resultInfo);
        po.setMemberId(memberId);
        po.setCreateTime(new Date());
        po.setDataJson(dataJson);
        po.setUrl(url);
        DBAgent.save(po);
    }


    /**
     * 根据ID获取PO
     *
     * @param id
     * @return
     * @throws BusinessException
     */
    @Override
    public GbEdocLogPo getEdocLogPoById(Long id) throws BusinessException {
        return gbEdocLogDao.getEdocLogPoById(id);
    }


    /**
     * 根据ID获取日志VO
     * @param id
     * @return
     * @throws BusinessException
     */
    @Override
    @AjaxAccess
    public GbEdocLogVo getEdocLogVoById(Long id) throws BusinessException {
        GbEdocLogPo edocLogPo = gbEdocLogDao.getEdocLogPoById(id);
        if (edocLogPo == null){
            return null;
        }
        GbEdocLogVo gbEdocLogVo = new GbEdocLogVo();
        BeanUtils.convert(gbEdocLogVo,edocLogPo);
        if (edocLogPo.getMemberId() != null) {
            Long memberId = edocLogPo.getMemberId();
            V3xOrgMember member = orgManager.getMemberById(memberId);
            if (member != null){
                gbEdocLogVo.setMemberName(member.getName());
            }
        }
        if (edocLogPo.getActionType() != null) {
            Integer actionType = edocLogPo.getActionType();
            gbEdocLogVo.setActionName(GbEnum.ACTION_TYPE.getValue(actionType).getText());
        }
        if (edocLogPo.getModuleType() != null) {
            Integer moduleType = edocLogPo.getModuleType();
            gbEdocLogVo.setModuleTypeName(GbEnum.MODULE_TYPE.getValue(moduleType).getText());
        }
        if (edocLogPo.getResultType() != null) {
            Integer resultType = edocLogPo.getResultType() ;
            gbEdocLogVo.setResultTypeName(GbEnum.HANDLE_RESULT.getValue(resultType).getText());
        }
        return gbEdocLogVo;
    }
}
