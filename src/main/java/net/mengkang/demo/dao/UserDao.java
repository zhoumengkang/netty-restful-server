package net.mengkang.demo.dao;

import net.mengkang.demo.entity.User;
import net.mengkang.nettyrest.mysql.MySelect;
import net.mengkang.nettyrest.mysql.Mysql;

public class UserDao {

    /**
     * 单独获取用户的名称
     * 面向过程的方式
     * 仅仅为演示其 Mysql 类的使用方便，不推荐这种方式来获取结果
     *
     * @param id
     * @return
     */
    public String getName(int id){
        String sql = "select name from user where id=?";
        return Mysql.getValue(sql,id);
    }

    public User getOne(int id){
        String sql = "select id,name from user where id=?";
        return new MySelect<User>().get(sql,id);
    }

}
