package com.swu.erp.util.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.swu.erp.auth.emp.vo.EmpModel;

public class BaseAction extends ActionSupport{
	//定义页面跳转使用的常量
	protected static final String LIST = "list";
	protected static final String TO_LIST = "toList";
	protected static final String INPUT = "input";
	
	//定义分页使用的常量
	public Integer curPageNum = 1;    //当前页码，此处必须初始化
	public Integer pageCount = 5;     //每页显示的记录数
	public Integer totalPage;        //总页数
	public Integer totalCount;
	
	public String getActionName() {
		String actionName = this.getClass().getSimpleName(); 
		String temp = actionName.substring(0,actionName.length()-6);
		return temp.substring(0, 1).toLowerCase() + temp.substring(1);
	}

	//定义用于数据存储的常用方法
	protected void put(String key,Object value) {
		ActionContext.getContext().put(key, value);
	}
	
	protected Object get(String name){
		return ActionContext.getContext().get(name);
	}
	
	protected void putSession(String key,Object value) {
		ActionContext.getContext().getSession().put(key, value);
	}
	
	protected Object getSession(String name){
		return ActionContext.getContext().getSession().get(name);
	}
	
	//定义获取登录用户信息的方法
	protected EmpModel getLoginUser() {
		return (EmpModel) getSession(EmpModel.EMP_LOGIN_USER_OBJECT_NAME);
	}
	
	//定义分页数据初始化的方法
	protected void initPageDate(Integer totalCount){
		//注意：不能在此处给curPageNum重新赋值，否则会覆盖之前的值，导致其无法动态变化
		this.totalCount = totalCount;
		this.totalPage = (totalCount + this.pageCount-1)/this.pageCount;
		
		//兼容页码值初始化错误
		if(curPageNum < 1) {
			this.curPageNum = 1;
		}
		
		if(curPageNum > totalPage){
			this.curPageNum = totalPage;
		}
	}
	
	protected HttpServletRequest getRequest(){
		return ServletActionContext.getRequest();
	}
	
	protected HttpServletResponse getResponse(){
		return ServletActionContext.getResponse();
	}
}
