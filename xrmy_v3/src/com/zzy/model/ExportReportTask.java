package com.zzy.model;

import java.util.Calendar;

import com.zzy.enums.AdNetwork;
import com.zzy.enums.TaskStatus;
import com.zzy.model.supermodel.ModelObject;


public class ExportReportTask extends ModelObject<Long> {
	
	private AdNetwork network;
	private String worker; // worker class
	private TaskStatus status;
	private Calendar dtCreated;
	private Calendar dtStarted;
	private Calendar dtUpdated;
	private String parameter;
	private Long advertiserId=-1l;
	private String csvPath;
	public ExportReportTask(){}
	public ExportReportTask(Long id) {
		super(id);
	}
	
	public String getCsvPath() {
		return csvPath;
	}
	public void setCsvPath(String csvPath) {
		this.csvPath = csvPath;
	}
	public AdNetwork getNetwork() {
		return network;
	}
	public void setNetwork(AdNetwork network) {
		this.network = network;
	}
	public String getWorker() {
		return worker;
	}
	public void setWorker(String worker) {
		this.worker = worker;
	}
	public TaskStatus getStatus() {
		return status;
	}
	public void setStatus(TaskStatus status) {
		this.status = status;
	}
	public Calendar getDtCreated() {
		return dtCreated;
	}
	public void setDtCreated(Calendar dtCreated) {
		this.dtCreated = dtCreated;
	}
	public Calendar getDtStarted() {
		return dtStarted;
	}
	public void setDtStarted(Calendar dtStarted) {
		this.dtStarted = dtStarted;
	}
	public Calendar getDtUpdated() {
		return dtUpdated;
	}
	public void setDtUpdated(Calendar dtUpdated) {
		this.dtUpdated = dtUpdated;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public Long getAdvertiserId() {
		return advertiserId;
	}
	public void setAdvertiserId(Long advertiserId) {
		this.advertiserId = advertiserId;
	}

	
}
