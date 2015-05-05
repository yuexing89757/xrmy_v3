package com.zzy.job;

import java.util.Date;
import java.util.List;

import org.apache.commons.mail.EmailException;
import org.junit.Test;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zzy.biz.ISendMail;
import com.zzy.biz.impl.SendCloudMail;
import com.zzy.biz.impl.SendMail;
import com.zzy.dao.EmailTaskDao;
import com.zzy.dao.dbtool.HibernateTransactionStrategy;
import com.zzy.dao.dbtool.TransactionStrategy;
import com.zzy.dao.impl.EmailTaskDaoImpl;
import com.zzy.enums.StatusType;
import com.zzy.model.EmailTask;
import com.zzy.scheduler.SchedulableJob;
import com.zzy.util.LanguageKey;
import com.zzy.util.Log;
import com.zzy.util.Paging;
import com.zzy.util.StringUtils;
import com.zzy.util.UncheckedException;


public class AutoSendMailJob extends SchedulableJob {
	private Log log = Log.getLogger(AutoSendMailJob.class);
	EmailTaskDao eamilTaskDao = new EmailTaskDaoImpl();

	public void execute(JobExecutionContext jobContext) {
		Paging paging = new Paging();
		paging.setCurrentPage(0);
		paging.setPageSize(10);
		List<EmailTask> resultList=null;
		do{
			resultList= getTaskDB(paging);
			if (null != resultList && resultList.size()>0) {
				TransactionStrategy transaction = HibernateTransactionStrategy.getInstance();
				transaction.begin();
				try{
					for (EmailTask emailTask : resultList) {
						 emailTask=sendMail(emailTask);
						if(StringUtils.hasText(emailTask.getFailcode())){
							if(null!=emailTask.getToMailInfo().getPlanTime()){
								emailTask.setFailcode(null);
								emailTask.setStatus(StatusType.READY);
								eamilTaskDao.addEmailTask(emailTask);
							}else{
							    eamilTaskDao.addEmailTask(emailTask);
							}
						}else{
							eamilTaskDao.deleteEmailTask(emailTask);
						}
					}
					transaction.commit();
				}catch(Exception e){
					transaction.rollback();
					log.error(e);
				}
			}
			resultList= getTaskDB(paging);
		}while(null!=resultList && resultList.size()>0);
	}

	public static void main(String[] args) throws JobExecutionException {
		AutoSendMailJob job = new AutoSendMailJob();
		job.execute(null);
	}

	public synchronized List<EmailTask> getTaskDB(Paging paging) {
		List<EmailTask> resultList = eamilTaskDao.findByPlanTimeNull(StatusType.READY, "id", "ASC",paging);
		List<EmailTask> resultNotNull = eamilTaskDao.findByPlanTimeNotNull(StatusType.READY, "id", "ASC",paging);
		resultList.addAll(resultNotNull);
		TransactionStrategy transaction = HibernateTransactionStrategy.getInstance();
		transaction.begin();
		try{
			for(EmailTask emailTask :resultList){
				emailTask.setFunctionTime(new Date());
				emailTask.setStatus(StatusType.FUNCTION);
				eamilTaskDao.addEmailTask(emailTask);
			}
			transaction.commit();
			return resultList;
		}catch(Exception e){
			log.error(e);
			transaction.rollback();
		}
		return null;
	}
	
	
	
	public  EmailTask  sendMail(EmailTask emailTask){
		ISendMail sendmail = new SendCloudMail();
		//如果是易尔通邮件服务，则用原来的common-mail组件发送
		if ("7".equals(emailTask.getMailUserInfo().getClassify())) {
			sendmail = new SendMail();
		}
		
		try {
			
			if(null!=emailTask.getToMailInfo().getPlanTime()){
				if(emailTask.getToMailInfo().getPlanTime().before(new Date())){
					sendmail.sendMail(StringToArray(emailTask.getToMailInfo().getReceiver()), 
					          emailTask.getToMailInfo().getTitle(), 
					          emailTask.getToMailInfo().getMailContent(), 
					          StringToArray(emailTask.getToMailInfo().getAttachPath()), 
					          StringToArray(emailTask.getToMailInfo().getAttachDescription()), 
					          StringToArray(emailTask.getToMailInfo().getAttachName()), 
					          StringToArray(emailTask.getToMailInfo().getCopySend()), 
					          StringToArray(emailTask.getToMailInfo().getBlindSend()),
					          emailTask.getToMailInfo().getMailType().toString(), 
					          emailTask.getMailUserInfo().getClassify());
				}else{
					throw new UncheckedException(LanguageKey.ACCOUNT,"001");
				}
				
			}else{
				sendmail.sendMail(StringToArray(emailTask.getToMailInfo().getReceiver()), 
				          emailTask.getToMailInfo().getTitle(), 
				          emailTask.getToMailInfo().getMailContent(), 
				          StringToArray(emailTask.getToMailInfo().getAttachPath()), 
				          StringToArray(emailTask.getToMailInfo().getAttachDescription()), 
				          StringToArray(emailTask.getToMailInfo().getAttachName()), 
				          StringToArray(emailTask.getToMailInfo().getCopySend()), 
				          StringToArray(emailTask.getToMailInfo().getBlindSend()),
				          emailTask.getToMailInfo().getMailType().toString(), 
				          emailTask.getMailUserInfo().getClassify());
			}
			
		} catch (EmailException e) {
			e.printStackTrace();
			emailTask.setFailcode(e.toString());
		} catch (Exception e) {
			emailTask.setFailcode(e.toString());
		}
		return emailTask; 
	}
	
	public static String[] StringToArray(String str){
		List<String> ps =null;
		if(StringUtils.hasLength(str)){
			ps=new Gson().fromJson(str,new TypeToken<List<String>>(){}.getType());
			return ps.toArray(new String[ps.size()]);
		}
		return null;
	}
	
	@Test
	public  void  test(){
		AutoSendMailJob job = new AutoSendMailJob();
		job.execute(null);
	}

}
