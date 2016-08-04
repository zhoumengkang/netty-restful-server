package net.mengkang.nettyrest.mysql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 *
 */

public class Mysql {

    private static final Logger logger = LoggerFactory.getLogger(Mysql.class);

    protected static void grammarCheck(String sql,DMLTypes sqlType){

        String dmlType = sqlType.toString().toLowerCase();

        try {
            if (!sql.toLowerCase().startsWith(dmlType)) {
                throw new Exception(dmlType + " statement needed");
            }

            // 除了 insert 之外的操作, 删改查都必须带上条件
            if(!dmlType.equals("insert") && sql.toLowerCase().indexOf("where") < 1){
                throw new Exception("where statement needed");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }


    }

    /**
     * 检查预处理语句和参数个数是否相符 以免出现参数太多,少写 ? 的情况
     *
     * @param sql
     * @param params
     * @return
     */
    protected static int getParameterNum(String sql, Object... params) {
        int paramSize = sql.length() - sql.replaceAll("\\?", "").length();

        try {
            if (paramSize != params.length) {
                throw new Exception("parameter's num error");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return paramSize;
    }

    /**
     * bind parameters
     *
     * @param statement
     * @param params
     * @return
     * @throws SQLException
     */
    protected static PreparedStatement bindParameters(PreparedStatement statement, Object... params) throws SQLException {

        int paramSize = params.length;

        for (int i = 1; i <= paramSize; i++) {
            Object param = params[i - 1];
            if (param instanceof java.lang.Integer) {
                statement.setInt(i, (Integer) param);
            } else if (param instanceof java.lang.String) {
                statement.setString(i, (String) param);
            } else if (param instanceof java.lang.Float) {
                statement.setFloat(i, (Float) param);
            } else if (param instanceof java.lang.Long) {
                statement.setLong(i, (Long) param);
            } else if (param instanceof java.lang.Double) {
                statement.setDouble(i, (Double) param);
            } else if (param instanceof java.sql.Date) {
                statement.setDate(i, (Date) param);
            } else {
                statement.setObject(i, param);
            }
        }

        return statement;
    }

    /**
     * 插入操作
     *
     * @param sql
     * @param params
     * @return
     */
    public static int insert(String sql, Object... params) {

        grammarCheck(sql,DMLTypes.INSERT);
        int paramSize = getParameterNum(sql,params);

        Connection        conn      = null;
        PreparedStatement statement = null;
        ResultSet         rs        = null;
        int               id        = 0;

        try {
            conn = JdbcPool.getWriteConnection();
            statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            if (paramSize > 0) {
                statement = bindParameters(statement, params);
            }

            statement.executeUpdate();
            rs = statement.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            logger.error("sql error",e);
        } finally {
            JdbcPool.release(conn, statement, rs);
        }

        return id;
    }

    /**
     * 删除操作
     *
     * @param sql
     * @param params
     * @return
     */
    public static int delete(String sql, Object... params) {
        return update(sql, params);
    }

    /**
     * 更新操作
     *
     * @param sql
     * @param params
     * @return
     */
    public static int update(String sql, Object... params) {

        if (!sql.toLowerCase().startsWith(DMLTypes.DELETE.toString().toLowerCase())
                && !sql.toLowerCase().startsWith(DMLTypes.UPDATE.toString().toLowerCase())
                && !sql.toLowerCase().startsWith(DMLTypes.REPLACE.toString().toLowerCase())) {
            try {
                throw new Exception("update statement needed");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        int paramSize = getParameterNum(sql,params);

        Connection        conn      = null;
        PreparedStatement statement = null;
        int               row       = 0;

        try {
            conn = JdbcPool.getWriteConnection();
            statement = conn.prepareStatement(sql);

            if (paramSize > 0) {
                statement = bindParameters(statement, params);
            }

            row = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("sql error",e);
        } finally {
            JdbcPool.release(conn, statement, null);
        }

        return row;
    }

    public static String getValue(String sql, Object... params) {
        grammarCheck(sql,DMLTypes.SELECT);
        int paramSize = getParameterNum(sql,params);

        Connection        conn      = null;
        PreparedStatement statement = null;
        ResultSet         rs        = null;
        String            res       = null;

        try {
            conn = JdbcPool.getReadConnection();
            statement = conn.prepareStatement(sql);

            if (paramSize > 0) {
                statement = bindParameters(statement, params);
            }

            rs = statement.executeQuery();
            if (rs.next()) {
                res = rs.getString(1);
            }
        } catch (SQLException e) {
            logger.error("sql error", e);
        } finally {
            JdbcPool.release(conn, statement, rs);
        }

        return res;
    }

    public static int getIntValue(String sql, Object... params) {

        grammarCheck(sql,DMLTypes.SELECT);
        int paramSize = getParameterNum(sql,params);

        if (!sql.toLowerCase().startsWith("select")) {
            return -1;
        }

        Connection        conn      = null;
        PreparedStatement statement = null;
        ResultSet         rs        = null;
        int               res       = 0;

        try {
            conn = JdbcPool.getReadConnection();
            statement = conn.prepareStatement(sql);

            if (paramSize > 0) {
                statement = bindParameters(statement, params);
            }

            rs = statement.executeQuery();
            if (rs.next()) {
                res = rs.getInt(1);
            }
        } catch (SQLException e) {
            logger.error("sql error",e);
        } finally {
            JdbcPool.release(conn, statement, rs);
        }

        return res;
    }
}
