package com.zzy.dao.dbshard;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.zzy.util.UncheckedException;


public class HibernateManagerShard {

	private static final ThreadLocal<Map<Long,Session>> localSession = new ThreadLocal<Map<Long,Session>>();

	/**
	 * 
	 * 获取ThreadLocal中的session.
	 */
	public static synchronized Session getCurrentSession(){
		return getCurrentSessionByAdvertiserId(null);
	}
	
	/**
	 * 获取LocalSession map中的第一个key
	 * @return
	 */
	public static Long getFirstKeyFromLocalSession(){
		Map<Long,Session> threadMap = localSession.get();
		return threadMap.entrySet().iterator().next().getKey();
	}
	
	/**
	 * 根据广告商id 获取不同sessionFactory下的session.
	 */
	public static Session getCurrentSessionByAdvertiserId(Long advertiserId) {
		if(advertiserId == null && localSession.get() == null){
			Map<Long,Session> threadMap = new HashMap<Long,Session>();
			threadMap.put(-1L,new  HibernateShardSessionFactory().getSessionFactoryByAdvertiser(-1L).openSession());
			localSession.set(threadMap);
			return threadMap.get(-1L);
		}
		Map<Long,Session> threadMap = localSession.get();
		Session session = null;
		if(advertiserId == null){
			session = threadMap.values().iterator().next();
			if(session != null && session.isOpen()){
				return session;
			}else{
				throw new UncheckedException("advertiserId is null and session is null or session is closed.","aaa");
			}
		}else if(threadMap == null){
			threadMap = new HashMap<Long,Session>();
			threadMap.put(advertiserId, new HibernateShardSessionFactory().getSessionFactoryByAdvertiser(advertiserId).openSession());
			session =  threadMap.get(advertiserId);
		}else{
			session = threadMap.get(advertiserId);
			if(session == null || !session.isOpen()){
				threadMap.put(advertiserId, new HibernateShardSessionFactory().getSessionFactoryByAdvertiser(advertiserId).openSession());
				session = threadMap.get(advertiserId);
			}else{
				return session;
			}
		}
		synchronized (localSession) {
			localSession.set(threadMap);
		}
		return session;
	}
	
	

	
	public static synchronized void closeAll(){
		Set<Entry<Long,Session>> entries = localSession.get().entrySet();
		for(Entry<Long,Session> entry : entries){
			Session session = entry.getValue();
			if(session != null && session.isOpen()){
				session.clear();
				session.close();
			}
		}
		
	}
	
	public static synchronized void closeByAdvertiserId(Long advertiserId) {
		Map<Long,Session> threadMap = localSession.get();
		Session session = null;
		if(advertiserId == null){
			session = threadMap.values().iterator().next();
			session.clear();
			session.close();
		}else{
			session = threadMap.get(advertiserId);
			session.clear();
			session.close();
		}
	}
	
	
	public static synchronized void close() {
		closeByAdvertiserId(null);
	}
	

}


class CoreManager{
	private Session session = null;
	private SessionFactory sessionFactory= null;
	public CoreManager(Session session,SessionFactory sessionFactory){
		this.session = session;
		this.sessionFactory = sessionFactory;
	}
	public CoreManager(){}
	public Session getSession() {
		return session;
	}
	public void setSession(Session session) {
		this.session = session;
	}
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
}
