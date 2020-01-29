package org.qbec.model;

public class UserRole {

	private  boolean readable = false;
	private  boolean writeable = false;
	private  User user;
	
	public boolean isReadable() {
		return readable;
	}
	public void setReadable(boolean readable) {
		this.readable = readable;
	}
	public boolean isWriteable() {
		return writeable;
	}
	public void setWriteable(boolean writeable) {
		this.writeable = writeable;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
