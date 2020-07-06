package com.andykurtz.api.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfiguration {

    @Bean(name = "billingDatabaseDataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource billingDatabaseDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "billingDatabaseJdbcTemplate")
    public NamedParameterJdbcTemplate billingDatabaseJdbcTemplate(@Qualifier("billingDatabaseDataSource") DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

}
