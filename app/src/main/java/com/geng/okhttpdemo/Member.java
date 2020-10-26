package com.geng.okhttpdemo;

public class Member {
    private String name;
    private String sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Member{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }

}
