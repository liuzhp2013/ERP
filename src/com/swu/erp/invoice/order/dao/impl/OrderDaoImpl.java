package com.swu.erp.invoice.order.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.swu.erp.invoice.order.dao.dao.OrderDao;
import com.swu.erp.invoice.order.vo.OrderModel;
import com.swu.erp.invoice.order.vo.OrderQueryModel;
import com.swu.erp.util.base.BaseDaoImpl;
import com.swu.erp.util.base.BaseQueryModel;

public class OrderDaoImpl extends BaseDaoImpl<OrderModel> implements OrderDao {
	public void doQBC(DetachedCriteria dc, BaseQueryModel bqm) {
		OrderQueryModel oqm = (OrderQueryModel) bqm;
		//只获取采购订单
		if(oqm.getOrderType()!=null && oqm.getOrderType()!=-1) {
			dc.add(Restrictions.eq("orderType", oqm.getOrderType()));
		}
		
		//订单创建人查询条件设置
		if(oqm.getCreater()!=null && 
				oqm.getCreater().getName()!=null && 
				oqm.getCreater().getName().trim().length()>0) {
			//设置别名
			dc.createAlias("creater", "c1");
			dc.add(Restrictions.like("c1.name", "%"+oqm.getCreater().getName().trim()+"%"));
		}
		
		if(oqm.getChecker()!=null && 
				oqm.getChecker().getName()!=null && 
				oqm.getChecker().getName().trim().length()>0){
			dc.createAlias("checker", "c2");
			dc.add(Restrictions.like("c2.name", "%"+oqm.getChecker().getName().trim()+"%"));
		}
		if(oqm.getCompleter()!=null && 
				oqm.getCompleter().getName()!=null && 
				oqm.getCompleter().getName().trim().length()>0){
			dc.createAlias("completer", "c3");
			dc.add(Restrictions.like("c3.name", "%"+oqm.getCompleter().getName().trim()+"%"));
		}
		if(oqm.getCompleter()!=null && oqm.getCompleter().getUuid()!=null && oqm.getCompleter().getUuid()!=-1){
			dc.add(Restrictions.eq("completer", oqm.getCompleter()));
		}
		dc.createAlias("sm", "s");
		if(oqm.getSm()!=null && oqm.getSm().getUuid()!=null && oqm.getSm().getUuid()!= -1){
			dc.add(Restrictions.eq("s.uuid", oqm.getSm()));
		}
		if(oqm.getSm()!=null && oqm.getSm().getNeeds()!=null && oqm.getSm().getNeeds()!= -1){
			dc.add(Restrictions.eq("s.needs", oqm.getSm().getNeeds()));
		}
		
		//订单状态查询条件设置
		if(oqm.getType()!=null && oqm.getType()!=-1) {
			dc.add(Restrictions.eq("type", oqm.getType()));
		}
		//TODO 添加自定义查询条件
	}

//审批任务========================================================
	
	private void doQbc2(DetachedCriteria dc,BaseQueryModel qm,Integer[] orderTypes){
		dc.add(Restrictions.in("orderType", orderTypes));
		doQBC(dc,qm);
	}
	
	//获取所有的采购审批列表
	public List<OrderModel> getAllOrderTypes(OrderQueryModel oqm,
			Integer curPageNum, Integer pageCount, Integer[] orderTypes) {
		DetachedCriteria dc = DetachedCriteria.forClass(OrderModel.class);
		doQbc2(dc,oqm,orderTypes);
		return this.getHibernateTemplate().findByCriteria(dc,(curPageNum-1)*pageCount,pageCount);
	}

	//统计采购审批列表记录数
	public Integer getCountOrderTypes(OrderQueryModel oqm,
			Integer[] orderTypes) {
		DetachedCriteria dc = DetachedCriteria.forClass(OrderModel.class);
		dc.setProjection(Projections.rowCount());
		doQbc2(dc, oqm, orderTypes);
		List<Long> count = this.getHibernateTemplate().findByCriteria(dc);
		return count.get(0).intValue();
	}

//运输任务====================================================================
	
	private void doQbc3(DetachedCriteria dc,BaseQueryModel qm,Integer[] orderTypes){
		dc.add(Restrictions.in("type", orderTypes));
		doQBC(dc,qm);
	}
	
	public Integer getCountTypes(OrderQueryModel oqm, Integer[] taskTypes) {
		DetachedCriteria dc = DetachedCriteria.forClass(OrderModel.class);
		dc.setProjection(Projections.rowCount());
		doQbc3(dc, oqm, taskTypes);
		List<Long> count = this.getHibernateTemplate().findByCriteria(dc);
		return count.get(0).intValue();
	}

	public List<OrderModel> getAllTypes(OrderQueryModel oqm,
			Integer curPageNum, Integer pageCount, Integer[] taskTypes) {
		DetachedCriteria dc = DetachedCriteria.forClass(OrderModel.class);
		doQbc3(dc,oqm,taskTypes);
		return this.getHibernateTemplate().findByCriteria(dc,(curPageNum-1)*pageCount,pageCount);
	}
}
