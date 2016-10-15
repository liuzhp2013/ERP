package com.swu.erp.auth.role.dao.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import com.swu.erp.auth.role.dao.dao.RoleDao;
import com.swu.erp.auth.role.vo.RoleModel;
import com.swu.erp.auth.role.vo.RoleQueryModel;
import com.swu.erp.util.base.BaseDaoImpl;
import com.swu.erp.util.base.BaseQueryModel;

public class RoleDaoImpl extends BaseDaoImpl<RoleModel> implements RoleDao {
	public void doQBC(DetachedCriteria dc, BaseQueryModel bqm) {
		RoleQueryModel rqm = (RoleQueryModel) bqm;
		if(rqm != null && rqm.getName() != null && rqm.getName().trim().length()>0) {
			dc.add(Restrictions.like("name", "%"+ rqm.getName() +"%"));
		}
		
		if(rqm != null && rqm.getCode() != null && rqm.getCode().trim().length()>0){
			dc.add(Restrictions.eq("code",  rqm.getCode()));
		}
	}
}
