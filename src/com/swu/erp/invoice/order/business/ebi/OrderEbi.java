package com.swu.erp.invoice.order.business.ebi;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.swu.erp.auth.emp.vo.EmpModel;
import com.swu.erp.invoice.order.vo.OrderModel;
import com.swu.erp.invoice.order.vo.OrderQueryModel;
import com.swu.erp.util.base.BaseEbi;

@Transactional
public interface OrderEbi extends BaseEbi<OrderModel>{
	/**
	 * 保存采购订单
	 * @param om 订单数据模型（封装了供应商的uuid）
	 * @param goodsUuids 商品uuid数组
	 * @param nums 数量数组
	 * @param prices 单价数组
	 * @param loginUser 制单人
	 */
	public void save(OrderModel om, Long[] goodsUuids, Integer[] nums,
			Double[] prices, EmpModel loginUser);

	/**
	 * 获取所有采购订单数据
	 * @param oqm 
	 * @param curPageNum
	 * @param pageCount
	 * @return
	 */
	public List<OrderModel> getAllBuy(OrderQueryModel oqm, Integer curPageNum,
			Integer pageCount);
	
	/**
	 * 采购审批列表方法
	 * @param oqm
	 * @param curPageNum
	 * @param pageCount
	 * @return
	 */
	public List<OrderModel> getAllBuyCheck(OrderQueryModel oqm,
			Integer curPageNum, Integer pageCount);

	public Integer getCountBuyCheck(OrderQueryModel oqm);

	/**
	 * 采购审核通过
	 * @param uuid 被审核订单的uuid
	 * @param empModel 
	 */
	public void buyCheckPass(String uuid, EmpModel checker);

	public void buyCheckNoPass(String uuid, EmpModel loginUser);

	public List<OrderModel> getAllTask(OrderQueryModel oqm,
			Integer curPageNum, Integer pageCount);

	public Integer getCountTask(OrderQueryModel oqm);
	
	/**
	 * 指派运输任务
	 * @param uuid 订单id
	 * @param completer 跟单人
	 */
	public void assignTask(String uuid, EmpModel completer);

}
