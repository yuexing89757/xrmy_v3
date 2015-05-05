package com.zzy.biz.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.zzy.biz.EmailTaskBiz;
import com.zzy.dao.EmailTaskDao;
import com.zzy.dao.MailUserInfoDao;
import com.zzy.dao.ToMailInfoDao;
import com.zzy.dao.dbtool.HibernateTransactionStrategy;
import com.zzy.dao.dbtool.TransactionStrategy;
import com.zzy.dao.impl.EmailTaskDaoImpl;
import com.zzy.dao.impl.MailUserInfoDaoImpl;
import com.zzy.dao.impl.ToMailInfoDaoImpl;
import com.zzy.enums.MailType;
import com.zzy.enums.StatusType;
import com.zzy.model.EmailTask;
import com.zzy.model.MailUserInfo;
import com.zzy.model.ToMailInfo;
import com.zzy.util.ErrorBundler;
import com.zzy.util.LanguageKey;
import com.zzy.util.Log;
import com.zzy.util.ObjectUtils;
import com.zzy.util.Paging;
import com.zzy.util.StringUtils;
import com.zzy.util.UncheckedException;
import com.zzy.view.EmailTaskView;
import com.zzy.view.Result;


public class EmailTaskBizImpl implements EmailTaskBiz{
	private Log log = Log.getLogger(MailUserInfoBizImpl.class);
	EmailTaskDao eamilTaskDao = new EmailTaskDaoImpl();
	ToMailInfoDao toMailInfoDao=new ToMailInfoDaoImpl();
	MailUserInfoDao mailUserInfoDao=new MailUserInfoDaoImpl();
	ErrorBundler errorBundler=new ErrorBundler("CN");
	
	public Result<Boolean> addEmailTask(String[] toMail, String title, String mailConent,
			                    String[] attachPath, String[] attachDescription,
			                    String[] attachName, String[] Cc, String[] Bcc, 
			                    String mailType, String classify ,String planTime) {
		    Result<Boolean> result=new Result<Boolean>();
	   try {
		    MailUserInfo  mailUser=null;
            ToMailInfo toMailInfo=new ToMailInfo();
            
		       if(StringUtils.hasLength(classify)){
		    	   mailUser=mailUserInfoDao.getAvailableMailUser(classify); 
				   toMailInfo.setSender(mailUser.getEmailAddress());
		       }else{
		    	   throw new UncheckedException("classify is null","classify is null");
		       }
		      
		       if(!ObjectUtils.isEmpty(toMail)){
		    	   toMailInfo.setReceiver(new Gson().toJson(toMail));
		       }else{
		    	   throw new UncheckedException("toMail is null","toMail is null");
		       }
		       
		      if(StringUtils.hasLength(mailType)){
		    	  toMailInfo.setMailType(MailType.get(mailType));
		      }else{
		    	   throw new IllegalArgumentException("mailType is null");
		      }
		      
		      if(StringUtils.hasLength(planTime)){
		    	  Long time=Long.parseLong(planTime);
		    	  toMailInfo.setPlanTime(new Date(time));
		      }
			   
			   toMailInfo.setMailContent(mailConent);
			   
			   if(!ObjectUtils.isEmpty(Cc))   toMailInfo.setCopySend(new Gson().toJson(Cc));
			   if(!ObjectUtils.isEmpty(Bcc))   toMailInfo.setBlindSend(new Gson().toJson(Bcc));
			   if(!ObjectUtils.isEmpty(attachPath))  toMailInfo.setAttachPath(new Gson().toJson(attachPath));
			   if(!ObjectUtils.isEmpty(attachDescription))  toMailInfo.setAttachDescription(new Gson().toJson(attachDescription));
			   if(!ObjectUtils.isEmpty(attachName))  toMailInfo.setAttachName(new Gson().toJson(attachName));
			   
			   toMailInfo.setTitle(title);
			   toMailInfo.setSendTime(new Date());
			   
			    TransactionStrategy transaction = HibernateTransactionStrategy.getInstance();
				transaction.begin();
				try {
				    toMailInfoDao.addToMailInfo(toMailInfo);
				    EmailTask emailTask=new EmailTask();
				    emailTask.setCreateTime(new Date());
				    emailTask.setMailUserInfo(mailUser);
				    emailTask.setToMailInfo(toMailInfo);
				    emailTask.setStatus(StatusType.READY);
				    result.setData(eamilTaskDao.addEmailTask(emailTask));
					transaction.commit();
				} catch (Exception e) {
					log.error("addEmailTask error" + e.getMessage());
					transaction.rollback();
					result.setData(false);
					errorBundler.setErrorInfo(result, LanguageKey.ACCORDING);
				} finally {
					transaction.close();
				}
				
	   }catch (UncheckedException ex) {
		   result.setData(false);
			log.error(ex);
			this.errorBundler.setErrorInfo(result, ex);
		} catch (Exception e) {
			result.setData(false);
			log.error(e);
			this.errorBundler.setErrorInfo(result, LanguageKey.ACCORDING);
		}
		return result;
	}


	public List<EmailTaskView> getEmailTask(StatusType status,String sortColunn, String sortDir, Paging paging) {
		List<EmailTask> resultList= eamilTaskDao.findByPage(status, sortColunn, sortDir, paging);
		EmailTaskView emailTaskView=new EmailTaskView();
		List<EmailTaskView> viewList=new ArrayList<EmailTaskView>();
		for(EmailTask emailTask:resultList){
			converToView(emailTask,emailTaskView);
			viewList.add(emailTaskView);
		}
		return viewList;
	}
	
	public  void converToView(EmailTask emailTask,EmailTaskView emailTaskView){
		emailTaskView.setMailStatus(emailTask.getStatus());
		emailTaskView.setFailCode(emailTask.getFailcode());
		emailTaskView.setCreateTime(emailTask.getCreateTime());
		emailTaskView.setFunctionTime(emailTask.getFunctionTime());
		emailTaskView.setPlatform(emailTask.getMailUserInfo().getPlatformName());
		emailTaskView.setSender(emailTask.getMailUserInfo().getEmailAddress());
		emailTaskView.setReceiver(emailTask.getToMailInfo().getReceiver());
	}

	
	public static String  ArrayToString(String[] arraystr){
		StringBuffer strLine=new StringBuffer("");
		for(String str:arraystr){
			strLine=strLine.append(str+StringUtils.COMMA);
		}
		return strLine.length()>0?(strLine.toString()).substring(0,strLine.length() - 1):strLine.toString();
	}
	


}
