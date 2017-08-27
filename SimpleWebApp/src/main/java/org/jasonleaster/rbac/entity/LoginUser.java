package org.jasonleaster.rbac.entity;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginUser {

    /**
     * 主键
     */
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 电话号码
     */
    private String telephone;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 账户状态，被锁定之类的，默认为0，表示正常
     */
    private int accountStatus;

    /**
     * 状态,默认为0，普通用户，1为超级管理员
     */
    private int status;

    @Override
    public String toString() {
        return "LoginUser [id=" + id + ", username=" + username + ", password=" + password
            + ", nickname=" + nickname
            + ", telephone=" + telephone + ", email=" + email + ", createTime=" + createTime
            + ", updateTime="
            + updateTime + ", accountStatus=" + accountStatus + ", status=" + status + "]";
    }

}
