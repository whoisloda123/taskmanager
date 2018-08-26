package com.liucan.taskmanager.common;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Data;
import org.quartz.utils.ConnectionProvider;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author liucan
 * @date 2018/8/25
 * @brief 自定义quartz连接池，使用druid连接池
 */
@Data
public class DruidConnectionProvider implements ConnectionProvider {
    //常量配置，与quartz.properties文件的key保持一致(去掉前缀)，同时提供set方法，Quartz框架自动注入值
    private String driver;
    private String url;
    private String username;
    private String password;
    private int maxConnection;

    private DruidDataSource druidDataSource;

    @Override
    public Connection getConnection() throws SQLException {
        return druidDataSource.getConnection();
    }

    @Override
    public void shutdown() throws SQLException {
        druidDataSource.close();
    }

    @Override
    public void initialize() throws SQLException {
        druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(driver);
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);
        druidDataSource.setMaxActive(maxConnection);
    }
}
