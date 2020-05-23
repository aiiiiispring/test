package com.seeyon.apps.sygb.listener;

import com.seeyon.apps.sygb.manager.GbDocManager;
import com.seeyon.ctp.common.AppContext;
import com.seeyon.ctp.common.exceptions.BusinessException;
import com.seeyon.ctp.workflow.event.AbstractWorkflowEvent;
import com.seeyon.ctp.workflow.event.WorkflowEventData;
import org.apache.log4j.Logger;

/**
 * 项目：国家博物馆客开
 * <p> Package: com.seeyon.apps.sygb.listener </p>
 * <p> File: GbEDocEventFlowFinished.java </p>
 * <p> Module: 流程结束事件 </p>
 * <p> Description: [国博公文流程结束事件 ] </p>
 * <p> Date: 2020-4-3 10:14</p>
 *
 * @author ML
 * @version 1.0
 * @email <a href="mailto:malei12257@163.com">ML</a>
 * @date 2020-4-3 10:14
 * @since jdk1.8
 */
public class GbDocEventFlowFinishedListener extends AbstractWorkflowEvent {

    private static Logger LOGGER = Logger.getLogger(GbDocEventFlowFinishedListener.class);

    private GbDocManager gbDocManager = (GbDocManager) AppContext.getBean("gbDocManager");

    @Override
    public String getId() {
        return "gbEDocEventFlowFinishedListener";
    }

    @Override
    public String getLabel() {
        return "国博报批件公文流程结束事件";
    }

    /**
     * 流程结束事件
     *
     * @param data
     * @return void
     * @throws BusinessException 异常捕获
     * @author ML
     * @version 1.0
     * @Time 2020-4-15 17:18
     */
    @Override
    public void onProcessFinished(WorkflowEventData data) {
        LOGGER.info("-------------开始国博报批件公文流程结束事件------------");
        gbDocManager.onGbProcessFinished(data);
        LOGGER.info("-------------完成国博报批件公文流程结束事件------------");
    }
}
