package org.qbec.interceptor;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class AuthenticationInterceptor implements Interceptor {
	private static final Logger log = Logger.getLogger(AuthenticationInterceptor.class);


	
	public String intercept(ActionInvocation invocation) throws Exception {
	   
	        LoginInfo loginInfo = (LoginInfo) ActionContext.getContext().getSession().get("loginInfo");
	        
	        if (null == loginInfo) {
	            log.debug("user is not authentic and redirected to login.");
	            ActionContext.getContext().put("sInvalidateMsg", "会话无效或账户被重新登录！");// 用于告知页面提示信息
	            return "NOT_LOGIN";
	        }else{
	            return invocation.invoke();
	        }
	        
	    }
	    
	
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void init() {
		// TODO Auto-generated method stub
		
	}


}
