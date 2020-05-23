package com.seeyon.apps.sygb.util;

import com.kg.commons.utils.CollectionUtils;
import com.seeyon.apps.sygb.vo.GbMessageVo;
import com.seeyon.ctp.util.Strings;

import java.util.LinkedHashMap;

public class GbMessageUtil {

    /**
     * 消息数据实体封装方法
     *
     * @param map
     * @return
     */
    public GbMessageVo createMessageInfo(LinkedHashMap<String, Object> map) {
        GbMessageVo instance = new GbMessageVo();
        if (!CollectionUtils.isEmpty(map)) {
            if (map.get("messageid") != null) {
                instance.setMessageId(map.get("messageid").toString());
            }
            if (map.get("title") != null) {
                instance.setTitle(map.get("title").toString());
            }
            if (map.get("senderName") != null) {
                instance.setSenderName(map.get("senderName").toString());
            }
            if (map.get("creation_date") != null) {
                instance.setCreation_date(map.get("creation_date").toString());
            }
            if (map.get("appName") != null && !Strings.isBlank(map.get("appName").toString())) {
                instance.setAppName(map.get("appName").toString());
            }
            if (map.get("messageurl") != null && !Strings.isBlank(map.get("messageurl").toString())) {
                instance.setMessageURL(map.get("messageurl").toString());
            } else {
                instance.setMessageURL(" ");
            }
            if (map.get("messageh5url") != null && !Strings.isBlank(map.get("messageh5url").toString())) {
                instance.setMessageH5URL(map.get("messageh5url").toString());
            } else {
                instance.setMessageH5URL(" ");
            }
        }
        return instance;
    }
}
