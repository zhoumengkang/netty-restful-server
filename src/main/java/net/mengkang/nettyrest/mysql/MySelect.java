package net.mengkang.nettyrest.mysql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhoumengkang on 16/1/16.
 */
public class MySelect<A> extends Mysql{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public A get(String sql,A bean, Object... params){

        grammarCheck(sql,DMLTypes.SELECT);
        int paramSize = getParameterNum(sql,params);

        Connection        conn = null;
        PreparedStatement statement = null;
        ResultSet         resultSet = null;
        try {
            conn = JdbcPool.getReadConnection();
            statement = conn.prepareStatement(sql);

            if(paramSize > 0){
                statement = bindParameters(statement, params);
            }

            resultSet = statement.executeQuery();
            try {
                if (resultSet.next()) {
                    // todo ...

                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JdbcPool.release(conn,statement,resultSet);
        }
        return bean;
    }

    public List<A> list(String sql,A bean, Object... params){
        List<A> beanList = new ArrayList<>();
        beanList.add(bean);

        grammarCheck(sql, DMLTypes.SELECT);
        int paramSize = getParameterNum(sql, params);

        Connection        conn = null;
        PreparedStatement statement = null;
        ResultSet         resultSet = null;
        try {
            conn = JdbcPool.getReadConnection();
            statement = conn.prepareStatement(sql);

            if(paramSize > 0){
                statement = bindParameters(statement,params);
            }

            resultSet = statement.executeQuery();
            try {
                while (resultSet.next()) {
                    // todo ...

                }
            }catch (SQLException e){
                e.printStackTrace();
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JdbcPool.release(conn,statement,resultSet);
        }

        return beanList;
    }
}
