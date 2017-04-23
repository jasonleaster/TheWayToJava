package org.jasonleaster.springtutorial.ext.form;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * Author: jasonleaster
 * Date  : 2017/4/22
 * Email : jasonleaster@gmail.com
 */
@XmlRootElement(name = "user")
public class User {

    // 普通对象的绑定
    private String name;

    private int age;

    // 多层级对象的绑定
    private Contact contact;

    @XmlElement // 必须绑定在getter方法上，不能绑定在成员属性上，否则会抛异常
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @XmlElement
    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
