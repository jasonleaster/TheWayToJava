package org.jasonleaster.rbac.service;

import org.jasonleaster.rbac.entity.LoginUser;
import org.jasonleaster.rbac.entity.Menu;
import java.util.List;


public interface MenuService {
	/**
	 * 添加菜单或修改菜单
	 */
	int addOrUpdateMenu(Menu menu);

	/**
	 * 根据id批量删除菜单
	 */
	int delete(String id);

	/**
	 * 查询为用户所分配的菜单
	 */
	List<Menu> queryAll(LoginUser loginUser);
	
	/**
	 * 根据id查询一个菜单
	 */
	Menu queryById(String id);
}
