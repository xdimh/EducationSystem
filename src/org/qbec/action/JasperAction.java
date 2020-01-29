package org.qbec.action;

import java.sql.Connection;
import java.util.HashMap;

import org.qbec.dao.DB;

import com.opensymphony.xwork2.ActionSupport;

public class JasperAction extends ActionSupport {
	private static final long serialVersionUID = 1L;

	private Connection conn;
	private int sid;
	private HashMap<String,Integer> param = new HashMap<String,Integer>();
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Connection getConn() {
		return conn;
	}
	public int getSid() {
		return sid;
	}
	public HashMap<String, Integer> getParam() {
		return param;
	}
	public void setConn(Connection conn) {
		this.conn = conn;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public void setParam(HashMap<String, Integer> param) {
		this.param = param;
	}

	public String execute() throws Exception {
			param.put("sid", sid);
			conn = DB.getConn();
			
	        return SUCCESS;
	    } 
}