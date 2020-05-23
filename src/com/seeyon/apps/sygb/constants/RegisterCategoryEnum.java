package com.seeyon.apps.sygb.constants;

import com.seeyon.apps.cip.manager.RegisterManager;
import com.seeyon.apps.cip.po.RegisterPO;
import com.seeyon.ctp.common.AppContext;
import com.seeyon.ctp.common.code.CustomCode;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/***
 * 项目：国家博物馆客开
 * <p> Package: com.seeyon.apps.sygb.constants </p>
 * <p> File: EnableCategotysEmun.java </p>
 * <p> Module: 前端枚举 </p>
 * <p> Description: [模块类型，用于前端系统注册下拉框枚举] </p>
 * <p> Date: 2020-4-13 15:55</p>
 * @author ML
 * @email <a href="mailto:malei12257@163.com">ML</a>
 * @date 2020-4-13 15:55
 * @since jdk1.8
 * @version 1.0
 */
public class RegisterCategoryEnum implements CustomCode {
    RegisterManager registerManager = (RegisterManager) AppContext.getBean("registerManager");;
    @Override
    public Map getCodesMap(Map codeCfg) {
        List<RegisterPO> registerList = registerManager.findAll();
        Map myMap = new LinkedHashMap();
        for (RegisterPO registerPO : registerList) {
//            myMap.put(registerPO.getId() + "", registerPO.getAppName()+"<"+registerPO.getAppCode()+">");
            myMap.put(registerPO.getId() + "", registerPO.getAppName());
        }
        return myMap;
    }

}
