package org.jasonleaster.springtutorial.ext.form;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * Author: jasonleaster
 * Date  : 2017/4/23
 * Email : jasonleaster@gmail.com
 */
public class Admin {

    private String name;

    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
