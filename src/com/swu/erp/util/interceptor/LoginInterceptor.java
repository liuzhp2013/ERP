package com.swu.erp.util.interceptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.swu.erp.auth.emp.vo.EmpModel;

//登录权限校验
public class LoginInterceptor extends MethodFilterInterceptor {
	
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		String operName = invocation.getProxy().getActionName();
		if("page_login".equals(operName)) {
			return invocation.invoke();
		}
		
		// 执行除登录外的任意操作之前做登录校验
		// 获取当前登录人信息
		EmpModel LoginEm = (EmpModel) ActionContext.getContext().getSession()
				.get(EmpModel.EMP_LOGIN_USER_OBJECT_NAME);
		// 如果当前没有登录，则跳转到登录页面进行登录
		if (LoginEm == null) {
			// 跳转到登录页面 
			return "noLogin";
		}
		// 放行，执行原始操作
		return invocation.invoke();
	}
}
