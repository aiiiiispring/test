package com.seeyon.apps.sygb.vo;

import java.util.Date;

/**
 * 认证系统表
 * @author ML
 * @date 2018-4-18
 */
public class AuthenticationVo {
	private Long id;

	/**
	 * CIP注册系统ID
	 */
	private Long registerId;

	/**
	 * 系统名称
	 */
	private String appName;
	/**
	 * 系统编码
	 */
	private String appCode;

	/**
	 * 产品编号
	 */
	private String productCode;

	/**
	 * 系统URL
	 */
	private String url;
	/**
	 * 模块类型
	 */
	private Integer moduleType;
	private String moduleTypeName;
	/**
	 * 传递方式
	 */
	private Integer way;
	private String wayName;
	/**
	 * 是否启用
	 */
	private Integer isEnable;
	private String isEnableName;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
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

	public String getWayName() {
		return wayName;
	}

	public void setWayName(String wayName) {
		this.wayName = wayName;
	}

	public Integer getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Integer isEnable) {
		this.isEnable = isEnable;
	}

	public String getIsEnableName() {
		return isEnableName;
	}

	public void setIsEnableName(String isEnableName) {
		this.isEnableName = isEnableName;
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

	public String getModuleTypeName() {
		return moduleTypeName;
	}

	public void setModuleTypeName(String moduleTypeName) {
		this.moduleTypeName = moduleTypeName;
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
}
