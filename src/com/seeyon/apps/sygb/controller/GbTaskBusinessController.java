package com.seeyon.apps.sygb.controller;

import com.seeyon.ctp.common.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * SAP业务集成回调窗口实现
 *
 * @author Aton
 */
public class GbTaskBusinessController extends BaseController {
    @Autowired
    /**
     * 弹出窗口框架页
     */
    public ModelAndView window(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView("plugin/sygb/index");
        mv.addObject("sapbusinessUrl", request.getParameter("url"));
         mv.addObject("jsessionid", request.getParameter("jsessionid"));
        return mv;
    }
}
