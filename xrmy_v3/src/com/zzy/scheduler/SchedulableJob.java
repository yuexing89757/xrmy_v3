package com.zzy.scheduler;

import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;

public abstract class SchedulableJob implements InterruptableJob {

	private Thread __current_thread__ = null;
	/**
	 * Main entry point of scheduled job business logic.
	 */
	public void execute(JobExecutionContext jobContext)
			throws JobExecutionException {
		__current_thread__ = Thread.currentThread();
	}

	public void interrupt() throws UnableToInterruptJobException {
		// terminate thread
		__current_thread__.interrupt();
	}
}
