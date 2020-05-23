package com.seeyon.apps.sygb.vo;

import com.seeyon.ctp.common.po.BasePO;

import java.util.Date;

/**
 * 项目：国家博物馆客开
 * <p> Package: com.seeyon.apps.sygb.po </p>
 * <p> File: GbEdocLogPo.java </p>
 * <p> Module: 日志记录</p>
 * <p> Description: 国博日志记录实体表 </p>
 * <p> Date: 2020-4-13 15:50</p>
 * @author ML
 * @email <a href="mailto:malei12257@163.com">ML</a>
 * @date 2020-4-13 15:50
 * @since jdk1.8
 * @version 1.0
 */
public class GbEdocLogVo extends BasePO{
	private static final long serialVersionUID = -5435988344206959800L;
	private Long id;

	/**
	 * 操作用户ID
	 */
	private Long memberId;
	private String memberName;
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
	private String actionName;

	/**
	 * 模块类型
	 */
	private Integer moduleType;
	private String moduleTypeName;
	/**
	 * 传输的数据
	 */
	private String dataJson;
	/**
	 * 结果类型
	 */
	private Integer resultType;
	private String resultTypeName;
	/**
	 * 结果详情
	 */
	private String resultInfo;

	/**
	 * 创建时间
	 */
	private Date createTime;

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

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getModuleTypeName() {
		return moduleTypeName;
	}

	public void setModuleTypeName(String moduleTypeName) {
		this.moduleTypeName = moduleTypeName;
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

	public String getResultTypeName() {
		return resultTypeName;
	}

	public void setResultTypeName(String resultTypeName) {
		this.resultTypeName = resultTypeName;
	}

	public String getResultInfo() {
		return resultInfo;
	}

	public void setResultInfo(String resultInfo) {
		this.resultInfo = resultInfo;
	}
}
