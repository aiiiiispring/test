package com.seeyon.apps.sygb.constants;

import com.seeyon.ctp.common.code.CustomCode;

import java.util.LinkedHashMap;
import java.util.Map;

/***
 * 项目：国家博物馆客开
 * <p> Package: com.seeyon.apps.sygb.constants </p>
 * <p> File: EnableCategotysEmun.java </p>
 * <p> Module: 前端枚举 </p>
 * <p> Description: [是否启用，用于前端系统注册下拉框枚举] </p>
 * <p> Date: 2020-4-13 15:55</p>
 * @author ML
 * @email <a href="mailto:malei12257@163.com">ML</a>
 * @date 2020-4-13 15:55
 * @since jdk1.8
 * @version 1.0
 */
public class EnableCategoryEnum implements CustomCode {
    @Override
    public Map getCodesMap(Map codeCfg) {
        GbEnum.EnableEnum[] categorys = GbEnum.EnableEnum.values();
        Map myMap = new LinkedHashMap();
        for (int i = 0; i < categorys.length; i++) {
            GbEnum.EnableEnum cate = categorys[i];
            myMap.put(cate.getKey() + "", cate.getText());
        }
        return myMap;
    }

}
