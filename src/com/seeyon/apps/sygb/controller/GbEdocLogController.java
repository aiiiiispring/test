package com.seeyon.apps.sygb.controller;

import com.seeyon.ctp.common.controller.BaseController;
import com.seeyon.ctp.common.exceptions.BusinessException;
import com.seeyon.ctp.common.log.CtpLogFactory;
import org.apache.commons.logging.Log;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 项目：国家博物馆客开
 * <p> Package: com.seeyon.apps.sygb.controller </p>
 * <p> File: GbEdocLogController.java </p>
 * <p> Module: 国博日志接口控制类 </p>
 * <p> Date: 2020-4-13 16:53</p>
 * @author ML
 * @email <a href="mailto:malei12257@163.com">ML</a>
 * @date 2020-4-13 16:53
 * @since jdk1.8
 * @version 1.0
 */

public class GbEdocLogController extends BaseController {
    private static Log LOG = CtpLogFactory.getLog(GbEdocLogController.class);

    /**
     * 日志列表显示
     * @param request
     * @param response
     * @return
     * @throws BusinessException
     */
    public ModelAndView logList(HttpServletRequest request, HttpServletResponse response) throws BusinessException {
        ModelAndView view = new ModelAndView("/apps/sygb/log/logList");
        return view;
    }

    /**
     * 显示详情
     * @param request
     * @param response
     * @return
     * @throws BusinessException
     */
    public ModelAndView show(HttpServletRequest request, HttpServletResponse response) throws BusinessException {
        ModelAndView view = new ModelAndView("/apps/sygb/log/show");
        String id = request.getParameter("id");
        view.addObject("id", id);
        return view;
    }
}
