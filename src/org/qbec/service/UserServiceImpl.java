package org.qbec.service;

import org.qbec.interceptor.LoginInfo;
import org.qbec.model.User;

public class UserServiceImpl implements UserService{

	private String username = null;
	PersistenceService ps = new DBPersistenceServiceImpl();
	
	public UserServiceImpl(){}
	public UserServiceImpl(String username)
	{
		this.username = username;
		if(username != null && !username.equals("")){
			ps = new DBPersistenceServiceImpl(username);
		}
	}
	
	public int createUser(User u) {
		return ps.getSchoolDAO().createUser(u);
	}
	
	public LoginInfo authenticateUser(String username, String password) {
		
		return ps.getSchoolDAO().authenticateUser(username, password);
		
	}
	public boolean exitedUserCheck(String username) {
		
		return ps.getSchoolDAO().exitedUserCheck(username);
	}
	
	

}
