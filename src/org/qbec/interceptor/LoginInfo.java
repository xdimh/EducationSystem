package org.qbec.interceptor;

public class LoginInfo {

	private int uid;
	private String username;
	private boolean Admin = false;
	private boolean readable = true;
	
	
	public LoginInfo(int uid, String username) {
		this.uid = uid;
		this.username = username;
	}
	
	public LoginInfo(int uid, String username, boolean admin, boolean readable) {
		this.uid = uid;
		this.username = username;
		Admin = admin;
		this.readable = readable;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public boolean isAdmin() {
		return Admin;
	}
	public void setAdmin(boolean admin) {
		Admin = admin;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public boolean isReadable() {
		return readable;
	}

	public void setReadable(boolean readable) {
		this.readable = readable;
	}
	
}
