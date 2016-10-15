package com.swu.erp.util.interceptor;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.swu.erp.auth.emp.vo.EmpModel;
import com.swu.erp.auth.res.business.ebi.ResEbi;
import com.swu.erp.auth.res.vo.ResModel;
import com.swu.erp.util.exception.AppException;

//权限校验
public class AuthInterceptor extends AbstractInterceptor {
	private ResEbi resEbi;

	public void setResEbi(ResEbi resEbi) {
		this.resEbi = resEbi;
	}

	//改良2：改良特定用户对应角色资源加载方式
	public String intercept(ActionInvocation invocation) throws Exception {
		// 1，获取本次操作的url(即请求的action)
		String actionName = invocation.getProxy().getAction().getClass()
				.getName();
		String methodName = invocation.getProxy().getMethod();
		String totalName = actionName + "." + methodName;

		// 2，判断本次操作是否是被拦截操作
		//全资源加载是不区分登录用户都要进行的操作
		//所有用户使用的该数据都相同
		//改良方案：将该数据提交获取完毕，放入指定范围，使用时直接获取
		//查询时机：服务器启动时完成————监听器
		//范围：ServletContext
		String allRes = ServletActionContext.getServletContext().getAttribute("allRes").toString();
		if (!(allRes.contains(totalName))) {
			return invocation.invoke();
		}

		// 3，从session中获取当前登录人信息
		
		EmpModel loginEm = (EmpModel) ActionContext.getContext().getSession()
				.get(EmpModel.EMP_LOGIN_USER_OBJECT_NAME);
		
		//对于登录用户对应的可操作资源在每次登录过程中，均保持不变
		//可以考虑进行一次性加载工作，放入指定范围后，每次使用时直接获取
		//查询时机：登录时查询
		//范围：session
		//loginEm.getReses()即为当前登录人可进行的所有资源数据字符串
		
		// 4，获取当前登录人可执行的所有操作
		// 5，判断当前登录人对应的所有可执行操作中是否包含有本次操作
		if (loginEm.getReses().contains(totalName)) {
			return invocation.invoke();
		}

		throw new AppException("对不起，您没有访问权限，请不要非法操作");
	}

	
	//改良1：原始方案中每次都要获取资源数据的全数据，每次进行查询，该操作将成为系统瓶颈
	//改良全资源加载方式
	public String intercept2(ActionInvocation invocation) throws Exception {
		// 1，获取本次操作的url(即请求的action)
		String actionName = invocation.getProxy().getAction().getClass()
				.getName();
		String methodName = invocation.getProxy().getMethod();
		String totalName = actionName + "." + methodName;

		// 2，判断本次操作是否是被拦截操作
		//全资源加载是不区分登录用户都要进行的操作
		//所有用户使用的该数据都相同
		//改良方案：将该数据提交获取完毕，放入指定范围，使用时直接获取
		//查询时机：服务器启动时完成————监听器
		//范围：ServletContext
		String allRes = ServletActionContext.getServletContext().getAttribute("allRes").toString();
		if (!(allRes.contains(totalName))) {
			return invocation.invoke();
		}

		// 3，从session中获取当前登录人信息
		EmpModel loginEm = (EmpModel) ActionContext.getContext().getSession()
				.get(EmpModel.EMP_LOGIN_USER_OBJECT_NAME);

		if (loginEm != null) {
			// 4，获取当前登录人可执行的所有操作
			List<ResModel> allResofEmp = resEbi.getAllResByEmp(loginEm
					.getUuid());

			// 5，判断当前登录人对应的所有可执行操作中是否包含有本次操作
			for (ResModel temp : allResofEmp) {
				if (temp.getUrl().equals(totalName)) {
					return invocation.invoke();
				}
			}
		}

		throw new AppException("对不起，您没有访问权限，请不要非法操作");
	}

	public String intercept1(ActionInvocation invocation) throws Exception {
		// 必须保证请求当前操作的用户处于已登录状态
		// 获取当前操作人信息
		// 当前登录人可执行的操作中是否包含本次操作的内容
		// 判断当前操作人是否可以执行
		// 调用原始操作

		// 1，获取本次操作的url(即请求的action)
		String actionName = invocation.getProxy().getAction().getClass()
				.getName();
		String methodName = invocation.getProxy().getMethod();
		String totalName = actionName + "." + methodName;

		// 2，判断本次操作是否是被拦截操作
		List<ResModel> allRes = resEbi.getAll();
		StringBuilder sbd = new StringBuilder();
		for (ResModel res : allRes) {
			sbd.append(res.getUrl());
			sbd.append(",");
		}
		if (sbd.indexOf(totalName) < 0) {
			return invocation.invoke();
		}

		// 3，从session中获取当前登录人信息
		EmpModel loginEm = (EmpModel) ActionContext.getContext().getSession()
				.get(EmpModel.EMP_LOGIN_USER_OBJECT_NAME);

		if (loginEm != null) {
			// 4，获取当前登录人可执行的所有操作
			List<ResModel> allResofEmp = resEbi.getAllResByEmp(loginEm
					.getUuid());

			// 5，判断当前登录人对应的所有可执行操作中是否包含有本次操作
			for (ResModel temp : allResofEmp) {
				if (temp.getUrl().equals(totalName)) {
					return invocation.invoke();
				}
			}
		}

		throw new AppException("对不起，您没有访问权限，请不要非法操作");
	}
}
