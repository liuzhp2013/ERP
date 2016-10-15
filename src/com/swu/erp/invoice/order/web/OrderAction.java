package com.swu.erp.invoice.order.web;

import java.util.List;

import com.swu.erp.auth.emp.business.ebi.EmpEbi;
import com.swu.erp.auth.emp.vo.EmpModel;
import com.swu.erp.invoice.goods.business.ebi.GoodsEbi;
import com.swu.erp.invoice.goods.vo.GoodsModel;
import com.swu.erp.invoice.goodstype.business.ebi.GoodsTypeEbi;
import com.swu.erp.invoice.goodstype.vo.GoodsTypeModel;
import com.swu.erp.invoice.order.business.ebi.OrderEbi;
import com.swu.erp.invoice.order.vo.OrderModel;
import com.swu.erp.invoice.order.vo.OrderQueryModel;
import com.swu.erp.invoice.supplier.business.ebi.SupplierEbi;
import com.swu.erp.invoice.supplier.vo.SupplierModel;
import com.swu.erp.util.base.BaseAction;

public class OrderAction extends BaseAction {
	public OrderModel om = new OrderModel();
	public OrderQueryModel oqm = new OrderQueryModel();
	

	private OrderEbi orderEbi;
	private EmpEbi empEbi;

	public void setOrderEbi(OrderEbi orderEbi) {
		this.orderEbi = orderEbi;
	}
	
	public void setEmpEbi(EmpEbi empEbi) {
		this.empEbi = empEbi;
	}


	public String input() {
		if (om.getUuid() != null) {
			om = orderEbi.get(om.getUuid());
		}
		return BaseAction.INPUT;
	}

	public String save() {
		if (om.getUuid() == null) {
			orderEbi.save(om);
		} else {
			orderEbi.update(om);
		}

		return BaseAction.TO_LIST;
	}

	public String delete() {
		orderEbi.delete(om);
		return BaseAction.TO_LIST;
	}

	public String list() {
		this.initPageDate(orderEbi.getCount(oqm));
		List<OrderModel> orderList = orderEbi
				.getAll(oqm, curPageNum, pageCount);
		this.put("orderList", orderList);
		return BaseAction.LIST;
	}

	// =========================================================================
	// ================================采购相关===================================
	// =========================================================================
	private SupplierEbi supplierEbi;
	private GoodsTypeEbi goodsTypeEbi;
	private GoodsEbi goodsEbi;

	public void setSupplierEbi(SupplierEbi supplierEbi) {
		this.supplierEbi = supplierEbi;
	}

	public void setGoodsTypeEbi(GoodsTypeEbi goodsTypeEbi) {
		this.goodsTypeEbi = goodsTypeEbi;
	}

	public void setGoodsEbi(GoodsEbi goodsEbi) {
		this.goodsEbi = goodsEbi;
	}

	public String buyList() {
		this.initPageDate(orderEbi.getCount(oqm));
		List<OrderModel> orderList = orderEbi
				.getAllBuy(oqm, curPageNum, pageCount);
		this.put("orderList", orderList);
		return "buyList";
	}

	public String buyInput() {
		// 加载供应商信息
		List<SupplierModel> supplierList = supplierEbi.getAllUnionTwo();
		// 加载商品类别信息
		List<GoodsTypeModel> gtmList = goodsTypeEbi
				.getAllUnionBySm(supplierList.get(0).getUuid());
		// 加载商品信息
		List<GoodsModel> gmList = goodsEbi
				.getAllByGtm(gtmList.get(0).getUuid());
		put("supplierList", supplierList);
		put("gtmList", gtmList);
		put("gmList", gmList);

		return "buyInput";
	}

	public Long[] goodsUuids;
	public Integer[] nums;
	public Double[] prices;
	//保存采购订单
	public String buySave() {
		orderEbi.save(om,goodsUuids,nums,prices,getLoginUser());
		return "toBuyList";
	}
	
	//查看采购订单明细
	public String buyDetail() {
		//根据om.uuid获取对应的订单信息，加载到详情页
		om = orderEbi.get(om.getUuid());
		return "buyDetail";
	}

//========================================================================
//=============================采购审核相关开始===============================
//========================================================================
	
	//采购审核列表
	public String buyCheckList() {
		this.initPageDate(orderEbi.getCountBuyCheck(oqm));
		List<OrderModel> orderList = orderEbi
				.getAllBuyCheck(oqm, curPageNum, pageCount);
		this.put("orderList", orderList);
		return "buyCheckList";
	}
	
	//采购审核订单详情
	public String buyCheckDetail(){
		//根据om.uuid查询订单明细
		om = orderEbi.get(om.getUuid());
		return "buyCheckDetail";
	}

	//采购审核通过
	public String buyCheckPass() {
		orderEbi.buyCheckPass(om.getUuid(),getLoginUser());
		return "toBuyCheckDetail";
	}
	
