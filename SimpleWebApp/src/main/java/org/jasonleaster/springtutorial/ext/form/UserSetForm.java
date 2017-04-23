package org.jasonleaster.springtutorial.ext.form;

import java.util.HashSet;
import java.util.Set;

/**
 * Author: jasonleaster
 * Date  : 2017/4/23
 * Email : jasonleaster@gmail.com
 *
 * 并不推荐Set元素做表单接收模型
 */
public class UserSetForm {

    private Set<User> users;

    public UserSetForm() {
        // 这里必须对该表单初始化，否则form表单会抛异常
        this.users = new HashSet<>();
        // set 的大小为2，所以form表单中元素的索引不能大于Set的大小set.getSize()，否则会抛异常
        this.users.add(new User());
        this.users.add(new User());
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
