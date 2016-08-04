package net.mengkang.nettyrest.mysql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class MySelect<A> extends Mysql {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Class clazz;

    private Map<String, Field> fieldMap = new HashMap<>();

    public MySelect(A bean) {
        this.clazz = bean.getClass();
        fieldMapInit();
    }

    /**
     * 数据库中的字段和bean属性值的映射
     * {
     * 数据库字段名               bean 属性名
     * create_time      :       createTime
     * }
     */
    private void fieldMapInit() {
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(DbFiled.class)) {
                fieldMap.put(field.getAnnotation(DbFiled.class).value(), field);
            } else {
                fieldMap.put(field.getName(), field);
            }
        }
    }

    /**
     * 解析查询语句的字段 目前还不支持 * 查询 (也最好不要用)
     *
     * @param sql
     * @return
     */
    private String[] parseSelectFields(String sql) {
        sql = sql.toLowerCase();

        String[] fieldArray = sql.substring(sql.indexOf("select") + 6, sql.indexOf("from")).split(",");
        int length = fieldArray.length;
        String[] fields = new String[length];

        for (int i = 0; i < length; i++) {
            fields[i] = fieldArray[i].trim().replace("`", "");
        }

//        try {
//            if (fields.length == 0) {
//                throw new Exception("no select fields");
//            }
//
//            if (fields.length == 1 && fields[0].equals("*")) {
//                throw new Exception("select * not supported");
//            }
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//        }

        return fields;
    }

    /**
     * 根据 fieldMap 里字段名和 bean 属性名的对应关系,根据查询字段取出对应的属性名
     * 然后通过反射设置值
     *
     * @param selectFields
     * @param resultSet
     * @return
     */
    @SuppressWarnings("unchecked")
    public A resultSet(String[] selectFields, ResultSet resultSet) {

        A bean = null;

        try {
            bean = (A) Class.forName(clazz.getName()).newInstance();

            for (int i = 0; i < selectFields.length; i++) {
                int j = i + 1;

                if (!fieldMap.containsKey(selectFields[i])){
                    continue;
                }

                Field field = fieldMap.get(selectFields[i]);
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

        } catch (SQLException e) {
            logger.error("resultSet parse error", e);
        } catch (IllegalAccessException | ClassNotFoundException | InstantiationException e) {
            e.printStackTrace();
        }

        return bean;
    }

    public A get(String sql, Object... params) {

        grammarCheck(sql, DMLTypes.SELECT);
        int paramSize = getParameterNum(sql, params);

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            conn = JdbcPool.getReadConnection();
            statement = conn.prepareStatement(sql);

            if (paramSize > 0) {
                statement = bindParameters(statement, params);
            }

            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String[] selectFields = parseSelectFields(sql);
                return resultSet(selectFields, resultSet);
            }
        } catch (SQLException e) {
            logger.error("sql error", e);
        } finally {
            JdbcPool.release(conn, statement, resultSet);
        }
        return null;
    }

    public List<A> list(String sql, Object... params) {
        List<A> beanList = new ArrayList<>();

        grammarCheck(sql, DMLTypes.SELECT);
        int paramSize = getParameterNum(sql, params);

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            conn = JdbcPool.getReadConnection();
            statement = conn.prepareStatement(sql);

            if (paramSize > 0) {
                statement = bindParameters(statement, params);
            }

            resultSet = statement.executeQuery();
            String[] selectFields = parseSelectFields(sql);
            while (resultSet.next()) {
                beanList.add(resultSet(selectFields, resultSet));
            }
        } catch (Exception e) {
            logger.error("sql error", e);
        } finally {
            JdbcPool.release(conn, statement, resultSet);
        }

        return beanList;
    }
}
