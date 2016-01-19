package net.mengkang.demo.dao;

import net.mengkang.demo.entity.UserLite;
import net.mengkang.nettyrest.mysql.MySelect;
import net.mengkang.nettyrest.mysql.Mysql;

import java.util.List;

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
        return Mysql.getValue(sql, id);
    }

    public void getOne(int id){
        String sql = "select id,name,icon from user where id=?";
        MySelect mySelect = new MySelect<>(new UserLite());
        System.out.println(mySelect.get(sql, id).toString());

        String sql2 = "select id,name from user where id=?";
        System.out.println(mySelect.get(sql2, 2).toString());

        String sql3 = "select id,name from user limit 10";
        List<UserLite> userLiteList = mySelect.list(sql3);
    }

}
