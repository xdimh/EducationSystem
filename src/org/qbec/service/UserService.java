package org.qbec.service;

import org.qbec.interceptor.LoginInfo;
import org.qbec.model.User;

public interface UserService {
	
	
	public int createUser(User u);
	public LoginInfo authenticateUser(String username,String password);
	public boolean exitedUserCheck(String username);
}
