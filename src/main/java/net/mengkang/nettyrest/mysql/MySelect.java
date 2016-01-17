package net.mengkang.nettyrest.mysql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.*;
import java.sql.Date;
import java.util.*;


public class MySelect<A> extends Mysql {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private A bean;

    public MySelect(A bean) {
        this.bean = bean;
    }

    // todo select * xxx
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

    private List<Map<String, Field>> getSelectFieldMap(String[] selectFields) {
        int      length       = selectFields.length;

        Class   c        = bean.getClass();
        Field[] fields   = c.getDeclaredFields();
        int     fieldNum = fields.length;

        List<Map<String, Field>> fieldMap = new ArrayList<>();

        //todo, inefficient algorithm
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < fieldNum; j++) {
                Field field = fields[j];
                if (field.getName().equals(selectFields[i])
                        || (field.isAnnotationPresent(DbFiled.class)
                        && field.getAnnotation(DbFiled.class).value().equals(selectFields[i]))
                ) {
                    Map<String,Field> oneFieldMap = new HashMap<>();
                    oneFieldMap.put(selectFields[i],field);
                    fieldMap.add(oneFieldMap);
                    break;
                }
            }
        }

        return fieldMap;
    }

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
                String[] selectFields = parseSelectFields(sql);
                List<Map<String, Field>> selectFieldMap = getSelectFieldMap(selectFields);
                if (resultSet.next()){
                    for (int i = 0; i < selectFieldMap.size(); i++) {
                        int j = i + 1;
                        Field field = selectFieldMap.get(i).get(selectFields[i]);
                        field.setAccessible(true);
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
                    }
                }

            } catch (SQLException e) {
                logger.error("resultSet parse error", e);
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

    public List<A> list(String sql, Object... params) {
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
