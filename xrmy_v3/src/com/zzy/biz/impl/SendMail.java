/**    
 * Project name:ads-mail
 *
 * Copyright Pzoomtech.com 2011, All Rights Reserved.
 *  
 */
package com.zzy.biz.impl;
import java.net.URL;
import java.util.Date;

import javax.mail.internet.MimeUtility;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;

import com.zzy.biz.ISendMail;
import com.zzy.enums.MailType;
import com.zzy.model.MailParams;
import com.zzy.model.MailUserInfo;
import com.zzy.model.ToMailInfo;
import com.zzy.util.BASE64Encoder;
import com.zzy.util.Log;


/**
 * @name SendMail
 * 
 * @description CLASS_DESCRIPTION
 * 
 * @version 1.0
 */
public class SendMail implements ISendMail {
	private static final Log log = Log.getLogger(SendMail.class);
	SendMailUtil sendMailUtil = new SendMailUtil();

	/**
	 * sendText(send email)
	 * 
	 */
	//2014.3.6修改  //有用
	private boolean sendText(MailParams mailParams) throws EmailException,
			Exception {
		log.info("SendMail -> sendText");
		MailUserInfo mailUserInfo = mailParams.getMainInfo();    //测试
		if (mailUserInfo == null) {
			throw new EmailException("mailUserInfo cast be not null");
		}
		try {
			/* Send a HtmlEmail new object */
			MultiPartEmail email = new MultiPartEmail();
			String from = mailUserInfo.getEmailAddress();
			SendMailUtil.setEmailInfo(from, email);
			String userName = mailUserInfo.getUserName();
			email.setAuthentication(from, mailUserInfo.getPassword()); // User
			email.setFrom(from, userName);
			email.setCharset("utf-8");
			ToMailInfo toMailInfo = null;
			String title = mailParams.getTitle();
			String mailContent = mailParams.getMailContent();
			String attachPath = "";
			String attachDescription = "";
			String attachName = "";

			// 添加附件
			if (mailParams.getAttachPath() != null && mailParams.getAttachPath().length > 0) {
				for (int i = 0; i < mailParams.getAttachPath().length; i++) {
					String path = mailParams.getAttachPath()[i];
					String description = mailParams.getAttachDescription()[i];
					String name = mailParams.getAttachName()[i];
					EmailAttachment attachment = new EmailAttachment();
					attachment.setPath(path);
					attachment.setDisposition(EmailAttachment.ATTACHMENT);
					attachment.setDescription(description);
					attachment.setName(MimeUtility.encodeText(new String(name.getBytes(), "utf-8"), "utf-8", "B"));
					email.attach(attachment);
					attachPath += (path + ",");
					attachDescription += (description + ",");
					attachName += (name + ",");
				}
			}else{
				attachPath = ",";
				attachDescription = ",";
				attachName = ",";
			}
			// 添加抄送人员
			if (mailParams.getCc() != null) {
				for (String cc : mailParams.getCc()) {
					email.addCc(cc);
				}
			}
			// 添加密送人员
			if (mailParams.getBcc() != null) {
				for (String bcc : mailParams.getBcc()) {
					email.addBcc(bcc);
				}
			}
			// 添加发送邮件信息
			for (String to : mailParams.getToMail()) {
				email.addTo(to);
				Date sendTime = new Date();
				toMailInfo = new ToMailInfo(from, to, title,mailParams.getMailType(), mailContent, 
						attachPath.substring(0, attachPath.length() - 1),
						attachDescription.substring(0, attachDescription.length() - 1), 
						attachName.substring(0,attachName.length() - 1), 
						sendTime);
				sendMailUtil.getToMailInfoDao().addToMailInfo(toMailInfo);  //保存信息
			}
			email.setSubject(title);
			email.setMsg(mailContent);
			email.send();
			return true;
		} catch (EmailException e) {
			for (String toMail : mailParams.getToMail()) {
				log.error("toMail:" + toMail);
			}
			log.error("SendMail ->  classify:"+ mailUserInfo.getClassify());
			log.error("SendMail -> sendText :" + e);
			throw e;
		} catch (Exception e) {
			log.error("SendMail -> sendText :" + e);
			throw e;
		}
	}

