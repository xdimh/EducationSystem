package org.qbec.interceptor;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.apache.log4j.Logger;


public class HibernateUtil {
	private static final SessionFactory sessionFactory;
	private static final Logger LOG = Logger.getLogger(HibernateUtil.class);

	static {
		try{
			LOG.debug("HibernateUtil.static - loading config");
			sessionFactory = new Configuration().configure().buildSessionFactory();
			LOG.debug("HibernateUtil.static - end");
		}catch(HibernateException ex){
			throw new RuntimeException("创建SessionFactory失败: " + ex.getMessage(), ex);
		}
	}
	
	  public static SessionFactory getSessionFactory() {
	        return sessionFactory;
	    }
}