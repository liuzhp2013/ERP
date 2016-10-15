package com.swu.erp.auth.dep.web;

import java.util.List;
import com.swu.erp.auth.dep.business.ebi.DepEbi;
import com.swu.erp.auth.dep.vo.DepModel;
import com.swu.erp.auth.dep.vo.DepQueryModel;
import com.swu.erp.util.base.BaseAction;
/**
 * Title:部门模块Action
 * Description:
 * Company:swu
 * @author 刘长平
 * @date 2016年7月19日
 */
public class DepAction extends BaseAction{ 
	//收集DepModel信息
	public DepModel dm = new DepModel();
	//定义一个收集查询条件的数据对象
	public DepQueryModel dqm = new DepQueryModel();
	
	//注入业务层接口
	private DepEbi depEbi;
	public void setDepEbi(DepEbi depEbi) {
		this.depEbi = depEbi;
	}
	
	/*	此方法的功能已合并到list()方法中
	//查询列表功能
	public String queryList() {
		//根据查询条件获取数据（查询条件封装在dqm对象中）
		List<DepModel> depList = depEbi.getAll(dqm);
		//放入指定范围
		ActionContext.getContext().put("depList", depList);
		//跳转到页面
		
		return BaseAction.LIST;
	}
	*/
	//跳转到添加页面
	public String input() {
		if(dm.getUuid() != null) {
			dm = depEbi.get(dm.getUuid());
		}
		return BaseAction.INPUT;
	}
	
	//添加部门和更新部门合二为一
	public String save() {
		if(dm.getUuid() == null) {
			//将从页面中收集的数据传递到业务层，完成保存操作
			depEbi.save(dm);
		} else {
			//更新部门信息
			depEbi.update(dm);
		}
		
		return BaseAction.TO_LIST;
	}
	
	//删除部门信息
	public String delete() {
		depEbi.delete(dm);
		return BaseAction.TO_LIST;
	}
	
	//跳转到列表页面
	public String list() {
		//分页数据初始化
		this.initPageDate(depEbi.getCount(dqm));
		//加载所有部门信息，放入到指定域中，在页面上从指定域中取出数据，进行展示。
		List<DepModel> depList = depEbi.getAll(dqm,curPageNum,pageCount);
		//指定域范围：request,session,application
		this.put("depList", depList);
 		return BaseAction.LIST;
	}
}
