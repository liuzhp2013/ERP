package com.swu.erp.auth.menu.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.swu.erp.auth.menu.business.ebi.MenuEbi;
import com.swu.erp.auth.menu.vo.MenuModel;
import com.swu.erp.auth.menu.vo.MenuQueryModel;
import com.swu.erp.auth.role.business.ebi.RoleEbi;
import com.swu.erp.auth.role.vo.RoleModel;
import com.swu.erp.util.base.BaseAction;

public class MenuAction extends BaseAction {
	public MenuModel mm = new MenuModel();
	public MenuQueryModel mqm = new MenuQueryModel();

	private MenuEbi menuEbi;
	private RoleEbi roleEbi;

	public void setRoleEbi(RoleEbi roleEbi) {
		this.roleEbi = roleEbi;
	}

	public void setMenuEbi(MenuEbi menuEbi) {
		this.menuEbi = menuEbi;
	}

	public String input() {
		// 加载所有的父菜单
		List<MenuModel> menuList = menuEbi.getAllOneLeverMenu();
		put("menuList", menuList);

		// 加载角色信息
		List<RoleModel> roleList = roleEbi.getAll();
		put("roleList", roleList);

		if (mm.getUuid() != null) {
			// 修改时，回显数据
			mm = menuEbi.get(mm.getUuid());

			roleUuids = new Long[mm.getRoles().size()];
			int i = 0;
			for (RoleModel role : mm.getRoles()) {
				roleUuids[i++] = role.getUuid();
			}
		}
		return BaseAction.INPUT;
	}

	public Long[] roleUuids;

	public String save() {
		if (mm.getUuid() == null) {
			menuEbi.save(mm, roleUuids);
		} else {
			menuEbi.update(mm, roleUuids);
		}

		return BaseAction.TO_LIST;
	}

	public String delete() {
		menuEbi.delete(mm);
		return BaseAction.TO_LIST;
	}

	public String list() {
		List<MenuModel> parentList = menuEbi.getAllOneLeverMenu();
		put("parentList", parentList);

		this.initPageDate(menuEbi.getCount(mqm));
		List<MenuModel> menuList = menuEbi.getAll(mqm, curPageNum, pageCount);
		this.put("menuList", menuList);
		return BaseAction.LIST;
	}

	// 显示菜单
	public void showMenu() throws IOException {
		// 1.首先获取root参数
		String root = getRequest().getParameter("root");
		// 2.判断参数值 source id
		HttpServletResponse response = getResponse();

		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = response.getWriter();

		StringBuilder json = new StringBuilder();
		json.append("[");

		if ("source".equals(root)) {
			// 生成一级菜单
			List<MenuModel> menuList = menuEbi.getAllOneLevelByEmp(getLoginUser()
					.getUuid());
			for (MenuModel temp : menuList) {
				json.append("{\"text\":\"");
				json.append(temp.getName());
				json.append("\",\"hasChildren\":true,\"classes\":\"folder\",\"id\":\"");
				json.append(temp.getUuid());
				json.append("\"},");
			}
		} else {
			// 生成二级菜单项
			// 获取指定一级菜单的二级菜单项
			Long puuid = new Long(root);
			List<MenuModel> menuList = menuEbi.getByEmpAndPuuid(getLoginUser()
					.getUuid(), puuid);
			for (MenuModel temp : menuList) {
				json.append("{\"text\":\"<a class='hei' target='main' href='");
				json.append(temp.getUrl());
				json.append("'>");
				json.append(temp.getName());
				json.append("</a>\",\"hasChildren\":false,\"classes\":\"file\"},");
			}
		}

		json.deleteCharAt(json.length() - 1);
		json.append("]");

		pw.write(json.toString());
		pw.flush();
	}
}
