package org.jasonleaster.rbac.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Teacher {

    private String id;
    private String name;
    // 科目
    private String subject;
    private String info;
    private String img;
    private int order;

    @Override
    public String toString() {
        return "Teacher [id=" + id + ", name=" + name + ", subject=" + subject + ", info=" + info
            + ", img=" + img
            + ", order=" + order + "]";
    }

}
