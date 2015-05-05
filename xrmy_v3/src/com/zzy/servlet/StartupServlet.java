package com.zzy.servlet;

import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.zzy.scheduler.IJobConfig;
import com.zzy.scheduler.JobProvider;
import com.zzy.scheduler.SchedulableJob;
import com.zzy.scheduler.SchedulerService;
import com.zzy.util.ConfigManager;
import com.zzy.util.ExceptionUtil;
import com.zzy.util.Log;

public class StartupServlet extends HttpServlet implements Servlet {

	private static final long serialVersionUID = 1L;

	private static final Log log = Log.getLogger(StartupServlet.class);
	
	private static ConfigManager cm = ConfigManager.getInstance(ConfigManager.commonsFile);
	
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		// initialize quartz
		try {
			List<IJobConfig<? extends SchedulableJob>> jobSettings = JobProvider.getInstance().getJobSettings();
			if (jobSettings != null && jobSettings.size()>0) {
				for (IJobConfig<? extends SchedulableJob> job : jobSettings) {
					SchedulerService.getInstance().initJob(job);
				}
			}else {
				log.info("jobSettings == null or size == 0");
			}
		} catch (Exception e) {
			log.error(ExceptionUtil.getStackTrace(e));
		}
		
		System.out.println(cm.getConfigItem("sample")+"--------------------");

	}
	
	
}
