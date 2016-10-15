package com.swu.erp.invoice.orderdetail.web;

import java.util.List;
import com.swu.erp.invoice.orderdetail.business.ebi.OrderDetailEbi;
import com.swu.erp.invoice.orderdetail.vo.OrderDetailModel;
import com.swu.erp.invoice.orderdetail.vo.OrderDetailQueryModel;
import com.swu.erp.util.base.BaseAction;

public class OrderDetailAction extends BaseAction{ 
	public OrderDetailModel om = new OrderDetailModel();
	public OrderDetailQueryModel oqm = new OrderDetailQueryModel();

	private OrderDetailEbi orderDetailEbi;
	public void setOrderDetailEbi(OrderDetailEbi orderDetailEbi) {
		this.orderDetailEbi = orderDetailEbi;
	}

	public String input() {
		if(om.getUuid() != null) {
			om = orderDetailEbi.get(om.getUuid());
		}
		return BaseAction.INPUT;
	}

	public String save() {
		if(om.getUuid() == null) {
			orderDetailEbi.save(om);
		} else {
			orderDetailEbi.update(om);
		}

		return BaseAction.TO_LIST;
	}

	public String delete() {
		orderDetailEbi.delete(om);
		return BaseAction.TO_LIST;
	}

	public String list() {
		this.initPageDate(orderDetailEbi.getCount(oqm));
		List<OrderDetailModel> orderDetailList = orderDetailEbi.getAll(oqm,curPageNum,pageCount);
		this.put("orderDetailList", orderDetailList);
 		return BaseAction.LIST;
	}
}
