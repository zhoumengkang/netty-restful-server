package net.mengkang.demo.entity;

import net.mengkang.demo.bo.Icon;
import net.mengkang.nettyrest.BaseEntity;

public class User extends BaseEntity {
    private int    id;
    private String name;
    private Icon   icon;
    private int    age;

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

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public User(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public User(int id, String name, Icon icon, int age) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.age = age;
    }

    public User() {
    }
}
