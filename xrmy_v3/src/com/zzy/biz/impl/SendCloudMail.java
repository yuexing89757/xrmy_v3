package com.zzy.biz.impl;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.mail.EmailException;

import com.sohu.sendcloud.Message;
import com.sohu.sendcloud.SendCloud;
import com.zzy.biz.ISendMail;
import com.zzy.enums.MailType;
import com.zzy.model.MailParams;
import com.zzy.model.MailUserInfo;
import com.zzy.util.Log;

public class SendCloudMail implements ISendMail {
	private static final Log log = Log.getLogger(SendCloudMail.class);
	SendMailUtil sendMailUtil = new SendMailUtil();

	
	//发送souhu邮件
	public boolean sendMail(String[] toMail, String title, String mailConent,
			String[] attachPath, String[] attachDescription,
			String[] attachName, String[] Cc, String[] Bcc,
			String mailType, String classify) throws EmailException, Exception {
		log.info("SendClounMail -> sendMail");
		log.debug("SendClounMail-> SendMail ->parameters : toMail={0},title={1},mailConent={2},attachPath={3},"
								+ "attachDescroption={4},attachName={5},mailType={6},Cc={8},Bcc={9}",
						toMail, title, mailConent, attachPath,
						attachDescription, attachName, mailType, 
						Cc, Bcc);
		
		boolean result = false;
		try {
			MailUserInfo mailInfo = sendMailUtil.getFromMailInfo(classify);//3.7获取可用邮箱
			MailParams mailParams = new MailParams(mailInfo, toMail, title,
					mailConent, attachPath, attachDescription, attachName);     //由邮箱参数  和邮件参数组成
			//mailParams.setHtmlContext(htmlContext);
			mailParams.setCc(Cc);
			mailParams.setBcc(Bcc);  //邮件参数放进MailParams
			MailType type = MailType.get(mailType);
			if (type == null) {
				log.error("SendCloudMail -> sendMail: MailType is null !!!");
				throw new Exception("SendCloudMail-> sendMail: MailType is null !!!");
			}
			mailParams.setMailType(type);//邮件参数放进MailParams
			sendHtmlOrTextMail(mailParams);  //发送邮件
			result = true;
		} catch (Exception e) {
			log.error("SendCloudMail -> sendMail: " + e.toString());
			throw e;
		} 
		return result;
	}

	

	
	//发送邮件 
	public void sendHtmlOrTextMail(MailParams mailParams) throws Exception {
		log.info("SendCloudMail -> sendHtmlOrTextMail");
		MailUserInfo mailUserInfo = mailParams.getMainInfo();
		if (mailUserInfo == null) {
			throw new EmailException("mailUserInfo cast be not null");
		}
		//参数处理
		try {
				// 初始化 message Info
				Message message = new Message(mailParams.getMainInfo().getEmailAddress(), 
						                               mailParams.getMainInfo().getUserName()); //创建一个邮件
				String mailContent = StringUtils.isEmpty(
					mailParams.getHtmlContext()) ? mailParams.getMailContent():mailParams.getHtmlContext(); //邮件内容
				message.setSubject(mailParams.getTitle());  //标题
				message.setBody(mailContent);  //内容
				List<String> toList = new ArrayList<String>();  //收件人们
				List<String> ccList = new ArrayList<String>();  //抄件人们
				List<String> bccList = new ArrayList<String>();  //密件人们
				if (mailParams.getToMail() != null && mailParams.getToMail().length > 0) {
					for (String to : mailParams.getToMail())
						  toList.add(to);
					    message.addRecipients(toList);  //收件人们
				}
				if (mailParams.getCc() != null && mailParams.getCc().length > 0) {
					for (String cc : mailParams.getCc())
						ccList.add(cc);
					message.addCcs(ccList);//抄件人们
				}
				if (mailParams.getBcc() != null && mailParams.getBcc().length > 0) {
					for (String bcc : mailParams.getBcc())
						bccList.add(bcc);
					message.addBccs(bccList);//密件人们
				}
		  
			//获取发件io流
			try{
				    SSLContext sc = SSLContext.getInstance("SSL");
			        sc.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());
				//添加多个附件代码	
				if (mailParams.getAttachPath() != null
						&& mailParams.getAttachPath().length > 0
						&& mailParams.getAttachName() != null
						&& mailParams.getAttachName().length > 0) {
					 for(int index = 0;index<mailParams.getAttachPath().length;index++){
						    String urlAddress=mailParams.getAttachPath()[index];
							URL url = new URL(urlAddress.replace(" ", ""));
							URLConnection conn = url.openConnection();
							if(urlAddress.startsWith("https:")){
						        ((HttpsURLConnection) conn).setSSLSocketFactory(sc.getSocketFactory());
						        ((HttpsURLConnection) conn).setHostnameVerifier(new TrustAnyHostnameVerifier()); 
					         }
							InputStream in = conn.getInputStream();
							String fileName=new String(mailParams.getAttachName()[index].getBytes(),"UTF-8");
							message.addAttachment(fileName, in, "");
						}
				  }
			}catch(Exception e){
				log.error("AttachPath:" + Arrays.toString(mailParams.getAttachPath())+
						"--AttachName"+Arrays.toString(mailParams.getAttachDescription())+
						"--AttachDiscription"+Arrays.toString(mailParams.getAttachDescription()));
				log.error("SendCloudMail -> sendHtmlOrTextMail -> AttachException  113-122:" + e.toString()+ " classify:" + mailUserInfo.getClassify());
				throw e;
			}
			  ///发送邮件
			  try{
					SendCloud sendCloud = new SendCloud(mailParams.getMainInfo().getSmtpUserName(),mailParams.getMainInfo().getPassword());
					sendCloud.setMessage(message);
					sendCloud.setConnectionTimeout(10000l);
					sendCloud.setSocketTimeout(180000l); // 设为180秒
					sendCloud.send();   
				}catch(Exception e){
					log.error("SendCloudMail -> sendHtmlOrTextMail -> sendAction  134-139:" + e.toString()+ " classify:" + mailUserInfo.getClassify());
					throw e;	
				}
			/*//保存发件记录
			  try{
					  for (String to : mailParams.getToMail()) {
						Date sendTime = new Date();
						ToMailInfo toMailInfo = new ToMailInfo(mailParams.getMainInfo().getUserName(), to, mailParams.getTitle(), 
								        StringUtils.isEmpty(mailParams.getHtmlContext()) ? 
								        		 (mailParams.getMailContent().length()>3000?mailParams.getMailContent().substring(0, 3000):mailParams.getMailContent())
								        		:(mailParams.getHtmlContext().length()>3000?mailParams.getHtmlContext().substring(0, 3000):mailParams.getHtmlContext()), //内容
										Arrays.toString(mailParams.getAttachPath()),  
										Arrays.toString(mailParams.getAttachDescription()),
										Arrays.toString(mailParams.getAttachName()), sendTime);
						    sendMailUtil.getToMailInfoDao().addToMailInfo(toMailInfo);  //存数据
					  }
			  }catch (Exception e) {
					log.error("toMail:" + Arrays.toString(mailParams.getToMail()));
					log.error("SendCloudMail -> sendHtmlOrTextMail ->savelog 146-152:" + e.toString()+ " classify:" + mailUserInfo.getClassify());
					throw e;
			  }*/
		} catch (Exception e) {
			log.error("toMail:" + Arrays.toString(mailParams.getToMail()));
			log.error("SendCloudMail -> sendHtmlOrTextMail ->total:" + e.toString()+ " classify:" + mailUserInfo.getClassify());
			throw e;
		}  
	}
	
	//https认证方法
	private static class TrustAnyTrustManager implements X509TrustManager {
	    
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
    
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
    
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }
    //https认证通过
    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
}
