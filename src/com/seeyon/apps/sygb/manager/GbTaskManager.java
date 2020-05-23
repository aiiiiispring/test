package com.seeyon.apps.sygb.manager;

import java.util.HashMap;
import java.util.Map;

/**
 * 获取待办已办任务
 */
public interface GbTaskManager {

     HashMap<String,Object> getTaskInformation(Map<String, Object> map, Long memberId, String thirdPartyState, String affairState);
}
