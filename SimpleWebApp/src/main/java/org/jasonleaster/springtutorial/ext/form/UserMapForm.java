package org.jasonleaster.springtutorial.ext.form;

import java.util.Map;

/**
 * Author: jasonleaster
 * Date  : 2017/4/23
 * Email : jasonleaster@gmail.com
 */
public class UserMapForm {
    private Map<String, User> users;

    public Map<String, User> getUsers() {
        return users;
    }

    public void setUsers(
        Map<String, User> users) {
        this.users = users;
    }
}
