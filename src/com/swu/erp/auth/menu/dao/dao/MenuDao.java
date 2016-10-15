package com.swu.erp.auth.menu.dao.dao;

import java.util.List;

import com.swu.erp.auth.menu.vo.MenuModel;
import com.swu.erp.util.base.BaseDao;

public interface MenuDao extends BaseDao<MenuModel>{

	public List<MenuModel> getAllMenuIsOneOrSystem();

	public List<MenuModel> getAllOneLevelByEmpUuid(Long uuid);

	public List<MenuModel> getByEmpUuidAndPuuid(Long uuid, Long puuid);

}
