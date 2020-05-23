package com.seeyon.apps.sygb.bo;

import java.util.LinkedList;
import java.util.List;

/**
 * 项目：国家博物馆客开
 * <p> Package: com.seeyon.apps.sygb.bo </p>
 * <p> File: GBBpDataBo.java </p>
 * <p> Module: 公文流程发起 </p>
 * <p> Description: [公文报批件转换类 ] </p>
 * <p> Date: 2020-3-30 13:48</p>
 *
 * @author ML
 * @version 1.0
 * @email <a href="mailto:malei12257@163.com">ML</a>
 * @date 2020-3-30 13:48
 * @since jdk1.8
 */
public class GbBpDataBo {

    /**
     * 部门文号
     */
//    private String deptEDocNumber;
    /**
     * 公文标题
     */
    private String title;
    /**
     * 紧急程度
     */
    private String urgency;
    /**
     * 起草人
     */
    private String drafterMember;

    /**
     * 发起部门
     */
    private String drafterDept;

    /**
     * 联系电话
     */
    private String contactNumber;
    /**
     * 支付类型
     */
    private String payType;
    /**
     * 付款金额
     */
    private String payMoney;
    /**
     * 报批附件(组装时使用)
     */
    private String approvalFileId;
    /**
     * 签署合同
     */
    private String signContract;
    /**
     * 正文
     */
    private String content;
    /**
     * 正文ID
     */
    private Long summaryId;
    /**
     * 公文关联文档id
     */
    private List<Long> fileIds;
    /**
     * FTP方式附件列表
     */
    private List<GbFileBo> files;

    // 以下为调用第三方接口推送使用字段
    /**
     * 起草部门名称
     */
    private String drafterDeptName;

    /**
     * 部门负责人
     */
//    private String deptLeaderLoginName;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 印发文废文(文件状态)
     */
    private String documentStatus;
    /**
     * 是否上会
     */
//    private String toMeeting;
    /**
     * 报批序号
     */
    private String serialNumber;
    /**
     * 签批日期
     */
    private String approvalDate;

    /**
     * 单据类型
     */
//    private String documentType;

    /**
     * 汇报人
     */
    private String reportedBy;

    /**
     * 重要程度
     */
//    private String importance;

    /**
     * 馆长批示
     */
//    private String curatorInstruction;
    /**
     * 分管领导批示
     */
//    private String leaderInstruction;
    /**
     * 注办意见
     */
//    private String noteOpinion;
    /**
     * 会签部门意见
     */
//    private String countersignDeptOpinion;
    /**
     *
     * 添加附件数据
     * @param gbFileBo
     * @return
     */
    public GbBpDataBo addDocFile(GbFileBo gbFileBo){
        if (files == null) {
            files = new LinkedList<>();
        }
        files.add(gbFileBo);
        return this;
    }
    /**
     * 添加关联文档
     * @param fileId
     * @return
     */
    public GbBpDataBo addDocFileId(Long fileId){
        if (fileIds == null) {
            fileIds = new LinkedList<>();
        }
        fileIds.add(fileId);
        return this;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrgency() {
        return urgency;
    }

    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }

    public String getDrafterMember() {
        return drafterMember;
    }

    public void setDrafterMember(String drafterMember) {
        this.drafterMember = drafterMember;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(String payMoney) {
        this.payMoney = payMoney;
    }

    public String getApprovalFileId() {
        return approvalFileId;
    }

    public void setApprovalFileId(String approvalFileId) {
        this.approvalFileId = approvalFileId;
    }

    public String getSignContract() {
        return signContract;
    }

    public void setSignContract(String signContract) {
        this.signContract = signContract;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getSummaryId() {
        return summaryId;
    }

    public void setSummaryId(Long summaryId) {
        this.summaryId = summaryId;
    }

    public List<Long> getFileIds() {
        return fileIds;
    }

    public void setFileIds(List<Long> fileIds) {
        this.fileIds = fileIds;
    }

    public List<GbFileBo> getFiles() {
        return files;
    }

    public void setFiles(List<GbFileBo> files) {
        this.files = files;
    }

    public String getDrafterDept() {
        return drafterDept;
    }

    public void setDrafterDept(String drafterDept) {
        this.drafterDept = drafterDept;
    }

    public String getDrafterDeptName() {
        return drafterDeptName;
    }

    public void setDrafterDeptName(String drafterDeptName) {
        this.drafterDeptName = drafterDeptName;
    }



    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDocumentStatus() {
        return documentStatus;
    }

    public void setDocumentStatus(String documentStatus) {
        this.documentStatus = documentStatus;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(String approvalDate) {
        this.approvalDate = approvalDate;
    }

    public String getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(String reportedBy) {
        this.reportedBy = reportedBy;
    }

}
