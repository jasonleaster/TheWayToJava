package org.jasonleaster.springtutorial.model;

/**
 * Author: jasonleaster
 * Date  : 2017/4/22
 * Email : jasonleaster@gmail.com
 */
public class User {

    private String name;

    private int age;

    private Contact contact;

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

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
