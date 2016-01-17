package net.mengkang.demo.entity;

import net.mengkang.nettyrest.mysql.DbFiled;

/**
 * Created by zhoumengkang on 17/1/16.
 */
public class UserLite {
    private int    id;
    private String name;
    private String icon;
    private char   sex;
    private int    birthday;
    private char   status;
    @DbFiled("register_ts")
    private int    ts;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public int getBirthday() {
        return birthday;
    }

    public void setBirthday(int birthday) {
        this.birthday = birthday;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public int getTs() {
        return ts;
    }

    public void setTs(int ts) {
        this.ts = ts;
    }
}
