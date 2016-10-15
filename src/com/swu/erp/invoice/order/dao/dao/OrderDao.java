package com.swu.erp.invoice.order.dao.dao;

import java.util.List;

import com.swu.erp.invoice.order.vo.OrderModel;
import com.swu.erp.invoice.order.vo.OrderQueryModel;
import com.swu.erp.util.base.BaseDao;

public interface OrderDao extends BaseDao<OrderModel>{

	public List<OrderModel> getAllOrderTypes(OrderQueryModel oqm, Integer curPageNum,
			Integer pageCount, Integer[] orderTypes);

	public Integer getCountOrderTypes(OrderQueryModel oqm,
			Integer[] buyCheckOrderTypes);

	public Integer getCountTypes(OrderQueryModel oqm, Integer[] taskTypes);

	public List<OrderModel> getAllTypes(OrderQueryModel oqm,
			Integer curPageNum, Integer pageCount, Integer[] taskTypes);

}
