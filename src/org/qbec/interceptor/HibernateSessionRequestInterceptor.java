package org.qbec.interceptor;

import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;



public class HibernateSessionRequestInterceptor implements Interceptor {

	private SessionFactory sf = null;
	
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void init() {
		
		sf = HibernateUtil.getSessionFactory();
		
	}

	public String intercept(ActionInvocation invocation) throws Exception {
		
		Session sPerUser = null;
		Map session = invocation.getInvocationContext().getSession();
		String username = ((LoginInfo)session.get("loginInfo")).getUsername();
		if(session.get(username) == null) {
		  sPerUser = sf.openSession();
		  if(!sPerUser.getTransaction().isActive()){
			  		sPerUser.beginTransaction();
		  }
		  session.put(username,sPerUser);
	    }
	 
		System.out.println("execute before action");
		
		try {
			return invocation.invoke();
		}catch(Exception e)
		{
			if(session.get(username) != null){
		    	sPerUser = (Session)session.get(username);
		    	Transaction tx = sPerUser.getTransaction();
			      if(tx != null && tx.isActive()) {
			        tx.rollback();
			      }
			}
			return Action.ERROR;
		}finally {
			if(sPerUser != null)
			{
				System.out.println("session is closing....");
				sPerUser.getTransaction().commit();
				sPerUser.close();
			}
		}
	    
	 }
}