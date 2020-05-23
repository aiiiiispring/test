package com.seeyon.apps.sygb.manager;

import com.kg.commons.utils.CollectionUtils;
import com.seeyon.apps.cip.manager.RegisterManager;
import com.seeyon.apps.cip.po.RegisterPO;
import com.seeyon.apps.sygb.dao.GbMessageDao;
import com.seeyon.apps.sygb.util.GbMessageUtil;
import com.seeyon.apps.sygb.util.TaskBusinessUtil;
import com.seeyon.apps.sygb.vo.GbMessageVo;
import com.seeyon.ctp.common.AppContext;
import com.seeyon.ctp.common.exceptions.BusinessException;
import com.seeyon.ctp.organization.bo.V3xOrgMember;
import com.seeyon.ctp.organization.manager.OrgManager;
import com.seeyon.ctp.util.FlipInfo;
import com.seeyon.ctp.util.Strings;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 获取消息推送业务实现
 */
public class GbMessageManagerImpl implements GbMessageManager {
    //设置默认的当前页数和条数
    private static final Integer DEFAULT_PAGE_NUM = 1;
    private static final Integer DEFAULT_PAGE_MAX_SIZE = 20;
    //获取当前ip地址
    private static final String URL = AppContext.getSystemProperty("internet.site.url");
    private OrgManager orgManager = (OrgManager) AppContext.getBean("orgManager");
    private RegisterManager registerManager = (RegisterManager) AppContext.getBean("registerManager");
    private GbMessageDao gbGetMessageDao = (GbMessageDao) AppContext.getBean("gbMessageDao");
    /**
     * 消息推送业务实现
     */
    @Override
    public   HashMap<String, Object> getMessage(Map<String, Object> params, Long memberId) throws BusinessException {

        //获取当前页码，没有传递当前页码设置默认值：1。
        Integer pageNum = DEFAULT_PAGE_NUM;
        if (params.get("pageNum") != null && !Strings.isBlank(params.get("pageNum").toString())) {
            pageNum = Integer.parseInt(String.valueOf(params.get("pageNum")));
        }
        //获取分页最大数据条数，没有传递参数，设置默认值：20
        Integer pageSize = DEFAULT_PAGE_MAX_SIZE;
        if (params.get("pageSize") != null && !Strings.isBlank(params.get("pageSize").toString())) {
            pageSize = Integer.parseInt(String.valueOf(params.get("pageSize")));
        }
        FlipInfo result = gbGetMessageDao.getMessage(pageNum, pageSize, memberId, params);
        List<LinkedHashMap<String, Object>> dataList = new ArrayList<LinkedHashMap<String, Object>>();
        if (result != null) {
            dataList = result.getData();
        }
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        ArrayList<GbMessageVo> messageList = new ArrayList<GbMessageVo>(pageSize);
        // 数据转换格式
        if (!CollectionUtils.isEmpty(dataList)) {
            for (LinkedHashMap<String, Object> data : dataList) {
                if (!CollectionUtils.isEmpty(data)) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    if (data.get("creation_date") != null) {
                        data.put("creation_date", dateFormat.format(data.get("creation_date")));
                    }
                    Long senderId = null;
                    if (data.get("senderid") != null) {
                        senderId = Long.parseLong(data.get("senderid").toString());
                    }
                    String registerCode = null;
                    if (data.get("thirdpartyregistercode") != null) {
                        registerCode = String.valueOf(data.get("thirdpartyregistercode"));
                    }
                    //判断是否为第三方注册应用,综合平台待办进行url拼接
                    RegisterPO register = registerManager.getRegisterIdByCode(registerCode);
                    if (register != null) {
                        String appName = register.getAppName();
                        data.put("appName", appName);
                    } else {
                        //针对综合平台的，消息待办跳转url进行拼接
                        data.put("appName", "综合平台");
                        String messageurl = null;
                        if (memberId != null && data.get("messageid") != null && registerCode != null) {
                            messageurl = createMessageUrl(String.valueOf(params.get("receiverAccount")), String.valueOf(data.get("messageid")), registerCode);
                        }
                        if (!Strings.isBlank(messageurl)) {
                            data.put("messageurl", messageurl);
                            data.put("messageh5url", messageurl);
                        }
                    }
                    //根据发送人id，获取发送人名称，并将id转为发送人名称
                    if (null == orgManager) {
                        orgManager = (OrgManager) AppContext.getBean("orgManager");
                    }
                    V3xOrgMember senderMember = orgManager.getMemberById(senderId);
                    data.put("senderName", senderMember.getName());
                    GbMessageVo messageInfo = new GbMessageUtil().createMessageInfo(data);
                    messageList.add(messageInfo);
                }
            }
        }
        //封装返回值数据。
        if (!CollectionUtils.isEmpty(messageList)) {
            resultMap.put("messageInfoList", messageList);
        }
        resultMap.put("total", result.getTotal());
        resultMap.put("pageNum", pageNum);
        resultMap.put("pageSize", pageSize);
        return resultMap;
    }
    /**
     * 拼接跳转url
     *
     * @return
     */
    private String createMessageUrl(String receiverAccount, String affairId, String registerCode) {

        StringBuilder appendUrl = new StringBuilder(URL);
        String ticket = TaskBusinessUtil.encode(receiverAccount, affairId, registerCode);
        appendUrl
                .append("/seeyon/login/sso?from=guobo&ticket=")
                .append(ticket)
                .append("&tourl=/seeyon/colsso.jsp");
        return String.valueOf(appendUrl);
    }
}
