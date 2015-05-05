package com.zzy.scheduler;



import java.util.Properties;

import com.zzy.util.Log;

public class XmlJobConfig<T extends SchedulableJob> implements IJobConfig<T> {

	private int concurrency;
	private String cron;
	private Class<T> implementation;
	private Long jobId;
	private String name;
	private String params;
	private int timeout;
	private boolean enabled;
	private static final Log log = Log.getLogger(XmlJobConfig.class);

	public int getConcurrency() {
		return this.concurrency;
	}

	public String getCronExpression() {
		return this.cron;
	}

	public Class<T> getImplementationClass() {
		return this.implementation;
	}

	public Long getJobId() {
		return this.jobId;
	}

	public String getName() {
		return this.name;
	}

	public String getStartupParameter() {
		return this.params;
	}

	public int getTimeout() {
		return this.timeout;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static XmlJobConfig<?> getInstance(Properties p) {
		XmlJobConfig<?> xml = new XmlJobConfig<SchedulableJob>();
		xml.jobId = Long.parseLong(p.getProperty("jobid"));
		xml.name = p.getProperty("name");
		xml.timeout = Integer.parseInt(p.getProperty("timeout"));
		xml.enabled = Boolean.parseBoolean(p.getProperty("enabled"));
		xml.cron = p.getProperty("cron");
		xml.concurrency = Integer.parseInt(p.getProperty("concurrency"));
		xml.params = p.getProperty("params");
		try {
			xml.implementation = (Class) Class.forName(p.getProperty("class"));
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return xml;
	}

}
