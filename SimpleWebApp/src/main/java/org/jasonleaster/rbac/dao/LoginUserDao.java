package org.jasonleaster.rbac.dao;

import java.util.List;
import java.util.Set;
import org.apache.ibatis.annotations.Param;
import org.jasonleaster.rbac.entity.LoginUser;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginUserDao {

    /**
     * 登录方法
     */
    LoginUser login(@Param("username") String username, @Param("password") String password);

    /**
     * shiro的根据用户id查找其角色
     */
    Set<String> findRoles(@Param("user") LoginUser user);

    /**
     * shiro的根据用户id查找其权限
     */
    Set<String> findPermissions(@Param("user") LoginUser user);

    /**
     * 查询用户所管理的用户
     */
    List<LoginUser> queryAll(LoginUser user);

    /**
     * 检查用户名是否可用
     */
    LoginUser checkUsername(@Param("username") String username);

    /**
     * 管理员添加用户
     */
    int add(@Param("user") LoginUser loginUser);

    /**
     * 修改个人信息,不包括密码和角色
     */
    int update(@Param("user") LoginUser loginUser);

    /**
     * 修改登录密码
     */
    int updatePassword(@Param("userId") String userId, @Param("password") String password);

    /**
     * 插入用户角色
     */
    int addUserRole(@Param("userId") String userId, @Param("roleIds") String[] roleIds);

    /**
     * 根据用户id删除用户角色表中的数据
     */
    int deleteUserRoleByUserId(@Param("userId") String userId);

    /**
     * 删除用户
     */
    int delete(String id);

}
