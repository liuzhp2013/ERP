package com.swu.erp.auth.emp.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.swu.erp.auth.emp.dao.dao.EmpDao;
import com.swu.erp.auth.emp.vo.EmpModel;
import com.swu.erp.auth.emp.vo.EmpQueryModel;
import com.swu.erp.util.base.BaseDaoImpl;
import com.swu.erp.util.base.BaseQueryModel;

public class EmpDaoImpl extends BaseDaoImpl<EmpModel> implements EmpDao {
	public void doQBC(DetachedCriteria dc, BaseQueryModel bqm) {
		EmpQueryModel eqm = (EmpQueryModel) bqm;
		//TODO 添加自定义查询条件
		if(eqm.getUserName()!=null && eqm.getUserName().trim().length()>0) {
			dc.add(Restrictions.eq("userName", eqm.getUserName().trim()));
		}
		
		if(eqm.getName()!=null && eqm.getName().trim().length()>0) {
			dc.add(Restrictions.like("name", "%"+eqm.getName().trim()+"%"));
		}
		
		if(eqm.getTele()!=null && eqm.getTele().trim().length()>0) {
			dc.add(Restrictions.like("tele", "%"+eqm.getTele().trim()+"%"));
		}
		
		if(eqm.getGender()!=null && eqm.getGender()!=-1) {
			dc.add(Restrictions.eq("gender", eqm.getGender()));
		}
		
		if(eqm.getEmail()!=null && eqm.getEmail().trim().length()>0) {
			dc.add(Restrictions.like("email", "%"+eqm.getEmail().trim()+"%"));
		}
		
		if(eqm.getDm() != null && eqm.getDm().getUuid() != null && eqm.getDm().getUuid() != -1) {
			dc.add(Restrictions.eq("dm", eqm.getDm()));
		}
		
		//最后登录时间的最小值
		if(eqm.getLastLoginTime()!=null) {
			dc.add(Restrictions.ge("lastLoginTime", eqm.getLastLoginTime()));
		}
		
		//最后登录时间的最大值,注意时间的计算和系统时区的影响
		if(eqm.getLastLoginTime()!=null) {
			dc.add(Restrictions.le("lastLoginTime", eqm.getLastLoginTime2()+86400000-1));
		}
	}
	
	public EmpModel getByUserNameAndPwd(String userName, String pwd) {
		String hql = "from EmpModel where userName =? and pwd = ?";
		List<EmpModel> temp = this.getHibernateTemplate().find(hql,userName,pwd);
		return temp.size()>0?temp.get(0):null;
	}

	public boolean updatePwdByUserNameAndPwd(String userName, String pwd, String newPwd) {
		String hql = "update EmpModel set pwd=? where userName=? and pwd=?";
		
		int row = this.getHibernateTemplate().bulkUpdate(hql, newPwd,userName,pwd);	
		return row > 0;
	}

	//查询指定部门的所有员工信息
	public List<EmpModel> getAllByDepUuid(Long uuid) {
		String hql = "from EmpModel where dm.uuid = ?";
		
		return this.getHibernateTemplate().find(hql,uuid);
	}
}
