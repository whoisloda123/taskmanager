package com.liucan.taskmanager.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @author liucan
 * @date 2018/7/1
 * @brief mysql多数据源配置
 */
@Configuration
@EnableTransactionManagement
@PropertySource(value = "classpath:properties/mysqlJavaLearn.properties")
@MapperScan(basePackages = "com.liucan.taskmanager.mybatis.javalearn.dao", sqlSessionFactoryRef = "javaLearnSqlSessionFactory")
public class JavaLearnDataSourceConfig {
    //javalearn
    @Value("${mysql.javaLearn.driver}")
    private String driver;
    @Value("${mysql.javaLearn.url}")
    private String url;
    @Value("${mysql.javaLearn.username}")
    private String userName;
    @Value("${mysql.javaLearn.password}")
    private String password;

    @Bean(destroyMethod = "close")
    public DruidDataSource javaLearnDataSource(DruidConfig druidConfig) {
        DruidDataSource druidDataSource = druidConfig.dataSource();
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(userName);
        druidDataSource.setPassword(password);
        druidDataSource.setDriverClassName(driver);
        return druidDataSource;
    }

    @Bean
    public SqlSessionFactory javaLearnSqlSessionFactory(@Qualifier("javaLearnDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mapper/javalearn/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public DataSourceTransactionManager javaLearnTransactionManager(@Qualifier("javaLearnDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
