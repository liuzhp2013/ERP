package com.swu.erp.auth.emp.business.ebo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.swu.erp.auth.emp.business.ebi.EmpEbi;
import com.swu.erp.auth.emp.dao.dao.EmpDao;
import com.swu.erp.auth.emp.vo.EmpModel;
import com.swu.erp.auth.role.vo.RoleModel;
import com.swu.erp.util.base.BaseQueryModel;
import com.swu.erp.util.exception.AppException;
import com.swu.erp.util.format.MD5Utils;

public class EmpEbo implements EmpEbi {
	private EmpDao empDao;
	public void setEmpDao(EmpDao empDao) {
		this.empDao = empDao;
	}

	//废弃了
	public void save(EmpModel em) {
		/*//如果用户名没有输入，那么认定为特殊的错误现象。
		if(em.getUserName() == null || em.getUserName().trim().length() == 0) {
			//抛出异常信息
			throw new AppException("INFO_EMP_USERNAME_IS_EMPTY");
		}
		
		//对密码加密
		em.setPwd(MD5Utils.md5(em.getPwd()));
		//设置默认值
		em.setLastLoginTime(System.currentTimeMillis());//默认注册时间为第一次登录时间
		em.setLastLoginIp("-"); //减号代表未登录过
		em.setLoginTimes(0);
		
		empDao.save(em);
		*/
	}

	public void delete(EmpModel em) {
		empDao.delete(em);
	}
	
	//废弃了
	public void update(EmpModel em) {
/*//empDao.update(em);
		
		EmpModel temp = empDao.get(em.getUuid());
		//修改快照区中的name,email,tele,gender,address,dm
		temp.setName(em.getName());
		temp.setEmail(em.getEmail());
		temp.setAddress(em.getAddress());
		temp.setTele(em.getTele());
		temp.setGender(em.getGender());
		temp.setDm(em.getDm());
		*/
	}

	public EmpModel get(Serializable uuid) {
		return empDao.get(uuid);
	}

	public List<EmpModel> getAll() {
		return empDao.getAll();
	}

	public List<EmpModel> getAll(BaseQueryModel bqm, Integer curPageNum,
			Integer pageCount) {
		return empDao.getAll(bqm,curPageNum,pageCount);
	}

	public Integer getCount(BaseQueryModel bqm) {
		return empDao.getCount(bqm);
	}
	
	public EmpModel login(String userName, String pwd,String loginIp) {
		// MD5加密
		pwd = MD5Utils.md5(pwd);
		//调用数据层
		EmpModel loginEm = empDao.getByUserNameAndPwd(userName,pwd);
		
		//如果登录成功，则记录当前登录ip和登录时间等信息
		if(loginEm != null) {
			loginEm.setLastLoginTime(System.currentTimeMillis());
			loginEm.setLoginTimes(loginEm.getLoginTimes() + 1);
			loginEm.setLastLoginIp(loginIp);
		}
		
		return loginEm;
		
	}

	public boolean changePwd(String userName, String pwd, String newPwd) {
		//对密码加密
		pwd = MD5Utils.md5(pwd);
		newPwd = MD5Utils.md5(newPwd);
		
		return empDao.updatePwdByUserNameAndPwd(userName,pwd,newPwd);
	}

	public void save(EmpModel em, Long[] roleUuids) {
		//关联em对象与角色对象，创建em与rm的关联关系（多对多）
		Set<RoleModel> roles = new HashSet<RoleModel>();
		
		for(Long roleUuid : roleUuids) {
			RoleModel temp = new RoleModel();
			temp.setUuid(roleUuid);
			roles.add(temp);
		}
		em.setRoles(roles);
		
		
		//如果用户名没有输入，那么认定为特殊的错误现象。
		if(em.getUserName() == null || em.getUserName().trim().length() == 0) {
			//抛出异常信息
			throw new AppException("INFO_EMP_USERNAME_IS_EMPTY");
		}
		//对密码加密
		em.setPwd(MD5Utils.md5(em.getPwd()));
		//设置默认值ֵ
		em.setLastLoginTime(System.currentTimeMillis());//Ĭ��ע��ʱ��Ϊ��һ�ε�¼ʱ��
		em.setLastLoginIp("-"); //���Ŵ��δ��¼��
		em.setLoginTimes(0);
		empDao.save(em);
	}

	public void update(EmpModel em, Long[] roleUuids) {
		EmpModel temp = empDao.get(em.getUuid());
		//修改快照区中的name,email,tele,gender,address,dm
		temp.setName(em.getName());
		temp.setEmail(em.getEmail());
		temp.setAddress(em.getAddress());
		temp.setTele(em.getTele());
		temp.setGender(em.getGender());
		temp.setDm(em.getDm());
		
		//关联em对象与角色对象，修改em与rm的关联关系（多对多）
		Set<RoleModel> roles = new HashSet<RoleModel>();
		for(Long roleUuid : roleUuids) {
			RoleModel temp2 = new RoleModel();
			temp2.setUuid(roleUuid);
			roles.add(temp2);
		}
		temp.setRoles(roles);
	}

	public List<EmpModel> getByDep(Long uuid) {
		return empDao.getAllByDepUuid(uuid);
	}

}
