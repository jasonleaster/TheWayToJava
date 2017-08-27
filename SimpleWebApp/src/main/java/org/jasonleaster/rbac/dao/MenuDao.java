package org.jasonleaster.rbac.dao;


import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.jasonleaster.rbac.entity.LoginUser;
import org.jasonleaster.rbac.entity.Menu;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuDao {

	/**
	 * 查找用户所拥有的所有菜单
	 */
	List<Menu> queryAll(@Param("user") LoginUser loginUser);
	
	/**
	 * 根据id查询一个菜单
	 */
	Menu queryById(@Param("id") String id);

	/**
	 * 根据角色id查询角色所拥有的菜单
	 */
	List<Menu> queryByRoleId(@Param("roleId") String roleId);

	/**
	 * 新增一个菜单
	 */
	int add(@Param("menu") Menu menu);

	/**
	 * 新增一个菜单
	 */
	int update(@Param("menu") Menu menu);

	/**
	 * 删除菜单
	 */
	int delete(String id);
}
