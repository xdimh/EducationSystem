package org.qbec.dao;

import org.hibernate.Session;
import org.qbec.interceptor.HibernateUtil;
import org.qbec.interceptor.LoginInfo;
import org.qbec.model.User;

public class DBSchoolDAO implements SchoolDAO{

	private Session sess = null;
	
	public DBSchoolDAO(Session sess)
	{
		this.sess = sess;
	}
	
	public int createUser(User u) {
		if(sess == null)
		{
			sess = HibernateUtil.getSessionFactory().openSession();
			sess.beginTransaction();
		}
		sess.save(u);
		sess.getTransaction().commit();
		sess.close();
		return u.getUid();
	}
	
	public LoginInfo authenticateUser(String username,String password) {
		if(sess == null)
		{
			sess = HibernateUtil.getSessionFactory().openSession();
			sess.beginTransaction();
		}
		
		User u = (User)sess.createQuery("from User where uname = '" + username + "' and upass = '" + password + "'").uniqueResult();
		sess.getTransaction().commit();
		sess.close();
		
		if(u == null)
			return null;
		else 
			return new LoginInfo(u.getUid(), u.getUname(), u.getUrole().isWriteable(), u.getUrole().isReadable());
	}

	public boolean exitedUserCheck(String username) {
		// TODO Auto-generated method stub
		if(sess == null)
		{
			sess = HibernateUtil.getSessionFactory().openSession();
			sess.beginTransaction();
		}
		User u = (User)sess.createQuery("from User where uname = '" + username + "'").uniqueResult();
		sess.getTransaction().commit();
		sess.close();
		if(u == null)
			return false;
		else 
			return true;
	}

}
