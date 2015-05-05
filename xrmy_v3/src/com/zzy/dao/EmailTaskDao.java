package com.zzy.dao;

import java.util.List;

import com.zzy.enums.StatusType;
import com.zzy.model.EmailTask;
import com.zzy.util.Paging;



public interface EmailTaskDao {
	public boolean addEmailTask(EmailTask emailTask);
	public List<EmailTask>  findByPage(StatusType userStatus,String sortColumns,String sortDir,Paging paging);
	public boolean deleteEmailTask(EmailTask emailTask);
	List<EmailTask> findByPlanTimeNull(StatusType status, String sortColumns,
			String sortDir, Paging paging);
	List<EmailTask> findByPlanTimeNotNull(StatusType status, String sortColumns,
			String sortDir, Paging paging);
	List<EmailTask> findByPageRunPro(StatusType status, String sortColumns,
			String sortDir, Paging paging);
}
