package com.swu.erp.util.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.swu.erp.util.exception.AppException;

//自定义异常拦截
public class ExceptionInterceptor extends AbstractInterceptor{

	public String intercept(ActionInvocation invocation) throws Exception {
		try {
			return invocation.invoke();
		} catch (AppException e) {
			//记录日志
			//发送日志到程序员邮箱
			//报警
			ActionSupport as = (ActionSupport) invocation.getAction();
			as.addActionError(as.getText(e.getMessage()));
			return "error";
		} catch (Exception e) {
			/*ActionSupport as = (ActionSupport) invocation.getAction();
			as.addActionError("对不起，服务器已关闭，请联系管理员");
			//记录日志......
			//发送日志到程序员邮箱......
			//报警......
			//return "error"; 
			//上线时改成此格式	
		 */	
			
			e.printStackTrace();
			return invocation.invoke();
		}
	}
	

}
