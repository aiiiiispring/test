package com.seeyon.apps.sygb.po;

import com.seeyon.ctp.common.po.BasePO;

import java.util.Date;

/**
 * 项目：国家博物馆客开
 * <p> Package: com.seeyon.apps.sygb.po </p>
 * <p> File: GbEdocLogPo.java </p>
 * <p> Module: 日志记录</p>
 * <p> Description: 国博日志记录实体表 </p>
 * <p> Date: 2020-4-13 15:50</p>
 *
 * @author ML
 * @version 1.0
 * @email <a href="mailto:malei12257@163.com">ML</a>
 * @date 2020-4-13 15:50
 * @since jdk1.8
 */
public class GbEdocLogPo extends BasePO {
    private static final long serialVersionUID = -5435988344206959800L;
    private Long id;

    /**
     * 操作用户ID
     */
    private Long memberId;
    /**
     * 系统编号
     */
    private String systemCode;
    /**
     * 系统名
     */
    private String systemName;
    /**
     * 系统URL
     */
    private String url;
    /**
     * 动作方向
     */
    private Integer actionType;

    /**
     * 模块类型
     */
    private Integer moduleType;

    /**
     * 结果类型
     */
    private Integer resultType;
    /**
     * 结果详情
     */
    private String resultInfo;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 传输的数据
     */
    private String dataJson;

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

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public Integer getActionType() {
        return actionType;
    }

    public void setActionType(Integer actionType) {
        this.actionType = actionType;
    }

    public Integer getModuleType() {
        return moduleType;
    }

    public void setModuleType(Integer moduleType) {
        this.moduleType = moduleType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDataJson() {
        return dataJson;
    }

    public void setDataJson(String dataJson) {
        this.dataJson = dataJson;
    }

    public Integer getResultType() {
        return resultType;
    }

    public void setResultType(Integer resultType) {
        this.resultType = resultType;
    }

    public String getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(String resultInfo) {
        this.resultInfo = resultInfo;
    }
}
