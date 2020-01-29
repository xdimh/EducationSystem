package org.qbec.action;


public class QBEducationCounselAction {
	
	private String whichJsp;
	
	public String getWhichJsp() {
		return whichJsp;
	}

	public void setWhichJsp(String whichJsp) {
		this.whichJsp = whichJsp;
	}

	public String login()
	{
		return "login";
	}
	
	public String home()
	{
		return "home";
	}
	
	public String onClickMenu()
	{
		return whichJsp;
	}
	
	public String schoolInfo() {
		return "sinfo";
	}
	
}
