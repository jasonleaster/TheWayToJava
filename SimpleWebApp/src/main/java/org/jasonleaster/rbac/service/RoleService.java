package org.jasonleaster.rbac.service;

import org.jasonleaster.rbac.entity.LoginUser;
import org.jasonleaster.rbac.entity.Role;
import java.util.List;

public interface RoleService {

	/**
	 * 查询用户所能看到的角色列表
	 */
	List<Role> queryAll(LoginUser loginUser);

	/**
	 * 添加菜单或修改菜单
	 */
	int addOrUpdateRole(Role role, String[] menuIds);

	/**
	 * 删除角色
	 */
	int delete(String id);

}
