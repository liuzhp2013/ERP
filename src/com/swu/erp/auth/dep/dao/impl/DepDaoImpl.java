package com.swu.erp.auth.dep.dao.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import com.swu.erp.auth.dep.dao.dao.DepDao;
import com.swu.erp.auth.dep.vo.DepModel;
import com.swu.erp.auth.dep.vo.DepQueryModel;
import com.swu.erp.util.base.BaseDaoImpl;
import com.swu.erp.util.base.BaseQueryModel;

/**
 * Title:部门模块Dao层实现类 Description: Company:swu
 * 
 * @author 刘长平
 * @date 2016年7月19日
 */
public class DepDaoImpl extends BaseDaoImpl<DepModel> implements DepDao {
	// 实现自定义查询条件
	public void doQBC(DetachedCriteria dc, BaseQueryModel bqm) {
		DepQueryModel dqm = (DepQueryModel) bqm;
		if (dqm.getName() != null && dqm.getName().trim().length() > 0) {
			dc.add(Restrictions.like("name", "%" + dqm.getName().trim() + "%"));
		}

		if (dqm.getTele() != null && dqm.getTele().trim().length() > 0) {
			dc.add(Restrictions.like("tele", "%" + dqm.getTele().trim() + "%"));
		}
		
		//TODO 添加自定义查询条件
	}
}
