package com.efun;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import javax.sql.DataSource;

/**
 * Created by kinven on 16-11-9.
 */
@Configuration
public class DataSourceConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(DataSourceConfiguration.class);
    @Bean(name="mysqlDataSource")
    @Primary
    @ConfigurationProperties(prefix="datasource.mysql")
    public DataSource primaryDataSource() {
        LOG.info("-------------------- my DataSource init ---------------------");
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }

    @Bean(name="hbaseDataSource")
    @ConfigurationProperties(prefix="datasource.hbase")
    public DataSource secondaryDataSource() {
        LOG.info("-------------------- hbase DataSource init ---------------------");
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }
}
