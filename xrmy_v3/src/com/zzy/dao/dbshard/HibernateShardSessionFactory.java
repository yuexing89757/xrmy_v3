package com.zzy.dao.dbshard;

import java.text.MessageFormat;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.zzy.dao.ShardInfoDao;
import com.zzy.dao.impl.ShardInfoDaoImpl;
import com.zzy.model.ShardInfo;


public class HibernateShardSessionFactory {
	private static final String shardConfigFile = "/config/hibernate/hibernate_node.cfg.xml";
	private static final String cfgPath = "/config/hibernate/hibernate.cfg.xml";
	public final static  String __URL = "jdbc:mysql://{0}:{1}/{2}?useUnicode=true&amp;characterEncoding=UTF-8&amp;mysqlEncoding=utf-8";
	public final static String __CONNECTION = "hibernate.connection.url";
	public final static String __USERNAME = "hibernate.connection.username";
	public final static String __PASSWORD = "hibernate.connection.password";
	
	private SessionFactory sessionFactory=null;
	private Configuration configuration = null;
	
	
	public  SessionFactory getSessionFactoryByAdvertiser(Long adId){
		if(!adId.equals(-1L)){
			sessionFactory = new Configuration().configure(cfgPath).buildSessionFactory();
		}else{
			ShardInfoDao shardInfoDao=new ShardInfoDaoImpl();
			ShardInfo shardInfo=shardInfoDao.findByAdId(adId);
			sessionFactory=newSessionFactory(shardInfo);
		}
		return sessionFactory;
	}
		
	private SessionFactory newSessionFactory(ShardInfo shardInfo) {
		String url = MessageFormat.format(__URL, shardInfo.getIp(), shardInfo.getPort(), shardInfo.getDbname());
		configuration=new Configuration();
		configuration.setProperty(__CONNECTION, url);
		configuration.setProperty(__USERNAME, shardInfo.getUser());
		configuration.setProperty(__PASSWORD, shardInfo.getPwd());
		configuration.configure(shardConfigFile);
		SessionFactory sessionFactory = configuration.buildSessionFactory();
		return sessionFactory;
	}

}
