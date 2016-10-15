package com.swu.erp.invoice.goods.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.swu.erp.invoice.goods.dao.dao.GoodsDao;
import com.swu.erp.invoice.goods.vo.GoodsModel;
import com.swu.erp.invoice.goods.vo.GoodsQueryModel;
import com.swu.erp.util.base.BaseDaoImpl;
import com.swu.erp.util.base.BaseQueryModel;

public class GoodsDaoImpl extends BaseDaoImpl<GoodsModel> implements GoodsDao {
	public void doQBC(DetachedCriteria dc, BaseQueryModel bqm) {
		GoodsQueryModel gqm = (GoodsQueryModel) bqm;
		if(gqm.getUnit()!=null&&gqm.getUnit().trim().length()>0) {
			dc.add(Restrictions.eq("unit", gqm.getUnit().trim()));
		}
		
		if(gqm.getProducer()!= null&& gqm.getProducer().trim().length()>0) {
			dc.add(Restrictions.eq("producer", gqm.getProducer().trim()));
		}
		
		//按进货价格查询最小值
		if(gqm.getInPrice()!=null&&gqm.getInPrice()>0) {
			dc.add(Restrictions.ge("inPrice", gqm.getInPriceView()));
		}
		
		//按进货价格查询最大值
		if(gqm.getInPrice2()!=null&&gqm.getInPrice2()>0) {
			dc.add(Restrictions.le("inPrice", gqm.getInPrice2View()));
		}
		
		//按销售价格查询最小值
		if(gqm.getOutPrice()!=null&& gqm.getOutPrice()>0) {
			dc.add(Restrictions.ge("outPrice", gqm.getOutPriceView()));
		}
		
		//按销售价格查询最小值
		if(gqm.getOutPrice2()!=null&& gqm.getOutPrice2()>0) {
			dc.add(Restrictions.le("outPrice", gqm.getOutPrice2View()));
		}
		//TODO 添加自定义查询条件
	}

	public List<GoodsModel> getAllByGtmUuid(Long uuid) {
		String hql = "from GoodsModel where gtm.uuid =?";
		return this.getHibernateTemplate().find(hql,uuid);
	}
}
