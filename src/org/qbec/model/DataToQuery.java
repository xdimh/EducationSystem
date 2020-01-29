package org.qbec.model;

public class DataToQuery {
	
	private String province;
	private String city;
	private String[] attr;
	private String[] classType;
	private String[] lk;
	private String[] wk;
	
	
	public String getProvince() {
		return province;
	}
	public String getCity() {
		return city;
	}
	public String[] getAttr() {
		return attr;
	}
	public String[] getClassType() {
		return classType;
	}
	public String[] getLk() {
		return lk;
	}
	public String[] getWk() {
		return wk;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setAttr(String[] attr) {
		this.attr = attr;
	}
	public void setClassType(String[] classType) {
		this.classType = classType;
	}
	public void setLk(String[] lk) {
		this.lk = lk;
	}
	public void setWk(String[] wk) {
		this.wk = wk;
	}
}
