package org.qbec.action;


import org.qbec.interceptor.LoginInfo;
import org.qbec.model.User;
import org.qbec.model.UserRole;
import org.qbec.service.UserServiceImpl;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class RegisterAction extends ActionSupport{

	private String username;
	private String password;
	private String useremail;
	
	
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
	
	public String getUseremail() {
		return useremail;
	}
	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}
	@Override
	public String execute() throws Exception {
		
		setPassword(Md5EncryptionUtil.encryptionPassword(getPassword()));
		System.out.println(getPassword());
		System.out.println(getPassword().length());
		
		User u = new User(username, password, useremail, new UserRole());
		int uid = new UserServiceImpl(username).createUser(u);
		LoginInfo loginInfo = new LoginInfo(uid,username);
		ActionContext.getContext().getSession().put("loginInfo", loginInfo);
		
		return "home";
	}
	
	
	
	
	
}
