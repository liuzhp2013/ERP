package com.swu.erp.invoice.supplier.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.swu.erp.invoice.supplier.dao.dao.SupplierDao;
import com.swu.erp.invoice.supplier.vo.SupplierModel;
import com.swu.erp.invoice.supplier.vo.SupplierQueryModel;
import com.swu.erp.util.base.BaseDaoImpl;
import com.swu.erp.util.base.BaseQueryModel;

public class SupplierDaoImpl extends BaseDaoImpl<SupplierModel> implements SupplierDao {
	public void doQBC(DetachedCriteria dc, BaseQueryModel bqm) {
		SupplierQueryModel sqm = (SupplierQueryModel) bqm;
		if(sqm != null && sqm.getName() != null && sqm.getName().trim().length()>0) {
			dc.add(Restrictions.like("name", "%"+sqm.getName()+"%"));
		}
		
		if(sqm != null && sqm.getContact() != null && sqm.getContact().trim().length()>0) {
			dc.add(Restrictions.like("contact", "%"+sqm.getContact()+"%"));
		}
		
		if(sqm != null && sqm.getTele() != null && sqm.getTele().trim().length()>0) {
			dc.add(Restrictions.eq("tele", sqm.getTele()));
		}
		
		if(sqm != null && sqm.getNeeds() != null && sqm.getNeeds() != -1) {
			dc.add(Restrictions.eq("needs", sqm.getNeeds()));
			
		}
	}

	public List<SupplierModel> getAllUnion() {
		//查询出包含商品类别的供应商信息
		//关联 sm -> gtm   需要建立关联关系
		//关联 gtm -> sm   采用此关联方式
		String hql = "select distinct s from GoodsTypeModel gtm join gtm.sm s";
		return this.getHibernateTemplate().find(hql);
	}
	
	public List<SupplierModel> getAllUnionTwo() {
		//sm->gtm->gm
		//gm->gtm->sm
		String hql = "select distinct s from GoodsModel gm join gm.gtm gt join gt.sm s";
		return this.getHibernateTemplate().find(hql);
	}
}
