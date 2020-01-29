package org.qbec.action;

import org.qbec.interceptor.LoginInfo;
import org.qbec.service.DBPersistenceServiceImpl;
import org.qbec.service.UserServiceImpl;

import com.opensymphony.xwork2.ActionContext;

public class AuthenticationUserAction {

	private String username;
	private String password;
	private int resultCode;
	/*
	 * resultCode
	 * 1 user login success
	 * 2 user login failed
	 * 3 has a user who is called the value of username
	 * 4 this username is available
	 */
	
	public String validateUser()
	{
		LoginInfo loginInfo = new UserServiceImpl().authenticateUser(username, Md5EncryptionUtil.encryptionPassword(password));
		if(loginInfo != null)
		{
			ActionContext.getContext().getSession().put("loginInfo", loginInfo);
			setResultCode(1);
		}else{
			setResultCode(2);
		}
		return "validateuser";
	}
	
	
	public String existedCheck()
	{
		if(new UserServiceImpl().exitedUserCheck(username))
		{
			setResultCode(3);
		}else{
			setResultCode(4);
		}
		return "existedcheck";
	}
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}


	public int getResultCode() {
		return resultCode;
	}


	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}
	
	
}
