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
 * <p> File: AuthenticationController.java </p>
 * <p> Module: 系统注册 </p>
 * <p> Description: [第三方系统注册控制类] </p>
 * <p> Date: 2020-4-13 16:53</p>
 * @author ML
 * @email <a href="mailto:malei12257@163.com">ML</a>
 * @date 2020-4-13 16:53
 * @since jdk1.8
 * @version 1.0
 */

public class AuthenticationController extends BaseController {
    private static Log LOG = CtpLogFactory.getLog(AuthenticationController.class);


    public ModelAndView listAuthentication(HttpServletRequest request, HttpServletResponse response) throws BusinessException {
        LOG.info("进入配置第三方系统列表页面");
        ModelAndView view = new ModelAndView("/apps/sygb/authentication/authenticationList");
        return view;
    }

    public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws BusinessException {
        LOG.info("进入配置第三方系统编辑页面");
        ModelAndView view = new ModelAndView("/apps/sygb/authentication/edit");
        String flag = request.getParameter("flag");
        String id = request.getParameter("id");
        view.addObject("flag", flag);
        view.addObject("id", id);
        return view;
    }
}
