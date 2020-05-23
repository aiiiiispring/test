package com.seeyon.apps.sygb.manager;

import com.seeyon.apps.cip.manager.RegisterManager;
import com.seeyon.apps.cip.po.RegisterPO;
import com.seeyon.apps.sygb.constants.GbEnum;
import com.seeyon.apps.sygb.dao.AuthenticationDao;
import com.seeyon.apps.sygb.po.AuthenticationPo;
import com.seeyon.apps.sygb.po.SummaryAuthenticationPo;
import com.seeyon.apps.sygb.vo.AuthenticationVo;
import com.seeyon.ctp.common.exceptions.BusinessException;
import com.seeyon.ctp.common.log.CtpLogFactory;
import com.seeyon.ctp.util.*;
import com.seeyon.ctp.util.annotation.AjaxAccess;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;

import java.util.*;

/**
 * 项目：国家博物馆客开
 * <p> Package: com.seeyon.apps.sygb.manager </p>
 * <p> File: AuthenticationManagerImpl.java </p>
 * <p> Module: 系统认证 </p>
 * <p> Description: [认证系统Manager ] </p>
 * <p> Date: 2020-4-13 16:53</p>
 *
 * @author ML
 * @version 1.0
 * @email <a href="mailto:malei12257@163.com">ML</a>
 * @date 2020-4-13 16:53
 * @since jdk1.8
 */
public class AuthenticationManagerImpl implements AuthenticationManager {
    private static Log LOG = CtpLogFactory.getLog(AuthenticationManagerImpl.class);
    private AuthenticationDao authenticationDao;
    private RegisterManager registerManager;

    public void setRegisterManager(RegisterManager registerManager) {
        this.registerManager = registerManager;
    }

