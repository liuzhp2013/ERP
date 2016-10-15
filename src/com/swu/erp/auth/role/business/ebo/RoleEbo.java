package com.swu.erp.auth.role.business.ebo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.swu.erp.auth.menu.vo.MenuModel;
import com.swu.erp.auth.res.vo.ResModel;
import com.swu.erp.auth.role.business.ebi.RoleEbi;
import com.swu.erp.auth.role.dao.dao.RoleDao;
import com.swu.erp.auth.role.vo.RoleModel;
import com.swu.erp.util.base.BaseQueryModel;

public class RoleEbo implements RoleEbi {
	private RoleDao roleDao;
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}
	
	//废弃了
	public void save(RoleModel rm) {
		
	}
	
	//废弃了
	public void update(RoleModel rm) {
		
	}

	public void delete(RoleModel rm) {
		roleDao.delete(rm);
	}
	
	public RoleModel get(Serializable uuid) {
		return roleDao.get(uuid);
	}

	public List<RoleModel> getAll() {
		return roleDao.getAll();
	}

	public List<RoleModel> getAll(BaseQueryModel bqm, Integer curPageNum,
			Integer pageCount) {
		return roleDao.getAll(bqm,curPageNum,pageCount);
	}

	public Integer getCount(BaseQueryModel bqm) {
		return roleDao.getCount(bqm);
	}
	
/*
	public void save(RoleModel rm, Long[] resUuids) {
		
		Set<ResModel> reses = new HashSet<ResModel>();
 		int i = 0;
		for(Long resUuid : resUuids) {
			ResModel temp = new ResModel();
			temp.setUuid(resUuid);
			reses.add(temp);
		}
		rm.setReses(reses);
		roleDao.save(rm);
	}

	public void update(RoleModel rm, Long[] resUuids) {
		
		Set<ResModel> reses = new HashSet<ResModel>();
 		int i = 0;
		for(Long resUuid : resUuids) {
			ResModel temp = new ResModel();
			temp.setUuid(resUuid);
			reses.add(temp);
		}
		rm.setReses(reses);
		roleDao.update(rm);
	}
*/

	public void save(RoleModel rm, Long[] resUuids, Long[] menuUuids) {
		Set<ResModel> reses = new HashSet<ResModel>();
 		int i = 0;
		for(Long resUuid : resUuids) {
			ResModel temp = new ResModel();
			temp.setUuid(resUuid);
			reses.add(temp);
		}
		rm.setReses(reses);
		
		Set<MenuModel> menus = new HashSet<MenuModel>();
		i = 0;
		for(Long menuUuid : menuUuids) {
			MenuModel temp = new MenuModel();
			temp.setUuid(menuUuid);
			menus.add(temp);
		}
		rm.setMenus(menus);
		
		roleDao.save(rm);
	}
	
	public void update(RoleModel rm, Long[] resUuids, Long[] menuUuids) {
		Set<ResModel> reses = new HashSet<ResModel>();
 		int i = 0;
		for(Long resUuid : resUuids) {
			ResModel temp = new ResModel();
			temp.setUuid(resUuid);
			reses.add(temp);
		}
		rm.setReses(reses);
		
		Set<MenuModel> menus = new HashSet<MenuModel>();
		i = 0;
		for(Long menuUuid : menuUuids) {
			MenuModel temp = new MenuModel();
			temp.setUuid(menuUuid);
			menus.add(temp);
		}
		rm.setMenus(menus);
		
		roleDao.update(rm);
	}

}
