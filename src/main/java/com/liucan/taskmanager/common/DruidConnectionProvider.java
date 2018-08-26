package com.liucan.taskmanager.common;

import com.alibaba.druid.pool.DruidDataSource;
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

    }
}
