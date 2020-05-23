package com.seeyon.apps.sygb.dao;

import com.seeyon.ctp.util.FlipInfo;

import java.util.Map;

/**
 * 获取消息推送数据
 */
public interface GbMessageDao {
    FlipInfo getMessage(Integer pageNum, Integer pageSize, Long memberId, Map<String, Object> params);
}
