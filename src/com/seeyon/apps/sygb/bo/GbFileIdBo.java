package com.seeyon.apps.sygb.bo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

/**
 * 项目：国家博物馆客开
 * <p> Package: com.seeyon.apps.sygb.bo </p>
 * <p> File: GBFileBO.java </p>
 * <p> Module: 公文发起流程 </p>
 * <p> Description: [上传文件辅助类] </p>
 * <p> Date: 2020-3-30 13:50</p>
 *
 * @author ML
 * @version 1.0
 * @email <a href="mailto:malei12257@163.com">ML</a>
 * @date 2020-3-30 13:50
 * @since jdk1.8
 */
public class GbFileIdBo {
    /**
     * 表单字段附件（报批附件）ID集合
     */
    private List<Long> formFieldFileIds;
    /**
     * 流程附件ID集合
     */
    private List<Long> summaryFileIds;
    /**
     * 正文附件ID
     */
    private Long contentFileId;

    public List<Long> getFormFieldFileIds() {
        return formFieldFileIds;
    }

    public void setFormFieldFileIds(List<Long> formFieldFileIds) {
        this.formFieldFileIds = formFieldFileIds;
    }

    public List<Long> getSummaryFileIds() {
        return summaryFileIds;
    }

    public void setSummaryFileIds(List<Long> summaryFileIds) {
        this.summaryFileIds = summaryFileIds;
    }

    public Long getContentFileId() {
        return contentFileId;
    }

    public void setContentFileId(Long contentFileId) {
        this.contentFileId = contentFileId;
    }
}
