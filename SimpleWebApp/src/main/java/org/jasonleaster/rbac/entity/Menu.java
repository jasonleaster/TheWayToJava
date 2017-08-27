package org.jasonleaster.rbac.entity;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Menu {

    /**
     * 菜单id
     */
    private String id;

    /**
     * 父菜单id
     */
    private String parentId;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单url
     */
    private String url;

    /**
     *  菜单图标
     */
    private String icon;

    /**
     * 菜单顺序
     */
    private int order;

    /**
     * 子菜单
     */
    private List<Menu> children;

    /**
     * 父菜单
     */
    private Menu parent;

    @Override
    public String toString() {
        return "Menu [id=" + id + ", parentId=" + parentId + ", name=" + name + ", url=" + url
            + ", icon=" + icon
            + ", order=" + order + ", children=" + children + ", parent=" + parent + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Menu) {
            Menu menu = (Menu) obj;
            return (id.equals(menu.id));
        }
        return super.equals(obj);
    }

}
