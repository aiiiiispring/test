package com.seeyon.apps.sygb.manager;

import com.seeyon.apps.sygb.constants.GbEnum;
import com.seeyon.apps.sygb.po.GbEdocLogPo;
import com.seeyon.apps.sygb.vo.GbEdocLogVo;
import com.seeyon.ctp.common.exceptions.BusinessException;
import com.seeyon.ctp.util.FlipInfo;

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

public interface GbEdocLogManager {

    /**
     * 获取日志记录数据
     *
     * @param flipInfo
     * @param map
     * @return
     * @throws BusinessException
     */
    FlipInfo getGbLogList(FlipInfo flipInfo, Map<String, Object> map) throws BusinessException;

    /**
     * 保存国博日志记录
     *
     * @param registerId       系统注册ID
     * @param memberId         人员id
     * @param url              接口地址
     * @param moduleTypeEnum   模块类型
     * @param actionTypeEnum   动作
     * @param handleResultEnum 处理结果类型
     * @param resultInfo       处理结果详情
     * @param dataJson         json数据串
     * @return void
     * @throws BusinessException 异常捕获
     * @author ML
     * @version 1.0
     * @Time 2020-4-23 18:36
     */
    void saveGbLog(Long registerId,
                   Long memberId,
                   String url,
                   GbEnum.MODULE_TYPE moduleTypeEnum,
                   GbEnum.ACTION_TYPE actionTypeEnum,
                   GbEnum.HANDLE_RESULT handleResultEnum,
                   String resultInfo,
                   String dataJson) throws BusinessException;

    /**
     * 根据ID获取PO
     *
     * @param id
     * @return
     * @throws BusinessException
     */
    GbEdocLogPo getEdocLogPoById(Long id) throws BusinessException;

    /**
     * 根据ID获取日志VO
     *
     * @param id
     * @return
     * @throws BusinessException
     */
    GbEdocLogVo getEdocLogVoById(Long id) throws BusinessException;
}
