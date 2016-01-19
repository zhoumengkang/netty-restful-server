package net.mengkang.demo.entity;

import net.mengkang.nettyrest.mysql.DbFiled;


public class UserLite {
    private int    id;
    private String name;
    private String icon;
    private int    sex;
    private int    birthday;
    private int    status;
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

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getBirthday() {
        return birthday;
    }

    public void setBirthday(int birthday) {
        this.birthday = birthday;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTs() {
        return ts;
    }

    public void setTs(int ts) {
        this.ts = ts;
    }

    @Override
    public String toString() {
        return "UserLite{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", sex=" + sex +
                ", birthday=" + birthday +
                ", status=" + status +
                ", ts=" + ts +
                '}';
    }
}