    public void setAuthenticationDao(AuthenticationDao authenticationDao) {
        this.authenticationDao = authenticationDao;
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
    public FlipInfo getAuthenticationList(FlipInfo flipInfo, Map<String, Object> map)
            throws BusinessException {
        FlipInfo fiData = authenticationDao.getAuthenticationList(flipInfo, map);
        List<Map<String, Object>> data = fiData.getData();
        if (CollectionUtils.isEmpty(data)) {
            return flipInfo;
        } else {
            List<AuthenticationVo> dataList = new ArrayList();
            Iterator var6 = data.iterator();
            while (var6.hasNext()) {
                Map<String, Object> po = (Map) var6.next();
                Long registerId = NumberUtils.toLong(po.get("registerid").toString());
                Long authenticationId = NumberUtils.toLong(po.get("id").toString());
                AuthenticationVo vo = new AuthenticationVo();
                vo.setId(authenticationId);
                vo.setRegisterId(registerId);
                vo.setAppName(po.get("app_name").toString());
                vo.setAppCode(po.get("app_code").toString());
                if (po.get("product_code") != null) {
                    vo.setProductCode(po.get("product_code").toString());
                }
                if (null != po.get("url")) {
                    vo.setUrl(po.get("url").toString());
                }
                if (null != po.get("module_type")) {
                    Integer moduleType = Integer.parseInt(po.get("module_type").toString());
                    if (null != moduleType) {
                        vo.setModuleType(moduleType);
                        vo.setModuleTypeName(GbEnum.MODULE_TYPE.getValue(moduleType).getText());
                    }
                }
                if (null != po.get("way")) {
                    Integer way = Integer.parseInt(po.get("way").toString());
                    if (null != way) {
                        vo.setWay(way);
                        vo.setWayName(GbEnum.TRANSFER_METHOD_ENUM.getValue(way).getText());
                    }
                }
                if (null != po.get("is_enable")) {
                    Integer isEnable = Integer.parseInt(po.get("is_enable").toString());
                    if (null != isEnable) {
                        vo.setIsEnable(isEnable);
                        vo.setIsEnableName(GbEnum.EnableEnum.getValue(isEnable).getText());
                    }
                }
                if (null != po.get("update_time")) {
                    Date update_time = DateUtil.toDate(po.get("update_time").toString(), "yyyy-MM-dd HH:mm:ss");
                    vo.setUpdateTime(update_time);
                }
                if (null != po.get("create_time")) {
                    Date create_time = DateUtil.toDate(po.get("create_time").toString(), "yyyy-MM-dd HH:mm:ss");
                    vo.setCreateTime(create_time);
                }
                dataList.add(vo);
            }
            flipInfo.setData(dataList);
            return flipInfo;
        }
    }

    /**
     * PO 转 VO
     *
     * @param authenticationPoList
     * @return
     * @throws BusinessException
     */
    private List<AuthenticationVo> convertAuthenticationToVo(List<Map<String, Object>> authenticationPoList) throws BusinessException {
        List<Long> auIds = new ArrayList<>();
        List<AuthenticationVo> tvList = new ArrayList<>();
        if (Strings.isNotEmpty(authenticationPoList)) {
            for (Map<String, Object> map : authenticationPoList) {
                AuthenticationVo authentication = new AuthenticationVo();
                if (map.get("id") != null) {
                    Long auId = Long.valueOf(map.get("id").toString());
                    if (!auIds.contains(auId)) {
                        auIds.add(auId);
                        authentication.setId(auId);
                    } else {
                        continue;
                    }
                }
                if (map.get("way") != null) {
                    Integer way = Integer.parseInt(map.get("way").toString());
                    authentication.setWay(way);
                    authentication.setWayName(GbEnum.TRANSFER_METHOD_ENUM.getValue(way).getText());
                }
                if (map.get("isEnable") != null) {
                    Integer isEnable = Integer.parseInt(map.get("isEnable").toString());
                    authentication.setIsEnable(isEnable);
                    authentication.setIsEnableName(GbEnum.EnableEnum.getValue(isEnable).getText());
                }

                if (map.get("url") != null) {
                    authentication.setUrl(map.get("url").toString());
                }

                tvList.add(authentication);
            }
        }
        return tvList;
    }

    /**
     * 检查是否存在重复名称的PO
     *
     * @param registerId
     * @param moduleType
     * @param id
     * @param isNew
     * @return
     * @throws BusinessException
     */
    @Override
    @AjaxAccess
    public boolean getIsExistByRegisterId(String registerId, Integer moduleType, String id, boolean isNew)
            throws BusinessException {
        if (moduleType == null || Strings.isBlank(registerId)) {
            throw new BusinessException("检查是否存在重复项目时异常,参数异常，请联系系统管理员");
        }
        GbEnum.MODULE_TYPE moduleTypeEnum = GbEnum.MODULE_TYPE.getValue(moduleType);
        if (null == moduleTypeEnum) {
            throw new BusinessException("模块枚举<moduleType>异常，请联系管理员");
        }
        return authenticationDao.getIsExistByRegisterId(Long.parseLong(registerId), moduleTypeEnum, id, isNew);
    }


    @Override
    @AjaxAccess
    public void updateAuthenticationPo(Map<String, String> map) throws BusinessException {
        AuthenticationPo po = null;
        boolean isNew = false;
        if (Strings.isNotBlank(map.get("id"))) {
            po = getAuthenticationPoById(Long.parseLong(map.get("id")));
        }
        if (null == po) {
            isNew = true;
            po = new AuthenticationPo();
            po.setIdIfNew();
            po.setCreateTime(new Date());
            po.setWay(GbEnum.TRANSFER_METHOD_ENUM.JSON.getKey());
        }
        po.setRegisterId(Long.parseLong(map.get("registerId")));
        po.setUrl(map.get("url"));
        po.setModuleType(Integer.parseInt(map.get("moduleType")));
        String isEnable = map.get("isEnable");
        po.setIsEnable(Integer.valueOf(isEnable));
        po.setUpdateTime(new Date());
        if (isNew) {
            DBAgent.save(po);
        } else {
            DBAgent.update(po);
        }
    }

    /**
     * 删除
     *
     * @param ids
     * @throws BusinessException
     */
    @Override
    @AjaxAccess
    public void deleteAuthenticationPos(Long[] ids) throws BusinessException {
        List<AuthenticationPo> list = new ArrayList<>();
        for (Long id : ids) {
            AuthenticationPo po = DBAgent.get(AuthenticationPo.class, id);
            list.add(po);
        }
        DBAgent.deleteAll(list);
    }


    @Override
    @AjaxAccess
    public RegisterPO getRegisterById(String id) throws BusinessException {
        if (Strings.isBlank(id)) {
            return null;
        }
        RegisterPO register = registerManager.getRegisterById(Long.parseLong(id));
        return register;
    }


    /**
     * 根据主键，获取VO
     *
     * @param id
     * @return
     * @throws BusinessException
     */
    @Override
    @AjaxAccess
    public AuthenticationVo getAuthenticationVoById(String id) throws BusinessException {
        if (Strings.isBlank(id)) {
            return null;
        }
        AuthenticationPo po = getAuthenticationPoById(Long.parseLong(id));
        if (null != po) {
            AuthenticationVo vo = new AuthenticationVo();
            BeanUtils.convert(vo, po);
            RegisterPO register = registerManager.getRegisterById(po.getRegisterId());
            if (null == register) {
                return null;
            }
            vo.setRegisterId(register.getId());
            vo.setAppCode(String.valueOf(register.getAppCode()));
            vo.setAppName(register.getAppName());
            vo.setProductCode(register.getProductCode());
            if (null != vo.getIsEnable()) {
                vo.setIsEnableName(GbEnum.EnableEnum.getValue(vo.getIsEnable()).getText());
            }
            if (null != vo.getWay()) {
                vo.setWayName(GbEnum.TRANSFER_METHOD_ENUM.getValue(vo.getWay()).getText());
            }
            return vo;
        }
        return null;
    }

    /**
     * 根据ID获取注册系统PO
     *
     * @param id
     * @return
     * @throws BusinessException
     */
    @Override
    public AuthenticationPo getAuthenticationPoById(Long id) throws BusinessException {
        return authenticationDao.getAuthenticationPoById(id);
    }

    /**
     * 根据系统编码查询po
     *
     * @param code
     * @param moduleEnum
     * @return com.seeyon.apps.sygb.vo.AuthenticationVo
     * @throws BusinessException 异常捕获
     * @author ML
     * @version 1.0
     * @Time 2020-4-22 13:49
     */
    @Override
    public AuthenticationVo getAuthenticationVoByCodeAndModuleType(String code, GbEnum.MODULE_TYPE moduleEnum) throws BusinessException {
        if (Strings.isNotBlank(code) && null != moduleEnum) {
            AuthenticationVo vo = new AuthenticationVo();
            RegisterPO register = registerManager.getRegisterIdByCode(code);
            if (null == register) {
                return null;
            }
            vo.setRegisterId(register.getId());
            vo.setAppCode(String.valueOf(register.getAppCode()));
            vo.setAppName(register.getAppName());
            vo.setProductCode(register.getProductCode());
            AuthenticationPo po = authenticationDao.getAuthenticationPoByRegisterIdAndModuleType(register.getId(), moduleEnum.getKey());
            if (null != po) {
                BeanUtils.convert(vo, po);
                if (null != vo.getIsEnable()) {
                    vo.setIsEnableName(GbEnum.EnableEnum.getValue(vo.getIsEnable()).getText());
                }
                if (null != vo.getWay()) {
                    vo.setWayName(GbEnum.TRANSFER_METHOD_ENUM.getValue(vo.getWay()).getText());
                }
            }
            return vo;
        }
        return null;
    }

    /**
     * 根据summaryId获取注册系统关系表
     *
     * @param summaryId
     * @return com.seeyon.apps.sygb.po.SummaryAuthenticationPo
     * @throws BusinessException 异常捕获
     * @author ML
     * @version 1.0
     * @Time 2020-4-15 14:56
     */
    @Override
    public SummaryAuthenticationPo getSummaryAuthenticationPoBySummaryId(Long summaryId) throws BusinessException {
        return authenticationDao.getSummaryAuthenticationPoBySummaryId(summaryId);
    }

    /**
     * 保存公文流程与系统ID的关系，并保存第三方业务码
     *
     * @param summaryId
     * @param registerId
     * @param thirdCode
     * @throws BusinessException
     */
    @Override
    public void saveSummaryAuthentication(Long summaryId, Long registerId, String thirdCode) throws BusinessException {
        if (null == summaryId || null == registerId) {
            return;
        }
        SummaryAuthenticationPo po = new SummaryAuthenticationPo();
        po.setIdIfNew();
        po.setSummaryId(summaryId);
        po.setAuthenticationId(registerId);
        po.setThirdBusinessCode(thirdCode);
        DBAgent.save(po);
    }
}
