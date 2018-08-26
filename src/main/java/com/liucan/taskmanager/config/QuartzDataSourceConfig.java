package com.liucan.taskmanager.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @author liucan
 * @date 2018/7/1
 * @brief mysql多数据源配置, 多数据源配置要求必须有一个是主, 用@Primary注解
 */
@Configuration
@EnableTransactionManagement
@PropertySource(value = "classpath:properties/quartz.properties")
@MapperScan(basePackages = "com.liucan.taskmanager.mybatis.quartz.dao", sqlSessionFactoryRef = "quartzSqlSessionFactory")
public class QuartzDataSourceConfig {
    @Value("${quartz.driver}")
    private String driver;
    @Value("${quartz.url}")
    private String url;
    @Value("${quartz.username}")
    private String userName;
    @Value("${quartz.password}")
    private String password;

    @Bean(destroyMethod = "close")
    @Primary
    public DruidDataSource quartzDataSource(DruidConfig druidConfig) {
        DruidDataSource druidDataSource = druidConfig.dataSource();
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(userName);
        druidDataSource.setPassword(password);
        druidDataSource.setDriverClassName(driver);
        return druidDataSource;
    }

    @Bean
    @Primary
    public SqlSessionFactory quartzSqlSessionFactory(@Qualifier("quartzDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mapper/quartz/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    @Primary
    public DataSourceTransactionManager quartzTransactionManager(@Qualifier("quartzDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
