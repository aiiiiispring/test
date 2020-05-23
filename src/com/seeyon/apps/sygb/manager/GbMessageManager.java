package com.seeyon.apps.sygb.manager;

import com.seeyon.ctp.common.exceptions.BusinessException;

import java.util.HashMap;
import java.util.Map;

public interface GbMessageManager {

    HashMap<String, Object> getMessage(Map<String, Object> params, Long memberId) throws BusinessException;
}