	/**
	 * sendHtml(send email)
	 *
	 * boolean
	 */
	//有用
	private boolean sendHtml(MailParams mailParams) throws EmailException,
			Exception {
		log.info("SendMail -> sendHtml");
		MailUserInfo mailUserInfo = mailParams.getMainInfo();
		if (mailUserInfo == null) {
			throw new EmailException("mailUserInfo cast be not null");
		}
		try {
			HtmlEmail email = new HtmlEmail();
			String from = mailUserInfo.getEmailAddress();
			SendMailUtil.setEmailInfo(from, email); //设置host
			String userName = mailUserInfo.getUserName();
			email.setAuthentication(from, mailUserInfo.getPassword()); // User
			email.setFrom(from, userName);
			email.setCharset("utf-8");
			
			ToMailInfo toMailInfo = null;
			String title = mailParams.getTitle();
			String mailContent = mailParams.getMailContent();
			String attachPath = "";
			String attachDescription = "";
			String attachName = "";
			if (mailParams.getAttachPath() != null && mailParams.getAttachPath().length > 0) {
				for (int i = 0; i < mailParams.getAttachPath().length; i++) {
					String path = mailParams.getAttachPath()[i];
					String description = "";
					if (mailParams.getAttachDescription() != null) {
						description = mailParams.getAttachDescription()[i];
					}
					String name = mailParams.getAttachName()[i];
					EmailAttachment attachment = new EmailAttachment();
					attachment.setURL(new URL(path));
					attachment.setDisposition(EmailAttachment.ATTACHMENT);
					attachment.setDescription(description);
					name = "=?UTF-8?B?"+ BASE64Encoder.encode(name.getBytes("utf-8"))+ "?=";
					attachment.setName(name);
					log.info("sendMail attachment name end :{0}", attachment.getName());
					email.attach(attachment);
					attachPath += (path + ",");
					attachDescription += (description + ",");
					attachName += (name + ",");
				}
			}else{
				attachPath = ",";
				attachDescription = ",";
				attachName = ",";
			}
			// 添加抄送人员
			if (mailParams.getCc() != null) {
				for (String cc : mailParams.getCc()) {
					email.addCc(cc);
				}
			}
			// 添加密送人员
			if (mailParams.getBcc() != null) {
				for (String bcc : mailParams.getBcc()) {
					email.addBcc(bcc);
				}
			}
			// 添加发送邮件信息
			for (String to : mailParams.getToMail()) {
				email.addTo(to);
				Date sendTime = new Date();
				toMailInfo = new ToMailInfo(from, to, title,mailParams.getMailType(), mailContent,
						attachPath.substring(0, attachPath.length() - 1),
						attachDescription.substring(0, attachDescription.length() - 1), 
						attachName.substring(0,attachName.length() - 1), 
						sendTime);
				sendMailUtil.getToMailInfoDao().addToMailInfo(toMailInfo);   //保存信息
			}
			email.setSubject(title);
			
			if (!"".equals(mailContent) && null != mailContent) {   //判断邮件类型
				email.setMsg(mailContent);
			}
			if (mailParams.getHtmlContext() != null) {
				email.setHtmlMsg(mailParams.getHtmlContext());
			}
			try {
				email.send();   //发送邮件
				return true;
			} catch (EmailException e) {
				for (String toMail : mailParams.getToMail()) {
					log.error("toMail:" + toMail);
				}
				log.error("SendMail ->  classify:"+ mailUserInfo.getClassify()+" sendHtml EmailException :"+ e  );
				throw e;
			}		

		} catch (Exception e) {
			log.error("SendMail -> sendHtml classify:" + mailUserInfo.getClassify()+" Exception :" + e);
			throw e;
		}
	}

	


	//发邮件主方法
	public boolean sendMail(String[] toMail, String title, String mailConent,
			String[] attachPath, String[] attachDescription,
			String[] attachName, String[] Cc, String[] Bcc,
			String mailType, String classify) throws EmailException, Exception {
	      	log.debug("SendMail -> sendMail parameters : toMail={0},title={1},mailConent={2},attachPath={3},"
								+ "attachDescroption={4},attachName={5},mailType={6},Cc={8},Bcc={9}",
						toMail, title, mailConent, attachPath,
						attachDescription, attachName, mailType, 
						Cc, Bcc);
		boolean result = false;
		try {
			MailUserInfo mailInfo = sendMailUtil.getMailUserInfoDao().getAvailableMailUser(classify);  //3.7已经改
			MailParams mailParams = new MailParams(mailInfo, toMail, title,
					mailConent, attachPath, attachDescription, attachName);
			//mailParams.setHtmlContext(htmlContext);
			mailParams.setCc(Cc);
			mailParams.setBcc(Bcc);
			MailType type = MailType.get(mailType);
			log.info("type is:",type);
			if (type == null) {
				log.error("SendMail -> sendMail MailType is null !!!");
				throw new Exception("sendMail MailType is null !!!");
			}
			mailParams.setMailType(type);
			switch (type) {
			case HTML:
				result = sendHtml(mailParams);  //发送html
				break;
			case TEXT:
				result = sendText(mailParams);  //发送邮件
				break;
			}
		} catch (Exception e) {
			log.error("SendMail -> sendMail " + e);
		} 
		return result;
	}

}
