package com.zzy.model;

import java.util.Calendar;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.quartz.JobExecutionContext;

import com.zzy.enums.AdNetwork;
import com.zzy.enums.TaskPriority;
import com.zzy.enums.TaskStatus;
import com.zzy.model.supermodel.ModelObject;

public abstract class Task extends ModelObject<Long> {

	private Task parent;
	private AdNetwork network;
	@JsonIgnore
	private String worker; // worker class
	private int retry;
	private TaskPriority priority;
	private int priorityLevel = -1;
	@JsonIgnore
	private TaskStatus status;
	private Calendar dtCreated;
	private Calendar dtStarted;
	private Calendar dtUpdated;
	@JsonIgnore
	private Integer workCode;
	private String parameter;
	private Calendar preferredExecutionDate;
	private long retryInterval;
	@JsonIgnore
	private JobExecutionContext jobContext;
	private int networkObjectCount;
	private String formateProperties;
	private Double rate=1d;
	private Long advertiserId=-1l;
	private Long staticAccountId=-1l;
	private Long shardInfoId=-1l;
	
	@JsonIgnore
	public abstract String getLevelCode();

	@JsonIgnore
	public abstract void setLevelCode(String levelCode);

	public abstract Long getAccountId();

	public abstract void setAccountId(Long accountId);
	
	public Task (){
		
	}
	
	public Task(Long id) {
		super(id);
	}
	
	public Task getParent() {
		return this.parent;
	}

	public void setParent(Task parent) {
		this.parent = parent;
	}

	public AdNetwork getNetwork() {
		return this.network;
	}

	public void setNetwork(AdNetwork network) {
		this.network = network;
	}

	public String getWorker() {
		return this.worker;
	}

	public void setWorker(String worker) {
		this.worker = worker;
	}

	public int getRetry() {
		return this.retry;
	}

	public void setRetry(int retry) {
		this.retry = retry;
	}

	public TaskPriority getPriority() {
		return this.priority;
	}

	public void setPriority(TaskPriority priority) {
		this.priority = priority;
		this.priorityLevel = this.priority.getLevel();
	}

	public TaskStatus getStatus() {
		return this.status;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
	}

	public Calendar getDtCreated() {
		return this.dtCreated;
	}

	public void setDtCreated(Calendar dtCreated) {
		this.dtCreated = dtCreated;
	}

	public Calendar getDtStarted() {
		return this.dtStarted;
	}

	public void setDtStarted(Calendar dtStarted) {
		this.dtStarted = dtStarted;
	}

	public Calendar getDtUpdated() {
		return this.dtUpdated;
	}

	public void setDtUpdated(Calendar dtUpdated) {
		this.dtUpdated = dtUpdated;
	}

	public int getPriorityLevel() {
		if (null != this.priority) {
			return this.priority.getLevel();
		}
		return this.priorityLevel;
	}

	public void setPriorityLevel(int level) {
		this.priorityLevel = level;
		this.priority = TaskPriority.fromLevel(level);
	}

	public Integer getWorkCode() {
		return workCode;
	}

	public void setWorkCode(Integer workCode) {
		this.workCode = workCode;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public Calendar getPreferredExecutionDate() {
		return preferredExecutionDate;
	}

	public void setPreferredExecutionDate(Calendar preferredExecutionDate) {
		this.preferredExecutionDate = preferredExecutionDate;
	}

	public int getNetworkObjectCount() {
		return networkObjectCount;
	}

	public void setNetworkObjectCount(int networkObjectCount) {
		this.networkObjectCount = networkObjectCount;
	}

	public long getRetryInterval() {
		return retryInterval;
	}

	public void setRetryInterval(long retryInterval) {
		this.retryInterval = retryInterval;
	}
	
	@JsonIgnore
	public JobExecutionContext getJobContext() {
		return jobContext;
	}

	@JsonIgnore
	public void setJobContext(JobExecutionContext jobContext) {
		this.jobContext = jobContext;
	}
	
	@JsonIgnore
	public void putJobContextData(Object key,Object value){
		if(jobContext != null){
			jobContext.put(key, value);
		}
	}
	@JsonIgnore
	public Object getJobContextDataByKey(String key){
		if(jobContext != null && key != null){
			return jobContext.get(key);
		}else{
			return null;
		}
	}
	
	public String getFormateProperties() {
		return formateProperties;
	}

	public void setFormateProperties(String formateProperties) {
		this.formateProperties = formateProperties;
	}
	
	
	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}
	

	

	public Long getAdvertiserId() {
		return advertiserId;
	}

	public void setAdvertiserId(Long advertiserId) {
		this.advertiserId = advertiserId;
	}

	public Long getStaticAccountId() {
		return staticAccountId;
	}

	public void setStaticAccountId(Long staticAccountId) {
		this.staticAccountId = staticAccountId;
	}
	
	

	public Long getShardInfoId() {
		return shardInfoId;
	}

	public void setShardInfoId(Long shardInfoId) {
		this.shardInfoId = shardInfoId;
	}

	@Override
	public String toString() {
		return makeIdentifiableString(this, this.id, this.getAccountId(), this.network,this.worker,this.retry,this.priority,this.status,this.formateProperties,this.rate);
	}
}
