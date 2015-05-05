package com.zzy.task;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import com.zzy.model.ExportReportTask;
import com.zzy.util.ConfigManager;
import com.zzy.util.Log;
import com.zzy.util.ObjectUtils;
import com.zzy.util.Paging;



public class ReportThreadManager {
	private static Log log = Log.getLogger(AbstractExportReportWorker.class);
	private static int poolSize;
	private static ExecutorService workerPool;
	static {
		try {
			ConfigManager cm = ConfigManager.getInstance(ConfigManager.commonsFile);
			poolSize = Integer.parseInt((String) cm.getConfigItem("export_report_thread_poolsize", "15"));
			workerPool = Executors.newFixedThreadPool(poolSize, new ThreadFactory() {
				int i = 1;

				public Thread newThread(Runnable r) {
					Thread thread = new Thread(r);
					thread.setName("exprotReportThread--" + (i++));
					return thread;
				}
			});
		} catch (RuntimeException e) {
			log.error(e);
			throw e;
		}
	}

	/**
	 * 把数据库中的任务导入到线程并执行
	 * 
	 * @author zhangjunfeng
	 * @date 2015-3-24
	 * @reWriter
	 * @reWriteTime
	 */
	public static void loadTaskFromDB() {
		// 从数据库中获取ExportReportTask
		List<ExportReportTask> tasks = getDBTask();
		// 把任务进行托管
		submit(tasks);
	}

	

	/**
	 * 从数据库中获取ExportReportTask
	 * 
	 * @return
	 * @author zhangjunfeng
	 * @date 2015-3-25
	 * @reWriter
	 * @reWriteTime
	 */
	private static List<ExportReportTask> getDBTask() {
		List<ExportReportTask> tasks = new ArrayList<ExportReportTask>();
		try {
		/////dao
			Paging paging = new Paging();
			paging.setCurrentPage(1);
			paging.setPageSize(5000);

			while (true) {
				List<ExportReportTask> _tasks =null;//mysql //exportReportTaskDAO.findAllExceptByStatus(paging, TaskStatus.COMPLETE);
				if (ObjectUtils.isEmpty(_tasks)) {
					break;
				}
				tasks.addAll(_tasks);
			}
		} catch (Exception e) {
			log.error(e);
		}
		return tasks;
	}
	/**
	 * 把导出任务托管给线程池进行管理执行  
	 * @param tasks
	 * @author  zhangjunfeng
	 * @date  2015-3-25
	 * @reWriter  
	 * @reWriteTime
	 */
	public static void submit(List<ExportReportTask> tasks) {

		if (!ObjectUtils.isEmpty(tasks)) {
			for (ExportReportTask exportReportTask : tasks) {
				submit(exportReportTask);
			}
		}

	}
	
	/**
	 * 把导出任务托管给线程池进行管理执行
	 * @param task
	 * @author  zhangjunfeng
	 * @date  2015-3-25
	 * @reWriter  
	 * @reWriteTime
	 */
	public static void submit(ExportReportTask task) {
		AbstractExportReportWorker exportReport;
		Object o;
		try {
			Class<?> cla = Class.forName(task.getWorker());
			Constructor<?> con = cla.getConstructor(ExportReportTask.class);
			o = con.newInstance(task);
			if (o instanceof AbstractExportReportWorker) {
				exportReport = (AbstractExportReportWorker) o;
				workerPool.execute(exportReport);
			} else {
				throw new Exception("不是一个有效的AbstractExportReport任务");
			}
		} catch (Exception e) {
			log.error(e);
		}
	}

	public static void addTimeoutListener() {
		// TODO 添加一个超时监听器
	}

	/**
	 * 优雅的关闭线程池中的所有任务，发出指令后，将不在接收新的任务 注意，此方法会导致线程
	 * 
	 * @author zhangjunfeng
	 * @date 2015-3-24
	 * @reWriter
	 * @reWriteTime
	 */
	public static void shutdownGraceful() {
		// 通知线程池关闭，不在接收新的任务
		workerPool.shutdown();
		try {
			// 等待任务线程结束
			if (!workerPool.awaitTermination(60, TimeUnit.SECONDS)) {
				// 超过指定时间，没有关闭，那么进行强制关闭
				shutdownNow();
				// 再次等待任务线程结束
				if (!workerPool.awaitTermination(60, TimeUnit.SECONDS)) {
					// 超过指定时间，还是没有关闭，发送预警
					// TODO 告知管理员没有关闭
				}
			}
		} catch (InterruptedException ie) {
			// 如果上述方法中的awaitTermination别其他线程中断，强制关闭线程
			shutdownNow();
			// 继续保持改线程的中断状态
			Thread.currentThread().interrupt();
		}

	}

	/**
	 * 立即停止线程池中的所有任务，但是并不能保证一定可以停止
	 * 
	 * @author zhangjunfeng
	 * @date 2015-3-24
	 * @reWriter
	 * @reWriteTime
	 */
	public static void shutdownNow() {
		workerPool.shutdownNow();
	}
}