	//采购审核驳回
	public String buyCheckNoPass() {
		orderEbi.buyCheckNoPass(om.getUuid(),getLoginUser());
		return "toBuyCheckDetail";
	}

//========================================================================
//=============================采购审核相关结束==================================
//========================================================================

//==========================运输任务相关开始=====================================
	//运输任务列表
	public String taskList() {
		this.initPageDate(orderEbi.getCountTask(oqm));
		List<OrderModel> orderList = orderEbi
				.getAllTask(oqm, curPageNum, pageCount);
		this.put("orderList", orderList);
		return "taskList";
	}
	
	//运输任务详情
	public String taskDetail() {
		//加载运输部门的所有员工信息
		List<EmpModel> empList = empEbi.getByDep(getLoginUser().getDm().getUuid());
		put("empList", empList);
		om = orderEbi.get(om.getUuid());
		return "taskDetail";
	}
	
	//指派任务
	public String assignTask() {
		orderEbi.assignTask(om.getUuid(),om.getCompleter());
		return "toTaskList";
	}
	
	
//==========================运输任务相关结束=====================================
	

	
	
	
	// =================================ajax======================================
	// 向页面返回商品类别和商品信息数据
	private List<GoodsTypeModel> gtmList;
	private List<GoodsModel> gmList;
	private GoodsModel gm;

	public List<GoodsTypeModel> getGtmList() {
		return gtmList;
	}

	public List<GoodsModel> getGmList() {
		return gmList;
	}

	public GoodsModel getGm() {
		return gm;
	}

	// 接收页面传来的供应商id
	public Long supplierUuid;
	public Long gtmUuid;
	public Long gmUuid;

	// ajax根据供应商的uuid获取商品类别和商品信息
	public String ajaxGetGtmAndGm() {
		gtmList = goodsTypeEbi.getAllUnionBySm(supplierUuid);
		gmList = goodsEbi.getAllByGtm(gtmList.get(0).getUuid());
		gm = gmList.get(0);
		return "ajaxGetGtmAndGm";
	}

	// 页面上已经使用过的需要过滤的商品的uuid
	public String used;

	// ajax根据供应商的uuid获取商品类别和商品信息(具有数据过滤功能)
	public String ajaxGetGtmAndGm2() {
		gtmList = goodsTypeEbi.getAllUnionBySm(supplierUuid);
		// 由于商品类别数据中第一个类别对应的所有商品已经使用完毕，而没有将其删除，导致该商品类别下的商品集合在下面的迭代过程中没有商品，跑出了索引越界异常
		// 解決方案：刪除对应的商品类别即可
		// 过滤点所有商品已经使用王逼的商品类别
		Goods: //循环跳转标志
		for(int i=gtmList.size()-1;i>=0;i--){
			List<GoodsModel> gmListTemp = goodsEbi.getAllByGtm(gtmList.get(i).getUuid());
			for(GoodsModel gmTemp:gmListTemp) {
				if (!used.contains("'" + gmTemp.getUuid() + "'")) {
					//如果已使用的商品中不包含迭代的商品，说明该商品类别中还有未使用的商品，则保留该商品类别
					//保留该类别，直接判断下一个类别
					continue Goods;
				}
			}
			//如果循环执行到此处，表明该商品类别中的所有商品都已被使用，因此需要将该商品类别删除
			gtmList.remove(i);
		}
		
		gmList = goodsEbi.getAllByGtm(gtmList.get(0).getUuid());
		// 当前获取的商品的uuid具有重复的，要对其进行过滤
		// 对上述uuid集合进行过滤，需要对集合进行迭代删除--逆向迭代
		// 从gmList中取出所有袁术，挨个迭代（逆向迭代），与本次传递过来的used进行比对，比对完发现重复的，则删除掉，
		for (int i = gmList.size() - 1; i >= 0; i--) {
			Long uuid = gmList.get(i).getUuid();
			if (used.contains("'" + uuid + "'")) {
				gmList.remove(i);
			}
		}

		gm = gmList.get(0);
		return "ajaxGetGtmAndGm";
	}

	// 根据商品类别的uuid获取商品信息
	public String ajaxGetGm() {
		gmList = goodsEbi.getAllByGtm(gtmUuid);
		
		// 当前获取的商品的uuid具有重复的，要对其进行过滤
		// 对上述uuid集合进行过滤，需要对集合进行迭代删除--逆向迭代
		// 从gmList中取出所有袁术，挨个迭代（逆向迭代），与本次传递过来的used进行比对，比对完发现重复的，则删除掉，
		for (int i = gmList.size() - 1; i >= 0; i--) {
			Long uuid = gmList.get(i).getUuid();
			if (used.contains("'" + uuid + "'")) {
				gmList.remove(i);
			}
		}
		
		gm = gmList.get(0);
		return "ajaxGetGm";
	}

	public String ajaxGetPrice() {
		gm = goodsEbi.get(gmUuid);
		return "ajaxGetPrice";
	}
}
