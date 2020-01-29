package org.qbec.service;

import org.hibernate.Session;
import org.qbec.dao.DBSchoolDAO;
import org.qbec.dao.SchoolDAO;

import com.opensymphony.xwork2.ActionContext;

public class DBPersistenceServiceImpl implements PersistenceService{

	private String username = null;
	private Session sess = null;
	
    public DBPersistenceServiceImpl()
    {
    	
    }
	
	public DBPersistenceServiceImpl(String username)
	{
		this.username = username;
		this.sess = (Session)ActionContext.getContext().getSession().get(username);
	}
	
	public SchoolDAO getSchoolDAO() {
		return new DBSchoolDAO(sess);
	}

}
