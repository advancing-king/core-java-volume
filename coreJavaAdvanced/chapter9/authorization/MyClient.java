package com.corejava.chapter9.authorization;

import java.security.PrivilegedAction;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import com.corejava.chapter9.authentication.MyCallbackHandler;

public class MyClient
{
	 public static void main(String argv[]) 
	 {  
	    LoginContext ctx = null;  
	    try {  
	      ctx = new LoginContext("WeatherLogin", new MyCallbackHandler());  
	    } catch(LoginException le) 
	    {  
	      System.err.println("LoginContext cannot be created. "+ le.getMessage());  
	      System.exit(-1);  
	    } catch(SecurityException se) 
	    {  
	      System.err.println("LoginContext cannot be created. "+ se.getMessage());  
	    }
	    
	    try 
	    {  
	      ctx.login();  
	    } catch(LoginException le) 
	    {  
	     System.out.println("Authentication failed");  
	     System.exit(-1);  
	    }  
	    System.out.println("Authentication succeeded");  
	    // Ϊ��ʹ�ͻ����ܹ������û�Ȩ�ޣ�һ����֤�ɹ����û��Ѿ�����֤�ˣ���
	    // ��֤�˵������ʹ��ͨ��Subject subject=ctx.getSubject()����ȡ��
	    Subject subject = ctx.getSubject();  
	    System.out.println(subject.toString());
	    PrivilegedAction action = new MyAction();  
	    // Subject��doAs(����doAsPrivileged)�������뱻���ã����ڷ���������run������������Ϊ����ȥִ�еģ���
	    Subject.doAsPrivileged(subject, action, null);  
	    try 
	    {  
	      ctx.logout();  
	    } catch(LoginException le) 
	    {  
	      System.out.println("Logout: " + le.getMessage());  
	    }  
	 }  
}
