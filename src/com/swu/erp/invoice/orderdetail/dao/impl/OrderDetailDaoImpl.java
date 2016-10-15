package com.swu.erp.invoice.orderdetail.dao.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import com.swu.erp.invoice.orderdetail.dao.dao.OrderDetailDao;
import com.swu.erp.invoice.orderdetail.vo.OrderDetailModel;
import com.swu.erp.invoice.orderdetail.vo.OrderDetailQueryModel;
import com.swu.erp.util.base.BaseDaoImpl;
import com.swu.erp.util.base.BaseQueryModel;

public class OrderDetailDaoImpl extends BaseDaoImpl<OrderDetailModel> implements OrderDetailDao {
	public void doQBC(DetachedCriteria dc, BaseQueryModel bqm) {
		OrderDetailQueryModel oqm = (OrderDetailQueryModel) bqm;
		//TODO 添加自定义查询条件
	}
}
