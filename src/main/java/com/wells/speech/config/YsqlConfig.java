package com.wells.speech.config;

import com.yugabyte.data.jdbc.datasource.YugabyteTransactionManager;
import com.yugabyte.data.jdbc.repository.config.AbstractYugabyteJdbcConfiguration;
import com.yugabyte.data.jdbc.repository.config.EnableYsqlRepositories;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.TransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableYsqlRepositories(basePackages = "com.wells.speech.models")
@Primary
public class YsqlConfig extends AbstractYugabyteJdbcConfiguration {
    @Bean
    DataSource dataSource() {
        Properties poolProperties = new Properties();
        poolProperties.setProperty("dataSourceClassName",
                "com.yugabyte.ysql.YBClusterAwareDataSource");
        poolProperties.setProperty("dataSource.serverName", "20.115.82.248");
        poolProperties.setProperty("dataSource.portNumber", "5433");
        poolProperties.setProperty("dataSource.user", "yugabyte");
        poolProperties.setProperty("dataSource.password", "Hackathon22!");
        poolProperties.setProperty("dataSource.loadBalance", "true");
        HikariConfig hikariConfig = new HikariConfig(poolProperties);
        DataSource ybClusterAwareDataSource = new HikariDataSource(hikariConfig);
        return ybClusterAwareDataSource;
    }
    @Bean
    TransactionManager transactionManager(DataSource dataSource) {
        return new YugabyteTransactionManager(dataSource);
    }
}