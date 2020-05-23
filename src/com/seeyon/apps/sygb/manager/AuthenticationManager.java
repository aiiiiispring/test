package com.seeyon.apps.sygb.manager;

import com.seeyon.apps.cip.po.RegisterPO;
import com.seeyon.apps.sygb.constants.GbEnum;
import com.seeyon.apps.sygb.po.AuthenticationPo;
import com.seeyon.apps.sygb.po.SummaryAuthenticationPo;
import com.seeyon.apps.sygb.vo.AuthenticationVo;
import com.seeyon.ctp.common.exceptions.BusinessException;
import com.seeyon.ctp.util.FlipInfo;
import com.seeyon.ctp.util.annotation.AjaxAccess;

import java.util.Map;

/**
 * 项目：国家博物馆客开
 * <p> Package: com.seeyon.apps.sygb.manager </p>
 * <p> File: AuthenticationManager.java </p>
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

public interface AuthenticationManager {

    /**
     * 获取认证系统数据
     *
     * @param flipInfo
     * @param map
     * @return
     * @throws BusinessException
     */
    FlipInfo getAuthenticationList(FlipInfo flipInfo, Map<String, Object> map) throws BusinessException;


    /**
     * 修改PO
     *
     * @param map
     * @throws BusinessException
     */
    void updateAuthenticationPo(Map<String, String> map) throws BusinessException;

    /**
     * 删除PO
     *
     * @param ids
     * @throws BusinessException
     */
    void deleteAuthenticationPos(Long[] ids) throws BusinessException;

    @AjaxAccess
    RegisterPO getRegisterById(String id) throws BusinessException;

    /**
     * 根据主键，获取VO
     *
     * @param id
     * @return
     * @throws BusinessException
     */
    AuthenticationVo getAuthenticationVoById(String id) throws BusinessException;

    /**
     * 根据ID获取注册系统PO
     *
     * @param id
     * @return
     * @throws BusinessException
     */
    AuthenticationPo getAuthenticationPoById(Long id) throws BusinessException;

    /**
     * 检查是否存在重名的PO
     *
     * @param registerId
     * @param id
     * @param isNew
     * @return
     * @throws BusinessException
     */
    boolean getIsExistByRegisterId(String registerId, Integer moduleType, String id, boolean isNew) throws BusinessException;

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
    AuthenticationVo getAuthenticationVoByCodeAndModuleType(String code, GbEnum.MODULE_TYPE moduleEnum) throws BusinessException;

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
    SummaryAuthenticationPo getSummaryAuthenticationPoBySummaryId(Long summaryId) throws BusinessException;

    /**
     * 保存公文流程与系统ID的关系，并保存第三方业务码
     *
     * @param summaryId
     * @param registerId
     * @param thirdCode
     * @throws BusinessException
     */
    void saveSummaryAuthentication(Long summaryId, Long registerId, String thirdCode) throws BusinessException;
}
