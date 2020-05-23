/**
 * Author:liguotao
 * Date : 2020:04:23
 * <p>
 * Copyright (C) 2012 Seeyon, Inc. All rights reserved.
 * <p>
 * This software is the proprietary information of Seeyon, Inc.
 * Use is subject to license terms.
 **/
package com.seeyon.apps.sygb.vo;


/**
 * <p>Title: 消息实体类</p>
 * <p>Description: 封装消息相关数据</p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: seeyon.com</p>
 */
public class GbMessageVo {
    private String messageId;
    private String title;
    private String senderName;
    private String appName;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    private String creation_date;

    private String messageURL;
    private String messageH5URL;


    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }


    public String getMessageURL() {
        return messageURL;
    }

    public void setMessageURL(String messageURL) {
        this.messageURL = messageURL;
    }

    public String getMessageH5URL() {
        return messageH5URL;
    }

    public void setMessageH5URL(String messageH5URL) {
        this.messageH5URL = messageH5URL;
    }
}
