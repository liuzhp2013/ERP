package com.swu.erp.invoice.goodstype.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.swu.erp.invoice.goodstype.dao.dao.GoodsTypeDao;
import com.swu.erp.invoice.goodstype.vo.GoodsTypeModel;
import com.swu.erp.invoice.goodstype.vo.GoodsTypeQueryModel;
import com.swu.erp.util.base.BaseDaoImpl;
import com.swu.erp.util.base.BaseQueryModel;

public class GoodsTypeDaoImpl extends BaseDaoImpl<GoodsTypeModel> implements GoodsTypeDao {
	public void doQBC(DetachedCriteria dc, BaseQueryModel bqm) {
		GoodsTypeQueryModel gqm = (GoodsTypeQueryModel) bqm;
		if(gqm != null && gqm.getName() != null && gqm.getName().trim().length()>0) {
			dc.add(Restrictions.like("name", "%"+gqm.getName()+"%"));
		}
		
		if(gqm != null && gqm.getSm()!=null && gqm.getSm().getUuid()!= null && gqm.getSm().getUuid() != -1) {
			dc.add(Restrictions.eq("sm", gqm.getSm()));
		}
	}

	public List<GoodsTypeModel> getAllBySmUuid(Long uuid) {
		String hql = "from GoodsTypeModel where sm.uuid = ?";
		return this.getHibernateTemplate().find(hql,uuid);
	}

	public List<GoodsTypeModel> getAllUnionBySmUuid(Long uuid) {
		String hql = "select distinct gt from GoodsModel gm join gm.gtm gt where gt.sm.uuid = ?";
		return this.getHibernateTemplate().find(hql,uuid);
	}
}
