package com.seeyon.apps.sygb.manager;

import com.kg.commons.utils.CollectionUtils;
import com.seeyon.apps.sygb.dao.GbTaskDao;
import com.seeyon.apps.sygb.util.GbTaskUtil;
import com.seeyon.apps.sygb.vo.GbTaskVo;
import com.seeyon.ctp.util.FlipInfo;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 待办已办业务实现
 */
public class GbTaskManagerImpl implements GbTaskManager {

    private GbTaskDao gbTaskDao;

    public void setGbTaskDao(GbTaskDao gbTaskDao) {
        this.gbTaskDao = gbTaskDao;
    }

    //默认当前页码为1，分页最大条数20
    private static final Integer DEFAULT_PAGE_NUM = 1;
    private static final Integer DEFAULT_PAGE_MAX_SIZE = 20;
    /**
     * 获取待办已办数据
     * @param map
     * @param memberId
     * @param thirdPartyState
     * @param affairState
     * @return
     */
    @Override
    public HashMap<String, Object> getTaskInformation(Map<String, Object> map, Long memberId, String thirdPartyState, String affairState) {
        //接收参数 //没有传递参数则设置为默认
        Integer pageNum = DEFAULT_PAGE_NUM;
        Integer pageSize = DEFAULT_PAGE_MAX_SIZE;
        if (null != map.get("pageNum")) {
            pageNum = Integer.parseInt(map.get("pageNum").toString());
        }
        if (null != map.get("pageSize")) {
            pageSize = Integer.parseInt(map.get("pageSize").toString());
        }
        String receiverAccount = map.get("receiverAccount").toString();
        //调用dao层处理数据
        FlipInfo result = gbTaskDao.getTaskInformation(pageNum, pageSize, receiverAccount, memberId, thirdPartyState, affairState);
        List<Map<String, Object>> dataList = result.getData();
        //判断有无查询到数据
        if (CollectionUtils.isEmpty(dataList)) {
            return null;
        }
        //转换数据格式
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        List<GbTaskVo> taskInfoList = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        for (Map<String, Object> data : dataList) {
            data.put("create_date", dateFormat.format(data.get("create_date")));
            // 转换数据格式
            GbTaskVo taskInfo = new GbTaskUtil().cteateTaskInfo(data, receiverAccount);
            taskInfoList.add(taskInfo);
        }
        //数据封装
        resultMap.put("taskList", taskInfoList);
        resultMap.put("total", result.getTotal());
        resultMap.put("pageNum", pageNum);
        resultMap.put("pageSize", pageSize);
        return resultMap;
    }


}
