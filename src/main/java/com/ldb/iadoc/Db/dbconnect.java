package com.ldb.iadoc.Db;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class dbconnect {
    @Bean(name = "iadoc")
    @ConfigurationProperties(prefix = "spring.iadoc")
    public DataSource IADOCJdbcTemplate() {
        return DataSourceBuilder.create().build();
    }
    @Bean(name = "IADOCJdbcTemplate")
    public JdbcTemplate IADOCDatasource(@Qualifier("iadoc") DataSource IADOCJdbcTemplate) {
        return new JdbcTemplate(IADOCJdbcTemplate);
    }
}
