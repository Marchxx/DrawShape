package com.march.common.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Druid数据源工具类
 */
public class DruidUtil {

    private static DataSource ds=null;

    //通过静态代码块，获取连接池对象
    static {
        try {
            //1.通过类加载器将配置文件加载到properties中
            Properties properties=new Properties();
            InputStream inputStream = DruidUtil.class.getClassLoader().getResourceAsStream("druid.properties");
            properties.load(inputStream);
           ds = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //返回一个连接
    public static Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            System.out.println("返回数据库连接发生异常，发生空值!");
            return null;
        }
    }

    //返回一个连接池对象
    public static DataSource getDataSource() {
        return ds;
    }

    //关闭资源
    public static void close(ResultSet resultSet, Statement statement, Connection connection) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //关闭资源的重载形式
    public static void close(Statement statement, Connection connection) {
        close(null, statement, connection);
    }

}
