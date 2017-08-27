package org.jasonleaster.rbac.entity;

import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Role {

    /**
     * 角色id
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 创建时间，主要用来排序
     */
    private Date createTime;

    /**
     * 一对多一个角色有多个权限
     */
    private List<Menu> menus;

    @Override
    public String toString() {
        return "Role [id=" + id + ", name=" + name + ", createTime=" + createTime + ", menus="
            + menus + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Role) {
            Role role = (Role) obj;
            return (id.equals(role.id));
        }
        return super.equals(obj);
    }

}
