package com.seeyon.apps.sygb.dao;

import com.seeyon.apps.sygb.constants.GbEnum;
import com.seeyon.apps.sygb.po.AuthenticationPo;
import com.seeyon.apps.sygb.po.SummaryAuthenticationPo;
import com.seeyon.ctp.common.exceptions.BusinessException;
import com.seeyon.ctp.util.FlipInfo;

import java.util.Map;

/**
 * 项目：国家博物馆客开
 * <p> Package: com.seeyon.apps.sygb.dao </p>
 * <p> File: AuthenticationDao.java </p>
 * <p> Module: 系统认证注册 </p>
 * <p> Description: 系统认证注册数据持久层dao </p>
 * <p> Date: 2020-4-13 15:46</p>
 *
 * @author ML
 * @version 1.0
 * @email <a href="mailto:malei12257@163.com">ML</a>
 * @date 2020-4-13 15:46
 * @since jdk1.8
 */
public interface AuthenticationDao {

    /**
     * 获取认证系统列表
     *
     * @return
     * @throws BusinessException
     */
    FlipInfo getAuthenticationList(FlipInfo flipInfo, Map<String, Object> map) throws BusinessException;

    /**
     * 检查是否存在重名的系统PO
     *
     * @param registerId 注册系统ID
     * @param moduleType 模块类型
     * @param id         注册ID
     * @param isNew      是否新增的
     * @return
     * @throws BusinessException
     */
    boolean getIsExistByRegisterId(Long registerId, GbEnum.MODULE_TYPE moduleType, String id, boolean isNew) throws BusinessException;

    /**
     * 根据id获取注册系统数据
     *
     * @param id
     * @return com.seeyon.apps.sygb.po.AuthenticationPo
     * @throws BusinessException 异常捕获
     * @author ML
     * @version 1.0
     * @Time 2020-4-15 14:50
     */
    AuthenticationPo getAuthenticationPoById(Long id) throws BusinessException;

    /**
     * 根据注册ID和模块类型获取系统信息
     *
     * @param registerId CIP注册ID
     * @param moduleType 模块类型
     * @return com.seeyon.apps.sygb.po.AuthenticationPo
     * @throws BusinessException 异常捕获
     * @author ML
     * @version 1.0
     * @Time 2020-4-21 18:43
     */
    AuthenticationPo getAuthenticationPoByRegisterIdAndModuleType(Long registerId, Integer moduleType) throws BusinessException;

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
}
