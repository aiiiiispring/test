package com.seeyon.apps.sygb.po;

import com.seeyon.ctp.common.po.BasePO;

/**
 * 项目：国家博物馆客开
 * <p> Package: com.seeyon.apps.sygb.po </p>
 * <p> File: AuthenticationPo.java </p>
 * <p> Module: 第三方系统注册</p>
 * <p> Description: 认证系统实体表 </p>
 * <p> Date: 2020-4-13 15:50</p>
 *
 * @author ML
 * @version 1.0
 * @email <a href="mailto:malei12257@163.com">ML</a>
 * @date 2020-4-13 15:50
 * @since jdk1.8
 */
public class SummaryAuthenticationPo extends BasePO {

    private Long id;
    /**
     * 公文流程ID
     */
    private Long summaryId;
    /**
     * 接入系统关系ID
     */
    private Long authenticationId;
    /**
     * 第三方业务码
     * 此值平台方不做改动，公文流程推回时原封不动推回
     */
    private String thirdBusinessCode;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long getSummaryId() {
        return summaryId;
    }

    public void setSummaryId(Long summaryId) {
        this.summaryId = summaryId;
    }

    public Long getAuthenticationId() {
        return authenticationId;
    }

    public void setAuthenticationId(Long authenticationId) {
        this.authenticationId = authenticationId;
    }

    public String getThirdBusinessCode() {
        return thirdBusinessCode;
    }

    public void setThirdBusinessCode(String thirdBusinessCode) {
        this.thirdBusinessCode = thirdBusinessCode;
    }
}
