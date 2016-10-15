package com.swu.erp.invoice.orderdetail.business.ebo;

import java.io.Serializable;
import java.util.List;
import com.swu.erp.invoice.orderdetail.business.ebi.OrderDetailEbi;
import com.swu.erp.invoice.orderdetail.dao.dao.OrderDetailDao;
import com.swu.erp.invoice.orderdetail.vo.OrderDetailModel;
import com.swu.erp.util.base.BaseQueryModel;

public class OrderDetailEbo implements OrderDetailEbi {
	private OrderDetailDao orderDetailDao;
	public void setOrderDetailDao(OrderDetailDao orderDetailDao) {
		this.orderDetailDao = orderDetailDao;
	}

	public void save(OrderDetailModel om) {
		orderDetailDao.save(om);
	}

	public void delete(OrderDetailModel om) {
		orderDetailDao.delete(om);
	}
	public void update(OrderDetailModel om) {
		orderDetailDao.update(om);
	}

	public OrderDetailModel get(Serializable uuid) {
		return orderDetailDao.get(uuid);
	}

	public List<OrderDetailModel> getAll() {
		return orderDetailDao.getAll();
	}

	public List<OrderDetailModel> getAll(BaseQueryModel bqm, Integer curPageNum,
			Integer pageCount) {
		return orderDetailDao.getAll(bqm,curPageNum,pageCount);
	}

	public Integer getCount(BaseQueryModel bqm) {
		return orderDetailDao.getCount(bqm);
	}
}
