package com.seeyon.apps.sygb.po;

import com.seeyon.ctp.common.po.BasePO;

import java.util.Date;

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
public class AuthenticationPo extends BasePO {
    private static final long serialVersionUID = -5435988344206959800L;
    private Long id;
    /**
     * CIP注册系统ID
     */
    private Long registerId;
    /**
     * 系统URL
     */
    private String url;
    /**
     * 传递方式
     */
    private Integer way;

    /**
     * 模块类型
     */
    private Integer moduleType;

    /**
     * 是否启用
     */
    private Integer isEnable;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getWay() {
        return way;
    }

    public void setWay(Integer way) {
        this.way = way;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getRegisterId() {
        return registerId;
    }

    public void setRegisterId(Long registerId) {
        this.registerId = registerId;
    }

    public Integer getModuleType() {
        return moduleType;
    }

    public void setModuleType(Integer moduleType) {
        this.moduleType = moduleType;
    }
}
