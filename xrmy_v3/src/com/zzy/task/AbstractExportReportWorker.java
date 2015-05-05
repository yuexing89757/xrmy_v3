package com.zzy.task;

import com.zzy.model.ExportReportTask;
import com.zzy.util.Log;


/**
 * report任务执行
 * @author  zhangjunfeng
 * @date  2015-3-24
 *
 */
public abstract class AbstractExportReportWorker implements Runnable{
	private Log log = Log.getLogger(AbstractExportReportWorker.class);
	protected ExportReportTask task;
	public AbstractExportReportWorker(ExportReportTask task){
	}
	/**
	 * 任务执行的主方法
	 * @return
	 * @author  zhangjunfeng
	 * @date  2015-3-25
	 * @reWriter  
	 * @reWriteTime
	 */
	protected abstract boolean execute();
	
	/**
	 * 初始化DAO方法
	 * 
	 * @author  zhangjunfeng
	 * @date  2015-3-25
	 * @reWriter  
	 * @reWriteTime
	 */
	protected abstract void intiDao();
	
	public void run() {
		//更改任务状态为pending
		updateTask2Pending();
		try {
			if(!execute()){
				log.error("任务{0}执行失败",task.getId());
			}
		} catch (Exception e) {
			log.error(e);
		}
		
	}
	/**
	 * 更改任务状态为pending
	 * 
	 * @author  zhangjunfeng
	 * @date  2015-3-25
	 * @reWriter  
	 * @reWriteTime
	 */
	private void updateTask2Pending() {
		try {
			//mysql
		} catch (Exception e) {
			///
		}
	}
}
