package com.acs.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
 

@Configuration
public class DataSourceConfig {
	// @ConfigurationProperties : 프로퍼티 설절파일을 읽어들이는 어노테이션
	// application.properties의 설정한 커스텀데이터소스 설정부분을 prefix의 값으로 설정한다.
    @Bean
    @ConfigurationProperties("spring.datasource.hikari.mariadb")
    public DataSource mariaDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }
    
    @Primary
	@Bean
	public PlatformTransactionManager mariaTxManager() throws Exception {
		return new DataSourceTransactionManager(mariaDataSource());
	}
    
    @Bean
    @ConfigurationProperties("spring.datasource.hikari.oracle")
    public DataSource oracleDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }
    
	@Bean
	public PlatformTransactionManager oracleTxManager() throws Exception {
		return new DataSourceTransactionManager(oracleDataSource());
	}
}