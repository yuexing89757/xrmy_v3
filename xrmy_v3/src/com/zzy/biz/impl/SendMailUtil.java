package com.zzy.biz.impl;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.mail.MultiPartEmail;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.zzy.dao.MailUserInfoDao;
import com.zzy.dao.ToMailInfoDao;
import com.zzy.dao.impl.MailUserInfoDaoImpl;
import com.zzy.dao.impl.ToMailInfoDaoImpl;
import com.zzy.model.MailUserInfo;
import com.zzy.util.Log;



public class SendMailUtil {
	private static final Log log = Log.getLogger(SendMailUtil.class);
	private static Map<String, String[]> mailInfos = new HashMap<String, String[]>();
	private static final String XML_PATH = SendMail.class.getClassLoader()
			                                 .getResource("").getPath()
			                                  + "mailSmtpPort.xml";
	static {
		try {
			log.info("SendMail -> loadXML");
			File f = new File(XML_PATH);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = null;
			builder = factory.newDocumentBuilder();
			Document doc = builder.parse(f);
			NodeList node = doc.getDocumentElement().getElementsByTagName("mailInfo");
			for (int i = 0; i < node.getLength(); i++) {
				String type = doc.getElementsByTagName("mailType").item(i)
						.getFirstChild().getNodeValue();
				String host = doc.getElementsByTagName("mailHost").item(i)
						.getFirstChild().getNodeValue();
				String sslValidate = doc.getElementsByTagName("sslValidate")
						.item(i).getFirstChild().getNodeValue();
				String tlsValidate = doc.getElementsByTagName("tlsValidate")
						.item(i).getFirstChild().getNodeValue();
				String mailSmtpPort = doc.getElementsByTagName("mailSmtpPort")
						.item(i).getFirstChild().getNodeValue();
				String[] info = new String[] { host, sslValidate, tlsValidate,
						mailSmtpPort };   //以上是获取host
				mailInfos.put(type, info); //放进mailinfos
			}
		} catch (Exception e) {
			log.error("SendMail LoadXML error:{0}", e);
		}
	}
	
	
	private MailUserInfoDao mailUserInfoDao;

	private ToMailInfoDao toMailInfoDao;

	
	public static void setEmailInfo(String from, MultiPartEmail email) {
		for (Iterator<Entry<String, String[]>> iterator = mailInfos.entrySet().iterator(); iterator.hasNext();) {
			Entry<String, String[]> entry = iterator.next();
			if (from.indexOf(entry.getKey()) >= 0) {
				String[] value = entry.getValue();
				email.setHostName(value[0]);
				boolean sslValidate = Boolean.valueOf(value[1]);
				boolean tslValidate = Boolean.valueOf(value[2]);
				email.setSSL(sslValidate);
				email.setTLS(tslValidate);
				email.setSmtpPort(Integer.valueOf(value[3]));
				break;
			}
		}
	}

	/**
	 * 
	 * getFromMailInfo(From the information obtained)
	 * 
	 * TODO(Automatically switch the user)
	 * 
	 * 通过类型获取可用邮箱
	 * @return User Information
	 * 
	 * MailInfo
	 */
	public MailUserInfo getFromMailInfo(String classify) {
		return getMailUserInfoDao().getAvailableMailUser(classify);
	}

	/***************************************************************************
	 * below are get, set methods
	 **************************************************************************/

	/**
	 * mailUserInfoDao
	 * 
	 * @return the mailUserInfoDao
	 * 
	 * @since codingExample Ver 1.0
	 */
	public MailUserInfoDao getMailUserInfoDao() {
		if (mailUserInfoDao == null) {
			mailUserInfoDao = new MailUserInfoDaoImpl();
		}
		return mailUserInfoDao;
	}

	public ToMailInfoDao getToMailInfoDao() {
		if (toMailInfoDao == null) {
			toMailInfoDao = new ToMailInfoDaoImpl();
		}
		return toMailInfoDao;
	}

	/**
	 * @param mailUserInfoDao
	 *            the mailUserInfoDao to set
	 */
	public void setMailUserInfoDao(MailUserInfoDao mailUserInfoDao) {
		this.mailUserInfoDao = mailUserInfoDao;
	}
}
