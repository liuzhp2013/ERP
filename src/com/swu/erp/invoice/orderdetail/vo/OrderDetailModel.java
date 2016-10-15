package com.swu.erp.invoice.orderdetail.vo;

import com.swu.erp.invoice.goods.vo.GoodsModel;
import com.swu.erp.invoice.order.vo.OrderModel;
import com.swu.erp.util.format.FormatUtil;

public class OrderDetailModel {
	private Long uuid;

	private Integer num;

	private Double price;

	private String priceView;
	private String totalPriceView;

	private GoodsModel gm;
	private OrderModel om;

	public Long getUuid() {
		return uuid;
	}

	public void setUuid(Long uuid) {
		this.uuid = uuid;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
		this.priceView = FormatUtil.formatMoney(price);
		this.totalPriceView = FormatUtil.formatMoney(num * price);
	}

	public GoodsModel getGm() {
		return gm;
	}

	public void setGm(GoodsModel gm) {
		this.gm = gm;
	}

	public OrderModel getOm() {
		return om;
	}

	public void setOm(OrderModel om) {
		this.om = om;
	}

	public String getPriceView() {
		return priceView;
	}

	public String getTotalPriceView() {
		return totalPriceView;
	}
	
}
