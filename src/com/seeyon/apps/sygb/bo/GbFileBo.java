package com.seeyon.apps.sygb.bo;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 项目：国家博物馆客开
 * <p> Package: com.seeyon.apps.sygb.bo </p>
 * <p> File: GBFileBO.java </p>
 * <p> Module: 公文发起流程 </p>
 * <p> Description: [文件相关] </p>
 * <p> Date: 2020-3-30 13:50</p>
 *
 * @author ML
 * @version 1.0
 * @email <a href="mailto:malei12257@163.com">ML</a>
 * @date 2020-3-30 13:50
 * @since jdk1.8
 */
public class GbFileBo {
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件后缀
     */
    private String fileSuffix;
    /**
     * FTP文件路径
     */
    private String ftpFilePath;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 流程文件类型，文单附件还是流程附件
     */
    private String fileType;

    /**
     * 文件类型
     */
    private String fileMimeType;

    /**
     * 本地路径，处理时需要
     */
    private String localPath;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileSuffix() {
        return fileSuffix;
    }

    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    @JsonIgnore
    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public String getFtpFilePath() {
        return ftpFilePath;
    }

    public void setFtpFilePath(String ftpFilePath) {
        this.ftpFilePath = ftpFilePath;
    }

    public String getFileMimeType() {
        return fileMimeType;
    }

    public void setFileMimeType(String fileMimeType) {
        this.fileMimeType = fileMimeType;
    }
}
