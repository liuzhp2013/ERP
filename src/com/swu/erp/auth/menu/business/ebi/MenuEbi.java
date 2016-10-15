package com.swu.erp.auth.menu.business.ebi;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.swu.erp.auth.menu.vo.MenuModel;
import com.swu.erp.util.base.BaseEbi;

@Transactional
public interface MenuEbi extends BaseEbi<MenuModel>{

	public List<MenuModel> getAllOneLeverMenu();

	public void save(MenuModel mm, Long[] roleUuids);

	public void update(MenuModel mm, Long[] roleUuids);

	public List<MenuModel> getAllOneLevelByEmp(Long uuid);

	public List<MenuModel> getByEmpAndPuuid(Long uuid, Long puuid);
}
