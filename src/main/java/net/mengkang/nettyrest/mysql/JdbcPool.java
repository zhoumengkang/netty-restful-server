package net.mengkang.nettyrest.mysql;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JdbcPool {

    private static final Logger logger = LoggerFactory.getLogger(JdbcPool.class);

    /**
     * 在 java 中，编写数据库连接池需实现 java.sql.DataSource 接口
     * DBCP 连接池就是 java.sql.DataSource 接口的一个具体实现
     */
    private static DataSource writeDataSource = null;
    private static DataSource readDataSource  = null;

    static {
        try {
            Properties writeProp = new Properties();
            writeProp.load(new InputStreamReader(JdbcPool.class.getResourceAsStream("/write.db.properties"), "UTF-8"));
            writeDataSource = BasicDataSourceFactory.createDataSource(writeProp);

            Properties readProp = new Properties();
            readProp.load(new InputStreamReader(JdbcPool.class.getResourceAsStream("/read.db.properties"), "UTF-8"));
            readDataSource = BasicDataSourceFactory.createDataSource(readProp);

        } catch (Exception e) {
            logger.error("load db properties error",e);
            throw new ExceptionInInitializerError(e);
        }
    }

    public static Connection getWriteConnection() throws SQLException {
        return writeDataSource.getConnection();
    }

    public static Connection getReadConnection() throws SQLException {
        return readDataSource.getConnection();
    }

    public static void release(Connection conn, Statement st, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            rs = null;
        }

        if (st != null) {
            try {
                st.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
