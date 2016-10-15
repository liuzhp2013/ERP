package com.swu.erp.auth.emp.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.swu.erp.auth.dep.business.ebi.DepEbi;
import com.swu.erp.auth.dep.vo.DepModel;
import com.swu.erp.auth.emp.business.ebi.EmpEbi;
import com.swu.erp.auth.emp.vo.EmpModel;
import com.swu.erp.auth.emp.vo.EmpQueryModel;
import com.swu.erp.auth.res.business.ebi.ResEbi;
import com.swu.erp.auth.res.vo.ResModel;
import com.swu.erp.auth.role.business.ebi.RoleEbi;
import com.swu.erp.auth.role.vo.RoleModel;
import com.swu.erp.util.base.BaseAction;

public class EmpAction extends BaseAction{ 
	public EmpModel em = new EmpModel();
	public EmpQueryModel eqm = new EmpQueryModel();

	private EmpEbi empEbi;
	private DepEbi depEbi;
	private RoleEbi roleEbi;
	public Long[] roleUuids;
	private ResEbi resEbi;
	
	public void setResEbi(ResEbi resEbi) {
		this.resEbi = resEbi;
	}

	public void setEmpEbi(EmpEbi empEbi) {
		this.empEbi = empEbi;
	}
	
	public void setDepEbi(DepEbi depEbi) {
		this.depEbi = depEbi;
	}
	
	public void setRoleEbi(RoleEbi roleEbi) {
		this.roleEbi = roleEbi;
	}

	public String input() {	
		//加载角色信息
		List<RoleModel> roleList = roleEbi.getAll();
		put("roleList",roleList);
		
		//加载部门信息
		List<DepModel> depList = depEbi.getAll();
		put("depList",depList);
		
		//修改员工信息时回显该员工数据
		if(em.getUuid() != null) {
			//回显员工基本信息
			em = empEbi.get(em.getUuid());
			//回显员工角色信息
			roleUuids = new Long[em.getRoles().size()];
			int i = 0;
			for(RoleModel temp : em.getRoles()) {
				roleUuids[i++] = temp.getUuid();
			}
			
		}
		return BaseAction.INPUT;
	}

	public String save() {
		if(em.getUuid() == null) {
			empEbi.save(em,roleUuids);
		} else {
			empEbi.update(em,roleUuids);
		}

		return BaseAction.TO_LIST;
	}

	public String delete() {
		empEbi.delete(em);
		return BaseAction.TO_LIST;
	}

	public String list() {
		//加载所有部门的信息数据
		List<DepModel> depList = depEbi.getAll();
		put("depList",depList);
		this.initPageDate(empEbi.getCount(eqm));
		List<EmpModel> empList = empEbi.getAll(eqm,curPageNum,pageCount);
		this.put("empList", empList);
 		return BaseAction.LIST;
	}
	
	public String login() {
		// 页面收集了用户名密码信息
		// 将数据传递到业务层
		// 业务层转掉数据层
		// 数据层进行业务查询，查询结果返回
		
		//获取用户ip
		HttpServletRequest request = getRequest();
		String loginIp = request.getHeader("x-forwarded-for"); 
		if(loginIp == null || loginIp.length() == 0 || "unknown".equalsIgnoreCase(loginIp)) { 
			loginIp = request.getHeader("Proxy-Client-IP"); 
		} 
		if(loginIp == null || loginIp.length() == 0 || "unknown".equalsIgnoreCase(loginIp)) { 
			loginIp = request.getHeader("WL-Proxy-Client-IP"); 
		} 
		if(loginIp == null || loginIp.length() == 0 || "unknown".equalsIgnoreCase(loginIp)) { 
			loginIp = request.getRemoteAddr(); 
		}
		
		EmpModel loginEm = empEbi.login(em.getUserName(), em.getPwd(),loginIp);

		// 判断查询结果，如果查询到，
		// 登录成功，否则登录失败
		if (loginEm == null) {
			// 登录失败
			// 添加登录失败的信息
			this.addActionError("对不起，用户名或密码错误");
			return "loginFail";
		} else {
			// 登录成功
			//获取该用户对应角色的资源信息，用于权限校验
			List<ResModel> allResofEmp = resEbi.getAllResByEmp(loginEm
					.getUuid());
			StringBuilder sbd = new StringBuilder();
			for(ResModel rm : allResofEmp){
				sbd.append(rm.getUrl());
				sbd.append(",");
			}
			loginEm.setReses(sbd.toString());
			
			// 将登录人信息加入session，跳转到主页
			putSession(EmpModel.EMP_LOGIN_USER_OBJECT_NAME, loginEm);
			return "loginSuccess";
		}
	}
	
	public String logout() {
		putSession(EmpModel.EMP_LOGIN_USER_OBJECT_NAME, "null");
		return "noLogin";
	}
	
	public String toChangePwd() {
		return "toChangePwd";
	}
	
	public String newPwd;
	//修改密码
	public String changePwd(){
		//原始密码 em.pwd
		//新密码：newPwd
		boolean flag = empEbi.changePwd(getLoginUser().getUserName(),em.getPwd(),newPwd);
		
		if(flag) {
			//如果修改成功，则重新登录
			putSession(EmpModel.EMP_LOGIN_USER_OBJECT_NAME, "null");
			return "noLogin";
		} else {
			//修改失败，则返回修改页面
			return "toChangePwd";
		}
	}
}
