package org.armitage.inc.AAInfo.setup.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "org.armitage.inc.AAInfo.dao")
@PropertySource("classpath:db.properties")
public class HibernateConfig {
	@Autowired
	private Environment env;

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DriverManagerDataSource dataSource){
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(dataSource);
		factory.setPackagesToScan("org.armitage.inc.AAInfo.entity");
		//factory.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
		factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		factory.setJpaProperties(hibernateProperties());
		
		return factory;
	}

	@Bean
	public DriverManagerDataSource dataSource() {
		DriverManagerDataSource db = new DriverManagerDataSource();
		db.setDriverClassName(env.getProperty("jdbc.driver"));
		db.setUrl(env.getProperty("jdbc.url"));
		db.setUsername(env.getProperty("jdbc.login"));
		db.setPassword(env.getProperty("jdbc.password"));

		return db;
	}

	private Properties hibernateProperties() {
		Properties prop = new Properties();
		prop.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
		prop.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		prop.put("hibernate.format_sql", env.getProperty("hibernate.format_sql"));
		prop.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));

		return prop;
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory, DriverManagerDataSource dataSource) {
		JpaTransactionManager tx = new JpaTransactionManager();
		tx.setEntityManagerFactory(entityManagerFactory);
		tx.setDataSource(dataSource);
		return tx;
	}
}
