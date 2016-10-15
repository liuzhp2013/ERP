package com.swu.erp.auth.role.web;

import java.util.List;

import com.swu.erp.auth.menu.business.ebi.MenuEbi;
import com.swu.erp.auth.menu.vo.MenuModel;
import com.swu.erp.auth.res.business.ebi.ResEbi;
import com.swu.erp.auth.res.vo.ResModel;
import com.swu.erp.auth.role.business.ebi.RoleEbi;
import com.swu.erp.auth.role.vo.RoleModel;
import com.swu.erp.auth.role.vo.RoleQueryModel;
import com.swu.erp.util.base.BaseAction;
import com.swu.erp.util.base.BaseEbi;

public class RoleAction extends BaseAction{ 
	public RoleModel rm = new RoleModel();
	public RoleQueryModel rqm = new RoleQueryModel();

	private RoleEbi roleEbi;
	private ResEbi resEbi;
	private MenuEbi menuEbi;
	
	public void setMenuEbi(MenuEbi menuEbi) {
		this.menuEbi = menuEbi;
	}

	public void setRoleEbi(RoleEbi roleEbi) {
		this.roleEbi = roleEbi;
	}

	public void setResEbi(ResEbi resEbi) {
		this.resEbi = resEbi;
	}

	public String input() {
		//加载所有资源
		List<ResModel> resList = resEbi.getAll();
		put("resList",resList);
		
		//加载所有菜单
		List<MenuModel> menuList = menuEbi.getAll();
		put("menuList",menuList);
		
		if(rm.getUuid() != null) {
			rm = roleEbi.get(rm.getUuid());
			//初始化resUuids,回显资源数据
			resUuids = new Long[rm.getReses().size()];
			int i = 0;
			for(ResModel temp : rm.getReses()) {
				resUuids[i++] = temp.getUuid();
			}
			
			//初始化menuUuids,回显菜单数据
			menuUuids = new Long[rm.getMenus().size()];
			i = 0;
			for(MenuModel temp : rm.getMenus()) {
				menuUuids[i++] = temp.getUuid();
			}
			
		}
		return BaseAction.INPUT;
	}

	public Long[] resUuids;
	public Long[] menuUuids;
	
	public String save() {
		if(rm.getUuid() == null) {
			roleEbi.save(rm,resUuids,menuUuids);
		} else {
			roleEbi.update(rm,resUuids,menuUuids);
		}

		return BaseAction.TO_LIST;
	}

	public String delete() {
		roleEbi.delete(rm);
		return BaseAction.TO_LIST;
	}

	public String list() {
		this.initPageDate(roleEbi.getCount(rqm));
		List<RoleModel> roleList = roleEbi.getAll(rqm,curPageNum,pageCount);
		this.put("roleList", roleList);
 		return BaseAction.LIST;
	}
}
