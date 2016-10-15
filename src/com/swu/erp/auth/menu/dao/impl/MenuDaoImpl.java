package com.swu.erp.auth.menu.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.swu.erp.auth.menu.dao.dao.MenuDao;
import com.swu.erp.auth.menu.vo.MenuModel;
import com.swu.erp.auth.menu.vo.MenuQueryModel;
import com.swu.erp.util.base.BaseDaoImpl;
import com.swu.erp.util.base.BaseQueryModel;

public class MenuDaoImpl extends BaseDaoImpl<MenuModel> implements MenuDao {
	public void doQBC(DetachedCriteria dc, BaseQueryModel bqm) {
		MenuQueryModel mqm = (MenuQueryModel) bqm;
		// uuid不等于1
		dc.add(Restrictions.not(Restrictions.eq("uuid",
				MenuModel.MENU_SYSTEM_MENU_UUID)));

		// 添加查询条件
		if (mqm.getName() != null && mqm.getName().trim().length() > 0) {
			dc.add(Restrictions.like("name", "%" + mqm.getName().trim() + "%"));
		}

		if (mqm.getParent() != null && mqm.getParent().getUuid() != null
				&& mqm.getParent().getUuid() != -1) {
			dc.add(Restrictions.eq("parent", mqm.getParent()));
		}
	}

	public List<MenuModel> getAllMenuIsOneOrSystem() {
		// 获取所有uuid等于1和puuid等于1的菜单
		String hql = "from MenuModel where uuid = ? or parent.uuid = ?";
		return this.getHibernateTemplate().find(hql,
				MenuModel.MENU_SYSTEM_MENU_UUID,
				MenuModel.MENU_SYSTEM_MENU_UUID);
	}

	public List<MenuModel> getAllOneLevelByEmpUuid(Long uuid) {
		// menu->role->emp
		String hql = "select distinct menu from MenuModel menu join menu.roles role join role.emps emp where emp.uuid = ? and menu.parent.uuid = ? order by menu.uuid";
		return this.getHibernateTemplate().find(hql, uuid,
				MenuModel.MENU_SYSTEM_MENU_UUID);
	}

	public List<MenuModel> getByEmpUuidAndPuuid(Long uuid, Long puuid) {
		String hql ="select distinct menu from MenuModel menu join menu.roles role join role.emps emp where emp.uuid = ? and menu.parent.uuid = ? order by menu.uuid";
		return this.getHibernateTemplate().find(hql,uuid,puuid);
	}
}
