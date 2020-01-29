package org.qbec.model;

public class User {
	
	private int uid;
	private String uname;
	private String upass;
	private String uemail;
	private UserRole urole;
	
	public User()
	{
		
	}
	
	public User(String uname, String upass, String uemail, UserRole urole) {
		super();
		this.uname = uname;
		this.upass = upass;
		this.uemail = uemail;
		this.urole = urole;
	}
	
	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getUname() {
		return uname;
	}
	
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getUpass() {
		return upass;
	}
	
	public void setUpass(String upass) {
		this.upass = upass;
	}
	
	public String getUemail() {
		return uemail;
	}
	
	public void setUemail(String uemail) {
		this.uemail = uemail;
	}
	
	public UserRole getUrole() {
		return urole;
	}
	
	public void setUrole(UserRole urole) {
		this.urole = urole;
	}
	
}
