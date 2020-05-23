package com.seeyon.apps.sygb.bo;

/**
 * 项目：国家博物馆客开
 * <p> Package: com.seeyon.apps.sygb.bo </p>
 * <p> File: GbResultDataBo.java </p>
 * <p> Module: 公文接口 </p>
 * <p> Description: [推回公文数据返回值对象 ] </p>
 * <p> Date: 2020-4-16 11:01</p>
 *
 * @author ML
 * @version 1.0
 * @email <a href="mailto:malei12257@163.com">ML</a>
 * @date 2020-4-16 11:01
 * @since jdk1.8
 */
public class GbBpResultDataBo {
    /**
     * 第三方系统编码（判断是否是有权限的系统编码）
     */
    private String systemCode;

    /**
     * 第三方传递的业务码
     */
    private String thirdBusinessCode;
    /**
     * 公文相关数据
     */
    private GbBpDataBo data;

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getThirdBusinessCode() {
        return thirdBusinessCode;
    }

    public void setThirdBusinessCode(String thirdBusinessCode) {
        this.thirdBusinessCode = thirdBusinessCode;
    }

    public GbBpDataBo getData() {
        return data;
    }

    public void setData(GbBpDataBo data) {
        this.data = data;
    }
}
