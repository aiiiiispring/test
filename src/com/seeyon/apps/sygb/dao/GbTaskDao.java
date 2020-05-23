package com.seeyon.apps.sygb.dao;

import com.seeyon.ctp.util.FlipInfo;

/**
 * 获取任务
 */
public interface GbTaskDao {
    FlipInfo getTaskInformation(Integer pageNum, Integer pageSize, String receiverAccount, Long memberId, String thirdPartyState, String affairState);
}
