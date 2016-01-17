package net.mengkang.nettyrest.mysql;

import net.mengkang.nettyrest.mysql.DMLTypes;
import net.mengkang.nettyrest.mysql.JdbcPool;
import net.mengkang.nettyrest.mysql.Mysql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class MySelect<A> extends Mysql {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String[] parseSelectFields(String sql) {
        sql = sql.toLowerCase();

        String[] fieldArray = sql.substring(sql.indexOf("select") + 6, sql.indexOf("from")).split(",");
        int      length     = fieldArray.length;
        String[] fields     = new String[length];

        for (int i = 0; i < length; i++) {
            fields[i] = fieldArray[i].trim().replace("`", "");
        }

        return fields;
    }

    public A get(String sql, A bean, Object... params) {

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
                String[] selectFields = parseSelectFields(sql);

                Class c = bean.getClass();
                Field[] fields = c.getDeclaredFields();

                if (resultSet.next()) {
                    for (int i = 0; i < selectFields.length; i++) {
                        int j = i + 1;

                        for (Field field : fields) {
                            field.setAccessible(true);
                            if (field.getName().equals(selectFields[i]) ||
                                    (field.isAnnotationPresent(DbFiled.class) &&
                                            field.getAnnotation(DbFiled.class).value().equals(selectFields[i]))
                                    ) {

                                Class fieldClass = field.getType();
                                if (fieldClass == String.class) {
                                    field.set(bean, resultSet.getString(j));
                                } else if (fieldClass == int.class || fieldClass == Integer.class) {
                                    field.set(bean, resultSet.getInt(j));
                                } else if (fieldClass == float.class || fieldClass == Float.class) {
                                    field.set(bean, resultSet.getFloat(j));
                                } else if (fieldClass == double.class || fieldClass == Double.class) {
                                    field.set(bean, resultSet.getDouble(j));
                                } else if (fieldClass == long.class || fieldClass == Long.class) {
                                    field.set(bean, resultSet.getLong(j));
                                } else if (fieldClass == Date.class) {
                                    field.set(bean, resultSet.getDate(j));
                                }
                                break;
                            }
                        }
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
                logger.error("resultSet error", e);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            logger.error("sql error", e);
        } finally {
            JdbcPool.release(conn, statement, resultSet);
        }
        return bean;
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
