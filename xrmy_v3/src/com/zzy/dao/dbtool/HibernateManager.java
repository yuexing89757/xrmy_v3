package com.zzy.dao.dbtool;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.cfg.Configuration;
import org.hibernate.stat.Statistics;

@SuppressWarnings("unchecked")
public class HibernateManager {
	private static final String cfgPath = "/config/hibernate/hibernate.cfg.xml";
	private static final SessionFactory sessionFactory;
	private static final ThreadLocal<Session> localSession;

	static {
		try {
			sessionFactory = new Configuration().configure(cfgPath).buildSessionFactory();
		} catch (Throwable ex) {
			throw new ExceptionInInitializerError(ex);
		}
		localSession = new ThreadLocal<Session>();
	}

	//获取sessionFactory
	public static SessionFactory getSessionfactory() {
		return sessionFactory;
	}
 
	//获取当前session
	public static synchronized Session getCurrentSession() {

		if (localSession.get() == null) {
			return openSession();
		}
		return (Session) localSession.get();
	}

	//获取当前session
	public static synchronized Session openSession() {
		Session session = (Session) localSession.get();

		if ((session != null) && (session.isOpen())) {
			return session;
		}

		localSession.set(sessionFactory.openSession());

		return (Session) localSession.get();
	}

	
	
	public static Statistics getStatistics() {
		return sessionFactory.getStatistics();
	}

	public static synchronized void close() {
		if (localSession.get() != null)
			((Session) localSession.get()).close();
	}

	public static synchronized StatelessSession openStatelessSession() {
		return sessionFactory.openStatelessSession();
	}



}