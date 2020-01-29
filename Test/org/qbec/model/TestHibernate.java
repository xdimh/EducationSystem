package org.qbec.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.BeforeClass;
import org.junit.Test;


public class TestHibernate {

	private static SessionFactory sf = null;
	@BeforeClass
	public static void initSf()
	{
		sf = new Configuration().configure().buildSessionFactory();
	}
	@Test
	
	public void createTable()
	{
		User u = new User();
		UserRole ur = new UserRole();
		ur.setReadable(true);
		u.setUname("zzy");
		u.setUpass("md5");
		u.setUemail("zzy7186@163.com");
		u.setUrole(ur);
		
		Session s = sf.openSession();
		s.beginTransaction();
		s.save(u);
		s.getTransaction().commit();
		s.close();
		
	}
	
	@Test
	public void testInteger()
	{
		int i = 111111;
		System.out.println(Integer.toHexString(i));
	}
	
}
