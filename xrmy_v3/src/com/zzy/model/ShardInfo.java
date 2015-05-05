package com.zzy.model;

import com.zzy.model.supermodel.ModelObject;

/**
 * ShardInfo entity. @author MyEclipse Persistence Tools
 */

public class ShardInfo extends ModelObject<Long> {

	// Fields
	private Long adId;
	private String dbname;
	private String user;
	private String pwd;
	private String ip;
	private String port;
	private Integer status;
	private String reportDBName;
	private String taskServerName;
	private String dataBaseName;//在页面上展示的数据库名称
	// Constructors
	
	/** default constructor */
	public ShardInfo() {
	}



	
	
	
	// Property accessors

	/**
	 * @return the dataBaseName
	 */
	public String getDataBaseName() {
		return dataBaseName;
	}

	/**
	 * @param dataBaseName the dataBaseName to set
	 */
	public void setDataBaseName(String dataBaseName) {
		this.dataBaseName = dataBaseName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getDbname() {
		return this.dbname;
	}

	public void setDbname(String dbname) {
		this.dbname = dbname;
	}

	public String getUser() {
		return this.user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return this.port;
	}

	public void setPort(String port) {
		this.port = port;
	}


	public Long getAdId() {
		return adId;
	}



	public void setAdId(Long adId) {
		this.adId = adId;
	}






	public String getReportDBName() {
		return reportDBName;
	}

	public void setReportDBName(String reportDBName) {
		this.reportDBName = reportDBName;
	}

	public String getTaskServerName() {
		return taskServerName;
	}

	public void setTaskServerName(String taskServerName) {
		this.taskServerName = taskServerName;
	}
	
}