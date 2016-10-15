package com.swu.erp.invoice.order.business.ebo;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.swu.erp.auth.emp.vo.EmpModel;
import com.swu.erp.invoice.goods.vo.GoodsModel;
import com.swu.erp.invoice.order.business.ebi.OrderEbi;
import com.swu.erp.invoice.order.dao.dao.OrderDao;
import com.swu.erp.invoice.order.vo.OrderModel;
import com.swu.erp.invoice.order.vo.OrderQueryModel;
import com.swu.erp.invoice.orderdetail.vo.OrderDetailModel;
import com.swu.erp.util.base.BaseQueryModel;
import com.swu.erp.util.encode.OrderNumGeneratorUtil;
import com.swu.erp.util.exception.AppException;

public class OrderEbo implements OrderEbi {
	private OrderDao orderDao;
	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	public void save(OrderModel om) {
		orderDao.save(om);
	}

	public void delete(OrderModel om) {
		orderDao.delete(om);
	}
	public void update(OrderModel om) {
		orderDao.update(om);
	}

	public OrderModel get(Serializable uuid) {
		return orderDao.get(uuid);
	}

	public List<OrderModel> getAll() {
		return orderDao.getAll();
	}

	public List<OrderModel> getAll(BaseQueryModel bqm, Integer curPageNum,
			Integer pageCount) {
		return orderDao.getAll(bqm,curPageNum,pageCount);
	}

	public Integer getCount(BaseQueryModel bqm) {
		return orderDao.getCount(bqm);
	}

	public void save(OrderModel om, Long[] goodsUuids, Integer[] nums,
			Double[] prices, EmpModel creater) {
		//设置订单号（必须保证订单号唯一)
		String orderNum = OrderNumGeneratorUtil.generatorOrderNum();
		om.setOrderNum(orderNum);
		
		//订单创建事件为当前的系统时间
		om.setCreateTime(System.currentTimeMillis());
		//当前保存的是采购订单
		om.setOrderType(OrderModel.ORDER_ORDERTYPE_OF_BUY);
		//新保存的订单的状态是未审核
		om.setType(OrderModel.ORDER_TYPE_OF_BUY_NO_CHECK);
		//制单人为当前登入人
		om.setCreater(creater);
		
		//构建订单对应的所有订单明细
		Integer totalNum = 0;
		Double totalPrice = 0.0d;
		Set<OrderDetailModel> odms = new HashSet<OrderDetailModel>();
		for(int i=0;i<goodsUuids.length;i++) {
			//创建订单明细的对象
			OrderDetailModel odm = new OrderDetailModel();
			//设置订单明细中的数量
			odm.setNum(nums[i]);
			
			//设置订单明细的单价
			odm.setPrice(prices[i]);
			
			//设置订单明细的商品
			GoodsModel gm = new GoodsModel();
			gm.setUuid(goodsUuids[1]);
			odm.setGm(gm);
			
			//设置所属的订单
			odm.setOm(om);
			
			//将订单明细添加到集合中
			odms.add(odm);
			
			//计算订单项总数量
			totalNum += nums[1];
			
			//计算订单总价
			totalPrice += prices[i];
		}
		//设置当前订单对应的所有订单明细数据
		om.setOdms(odms);
		//设置订单总数量
		om.setTotalNum(totalNum);
		//设置订单总价
		om.setTotalPrice(totalPrice);
		
		//保存订单
		orderDao.save(om);
	}

	public List<OrderModel> getAllBuy(OrderQueryModel oqm, Integer curPageNum,
			Integer pageCount) {
		//设置一个固定的条件(订单类别未采购)
		oqm.setOrderType(OrderModel.ORDER_ORDERTYPE_OF_BUY);
		return orderDao.getAll(oqm, curPageNum, pageCount);
	}

	private Integer[] buyCheckOrderTypes = new Integer[] {
			OrderModel.ORDER_ORDERTYPE_OF_BUY,
			OrderModel.ORDER_ORDERTYPE_OF_RETURN_BUY
		};
	public Integer getCountBuyCheck(OrderQueryModel oqm) {
		return orderDao.getCountOrderTypes(oqm,buyCheckOrderTypes);
	}
	 
	public List<OrderModel> getAllBuyCheck(OrderQueryModel oqm,
			Integer curPageNum, Integer pageCount) {
		return orderDao.getAllOrderTypes(oqm,curPageNum,pageCount,buyCheckOrderTypes);
	}

	public void buyCheckPass(String uuid, EmpModel checker) {
		//所谓的审核，实际上是修改业务（使用快照更新）
		//要修改的字段有：type、checker、checkTime
		OrderModel temp = orderDao.get(uuid);
		//逻辑校验
		if(!temp.getType().equals(OrderModel.ORDER_TYPE_OF_BUY_NO_CHECK)){
			throw new AppException("请不要进行非法操作");
		}
		temp.setType(OrderModel.ORDER_TYPE_OF_BUY_CHECK_PASS);
		temp.setCheckTime(System.currentTimeMillis());
		temp.setChecker(checker);
	}

	public void buyCheckNoPass(String uuid, EmpModel checker) {
		OrderModel temp = orderDao.get(uuid);
		//逻辑校验
		if(!temp.getType().equals(OrderModel.ORDER_TYPE_OF_BUY_NO_CHECK)){
			throw new AppException("请不要进行非法操作");
		}
		temp.setType(OrderModel.ORDER_TYPE_OF_BUY_CHECK_NO_PASS);
		temp.setCheckTime(System.currentTimeMillis());
		temp.setChecker(checker);
	}
	
	private Integer[] taskTypes = new Integer[] {
			OrderModel.ORDER_TYPE_OF_BUY_CHECK_PASS,
			OrderModel.ORDER_TYPE_OF_BUY_BUYING,
			OrderModel.ORDER_TYPE_OF_BUY_IN_STORE,
			OrderModel.ORDER_TYPE_OF_BUY_COMPLETE
		};
	
	public List<OrderModel> getAllTask(OrderQueryModel oqm,
			Integer curPageNum, Integer pageCount) {
		//运输的任务必须是已经审核通过的
		return orderDao.getAllTypes(oqm,curPageNum,pageCount,taskTypes);
	}

	public Integer getCountTask(OrderQueryModel oqm) {
		return orderDao.getCountTypes(oqm,taskTypes);
	}

	public void assignTask(String uuid, EmpModel completer) {
		//快照更新
		OrderModel temp = orderDao.get(uuid);
		
		//逻辑校验(集合包含性判定)
		if(!temp.getType().equals(OrderModel.ORDER_TYPE_OF_BUY_CHECK_PASS)){
			throw new AppException("请不要进行非法操作");
		}
		//设置状态
		temp.setType(OrderModel.ORDER_TYPE_OF_BUY_BUYING);
		//设置跟单人
		temp.setCompleter(completer);
	}
}
