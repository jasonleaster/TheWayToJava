package org.jasonleaster.rbac.service;

import org.jasonleaster.rbac.entity.LoginUser;
import java.util.List;
import org.jasonleaster.rbac.model.LoginUserVO;

public interface LoginUserService {

	/**
	 * 登录
	 */
	LoginUser login(String username, String password);

	/**
	 * 根据id查询一个登录用户
	 */
	LoginUser getById(String id);

	/**
	 * 检测用户名是否可用
	 */
	boolean checkUsername(String username);

	/**
	 * 登录密码修改
	 */
	int updatePwd(String id, String password);

	/**
	 * 个人信息修改
	 */
	int updateUserInfo(LoginUserVO user);

	/**
	 * 查看所有的用户
	 */
	List<LoginUser> queryAll(LoginUser user);

	/**
	 * 根据用户id返回他所有的角色
	 */
	List<LoginUserVO> queryRoleListByUserId(int offset, int limit, String[] userIds);

	/**
	 * 管理员添加或修改用户
	 */
	int addOrUpdate(LoginUser loginUser, String[] roleIds);

	/**
	 * 修改密码
	 */
	int updatePassword(String userId, String password);

	/**
	 * 删除用户
	 */
	int delete(String id);
}
