package com.zzy.dao.dbtool;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
  private static  Configuration config;
  private static SessionFactory factory;
  static{
	  config=new Configuration();
	  config.configure();
	  factory=config.buildSessionFactory();
  }
  
	  public static Configuration getConfig() {
			return config;
		}
		public static SessionFactory getFactory() {
			return factory;
		}
		public static Session getSession(){
			return factory.openSession();
		}
	  
		public static void closeSF(){
			if(factory!=null){
				if(!factory.isClosed()){
					factory.close();
				}
			}
			
		}
 
}
