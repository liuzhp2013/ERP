package com.swu.erp.auth.menu.business.ebo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.swu.erp.auth.menu.business.ebi.MenuEbi;
import com.swu.erp.auth.menu.dao.dao.MenuDao;
import com.swu.erp.auth.menu.vo.MenuModel;
import com.swu.erp.auth.role.vo.RoleModel;
import com.swu.erp.util.base.BaseQueryModel;

public class MenuEbo implements MenuEbi {
	private MenuDao menuDao;
	public void setMenuDao(MenuDao menuDao) {
		this.menuDao = menuDao;
	}
	
	//废弃
	public void save(MenuModel mm) {
		menuDao.save(mm);
	}

	public void delete(MenuModel mm) {
		//完成级联删除的前提是，被删除对象级联的数据存在
		//删除OID为4的数据时，对应的对象应该级联哪些必须是已知的，此时对象中的关系数据存在
		//mm对象此时具有哪些数据？只有uuid,关系数据为null
		//h3对于关系数据为空的处理方式是：断开所有关系即可，由于此时设置了inverse=true，又不维护关系，没有进行任何操作
		//解决方案：级联删除前加载关系
		MenuModel temp = menuDao.get(mm.getUuid());
		menuDao.delete(temp);
	}
	
	//����
	public void update(MenuModel mm) {
		MenuModel temp = menuDao.get(mm.getUuid());
		temp.setName(mm.getName());
		temp.setUrl(mm.getUrl());
	}

	public MenuModel get(Serializable uuid) {
		return menuDao.get(uuid);
	}

	public List<MenuModel> getAll() {
		return menuDao.getAll();
	}

	public List<MenuModel> getAll(BaseQueryModel bqm, Integer curPageNum,
			Integer pageCount) {
		return menuDao.getAll(bqm,curPageNum,pageCount);
	}

	public Integer getCount(BaseQueryModel bqm) {
		return menuDao.getCount(bqm);
	}

	public List<MenuModel> getAllOneLeverMenu() {
		//获取系统菜单和所有的一级菜单
		return menuDao.getAllMenuIsOneOrSystem();
	}

	public void save(MenuModel mm, Long[] roleUuids) {
		Set<RoleModel> roles = new HashSet<RoleModel>();
		for(Long roleUuid : roleUuids){
			RoleModel temp = new RoleModel();
			temp.setUuid(roleUuid);
			roles.add(temp);
		}
		
		mm.setRoles(roles);
		menuDao.save(mm);
	}

	public void update(MenuModel mm, Long[] roleUuids) {
		MenuModel temp = menuDao.get(mm.getUuid());
		temp.setName(mm.getName());
		temp.setUrl(mm.getUrl());
		
		Set<RoleModel> roles = new HashSet<RoleModel>();
		for(Long roleUuid : roleUuids){
			RoleModel temp2 = new RoleModel();
			temp2.setUuid(roleUuid);
			roles.add(temp2);
		}
		
		temp.setRoles(roles);
	}

	public List<MenuModel> getAllOneLevelByEmp(Long uuid) {
		return menuDao.getAllOneLevelByEmpUuid(uuid);
	}

	public List<MenuModel> getByEmpAndPuuid(Long uuid, Long puuid) {
		return menuDao.getByEmpUuidAndPuuid(uuid,puuid);
	}

}
