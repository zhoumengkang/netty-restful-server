package net.mengkang.nettyrest.mysql;

import net.mengkang.nettyrest.mysql.DMLTypes;
import net.mengkang.nettyrest.mysql.JdbcPool;
import net.mengkang.nettyrest.mysql.Mysql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class MySelect<A> extends Mysql {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public A get(String sql, Object... params) {

        grammarCheck(sql, DMLTypes.SELECT);
        int paramSize = getParameterNum(sql, params);

        Connection        conn      = null;
        PreparedStatement statement = null;
        ResultSet         resultSet = null;

        try {
            conn = JdbcPool.getReadConnection();
            statement = conn.prepareStatement(sql);

            if (paramSize > 0) {
                statement = bindParameters(statement, params);
            }

            resultSet = statement.executeQuery();
            try {
                if (resultSet.next()) {
                    // todo ...

                }
            } catch (Exception e) {
                logger.error("resultSet error", e);
            }

        } catch (SQLException e) {
            logger.error("sql error", e);
        } finally {
            JdbcPool.release(conn, statement, resultSet);
        }
        return null;
    }

    public List<A> list(String sql, A bean, Object... params) {
        List<A> beanList = new ArrayList<>();
        beanList.add(bean);

        grammarCheck(sql, DMLTypes.SELECT);
        int paramSize = getParameterNum(sql, params);

        Connection        conn      = null;
        PreparedStatement statement = null;
        ResultSet         resultSet = null;
        try {
            conn = JdbcPool.getReadConnection();
            statement = conn.prepareStatement(sql);

            if (paramSize > 0) {
                statement = bindParameters(statement, params);
            }

            resultSet = statement.executeQuery();
            try {
                while (resultSet.next()) {
                    // todo ...

                }
            } catch (SQLException e) {
                logger.error("resultSet error", e);
            }

        } catch (Exception e) {
            logger.error("sql error", e);
        } finally {
            JdbcPool.release(conn, statement, resultSet);
        }

        return beanList;
    }
}
