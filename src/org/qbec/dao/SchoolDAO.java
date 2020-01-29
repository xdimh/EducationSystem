package org.qbec.dao;

import org.qbec.interceptor.LoginInfo;
import org.qbec.model.User;

public interface SchoolDAO {

	
	public int createUser(User u);
	public LoginInfo authenticateUser(String username,String password);
	public boolean exitedUserCheck(String username);
}
