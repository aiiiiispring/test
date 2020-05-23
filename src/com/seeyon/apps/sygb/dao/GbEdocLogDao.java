package com.seeyon.apps.sygb.dao;

import com.seeyon.apps.sygb.po.GbEdocLogPo;
import com.seeyon.ctp.common.exceptions.BusinessException;
import com.seeyon.ctp.util.FlipInfo;

import java.util.List;
import java.util.Map;

/**
 * 项目：国家博物馆客开
 * <p> Package: com.seeyon.apps.sygb.dao </p>
 * <p> File: GbEdocLogDao.java </p>
 * <p> Module: 日志记录 </p>
 * <p> Date: 2020-4-13 15:46</p>
 *
 * @author ML
 * @version 1.0
 * @email <a href="mailto:malei12257@163.com">ML</a>
 * @date 2020-4-13 15:46
 * @since jdk1.8
 */
public interface GbEdocLogDao {

    /**
     * 获取国博日志列表
     *
     * @return
     * @throws BusinessException
     */
    List<Map<String, Object>> getGbEdocLogList(FlipInfo flipInfo, Map<String, Object> map) throws BusinessException;

    /**
     * 根据ID获取PO
     *
     * @param id
     * @return
     * @throws BusinessException
     */
    GbEdocLogPo getEdocLogPoById(Long id) throws BusinessException;
}
