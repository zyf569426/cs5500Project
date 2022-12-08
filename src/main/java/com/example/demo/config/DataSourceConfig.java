package com.example.demo.config;

import javax.sql.DataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {
	@Bean
	public DataSource datasource() {
		return DataSourceBuilder.create()
			.driverClassName("com.mysql.cj.jdbc.Driver")
			.url("jdbc:mysql://database-1.clv56pklcwju.us-east-1.rds.amazonaws.com:3306/db")
			.username("root")
			.password("password")
			.build();
	}
}