package com.liucan.taskmanager.config;

import org.quartz.Scheduler;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.IOException;
import java.util.Properties;

/**
 * @author liucan
 * @date 2018/8/20
 * @brief quartz配置
 *        1.QuartzInitializerListener监听器可以监听到工程的启动，在工程停止再启动时让
 *          已有的定时任务继续执行
 *        2.当集群是放置在不同的机器上时，通常称之为水平集群。节点是跑在同一台机器是，称之为垂直集群
 *        3.运行水平集群时，时钟应当要同步，以免出现离奇且不可预知的行为。假如时钟没能够同步，Scheduler 实例将对其他节点的状态产生混乱。
 *        4.有几种简单的方法来保证时钟何持同步，而且也没有理由不这么做。最简单的同步计算机时钟的方式是使用某一个 Internet 时间服务器(Internet Time Server ITS)。
 */
@Configuration
public class QuartzConfig {
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
        factoryBean.setQuartzProperties(quartzProperties());
        return factoryBean;
    }

    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("properties/quartz.properties"));
        //在quartz.properties中的属性被读取并注入后再初始化对象
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    /*
     * quartz初始化监听器
     */
    @Bean
    public QuartzInitializerListener quartzInitializerListener() {
        return new QuartzInitializerListener();
    }

    /*
     * 通过SchedulerFactoryBean获取Scheduler的实例
     */
    @Bean
    public Scheduler scheduler() throws IOException {
        return schedulerFactoryBean().getScheduler();
    }
}
