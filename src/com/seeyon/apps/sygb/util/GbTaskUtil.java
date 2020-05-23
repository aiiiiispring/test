package com.seeyon.apps.sygb.util;

import com.seeyon.apps.sygb.vo.GbTaskVo;
import com.seeyon.ctp.common.AppContext;
import java.util.Map;

/**
 * 封装任务类对象
 *
 * @param
 *
 * @return 任务类对象
 */
public class GbTaskUtil {
    private static final String URL = AppContext.getSystemProperty("internet.site.url");
    public GbTaskVo cteateTaskInfo(Map<String, Object> data, String receiverAccount) {
        GbTaskVo taskInfo = new GbTaskVo();
        if (data.get("app_name") != null && data.get("app_name") != "") {
            taskInfo.setAppName(data.get("app_name").toString());
        } else {
            taskInfo.setAppName("综合工作平台");
        }
        if (data.get("create_date") != null && data.get("create_date") != "") {
            taskInfo.setCreateDate(data.get("create_date").toString());
        }
        if (data.get("h5url") != null && data.get("h5url") != "") {
            taskInfo.setH5Url(data.get("h5url").toString());
        } else {
            taskInfo.setH5Url("");
        }
        if (data.get("id") != null && data.get("id") != "") {
            taskInfo.setId(Long.parseLong(data.get("id").toString()));
        }
        // PCurl 不是空，那就直接拿第三方的处理路径
        if (data.get("pcurl") != null && data.get("pcurl") != "") {
            taskInfo.setPcUrl(data.get("pcurl").toString());
        } else {
            // 1.判断APP 是什么？判断非空
            //设置路径
            String affairId = data.get("id").toString();
            if (null != data.get("app")) {
                String registercode = data.get("app").toString();
                String ticket = TaskBusinessUtil.encode(receiverAccount, affairId, registercode);
                StringBuilder appendUrl = new StringBuilder(URL);
                appendUrl
                        .append("/seeyon/login/sso?from=guobo&ticket=")
                        .append(ticket)
                        .append("&tourl=/seeyon/colsso.jsp");
                taskInfo.setPcUrl(String.valueOf(appendUrl));
            }
        }
        if (data.get("title") != null && data.get("title") != "") {
            taskInfo.setTitle(data.get("title").toString());
        }
        if (data.get("sender_id") != null && data.get("sender_id") != "") {
            taskInfo.setSenderId(data.get("sender_id").toString());
        }
        if (data.get("sender_name") != null && data.get("sender_name") != "") {
            taskInfo.setSenderName(data.get("sender_name").toString());
        } else {
            taskInfo.setSenderName("");
        }
        return taskInfo;
    }
}
