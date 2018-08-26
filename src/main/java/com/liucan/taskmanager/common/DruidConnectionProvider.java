package com.liucan.taskmanager.common;

import com.alibaba.druid.pool.DruidDataSource;
import com.liucan.taskmanager.config.DruidConfig;
import org.quartz.utils.ConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author liucan
 * @date 2018/8/25
 * @brief
 */
@Configuration
public class DruidConnectionProvider implements ConnectionProvider {
    @Autowired
    @Qualifier("quartzDataSource")
    private DruidDataSource druidDataSource;
    @Autowired
    private DruidConfig druidConfig;

    @Override
    public Connection getConnection() throws SQLException {
        DruidDataSource druidDataSource = new DruidDataSource();
        ;
        druidDataSource.setUrl("jdbc:mysql://192.168.32.128:3306/quartz?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("123456");
        druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        druidDataSource.setMaxActive(10);
        return druidDataSource.getConnection();
    }

    @Override
    public void shutdown() throws SQLException {
        druidDataSource.close();
    }

    @Override
    public void initialize() throws SQLException {

    }
}
