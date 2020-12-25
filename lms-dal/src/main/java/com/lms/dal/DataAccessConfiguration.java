package com.lms.dal;

import com.lms.datasource.Configuration;
import com.lms.datasource.DataSourceFactory;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@EntityScan(basePackages = "com.lms.dal.entities")
@EnableJpaRepositories(basePackages = "com.lms.dal.repository")
@ComponentScan(basePackages = {"com.lms.dal.repository"})
@org.springframework.context.annotation.Configuration
public class DataAccessConfiguration {

    private DataSource dataSource;

    @Bean
    DataSource getDatasource() {
        if (dataSource == null) {
//            ConfigProvider configProvider = ConfigProviderFactory.getInstance();
            Configuration configuration =
                    Configuration.Builder.getInstance()
                            .setJdbcUrl("LMS_DATABASE_CONN_STRING")
                            .setUser("LMS_POSTGRES_USER")
                            .setPassword("LMS_POSTGRES_PASSWORD")
                            .build();
            dataSource = DataSourceFactory.createDataSource(configuration);
        }
        return dataSource;
    }

    @Bean
    HibernateExceptionTranslator hibernateExceptionTranslator() {
        return new HibernateExceptionTranslator();
    }

    @Bean
    PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(getDatasource());
        em.setPackagesToScan("com.lms.dal.entities");
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabasePlatform("org.hibernate.dialect.PostgreSQL9Dialect");
        vendorAdapter.setDatabase(Database.SQL_SERVER);
        vendorAdapter.setShowSql(true);
        em.setJpaVendorAdapter(vendorAdapter);
        Map<String, String> properties = new HashMap<>();
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL9Dialect");
        properties.put("hibernate.ddl-auto", "none");
        properties.put(
                "hibernate.implicit_naming_strategy",
                "org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy");
        properties.put(
                "hibernate.physical_naming_strategy",
                "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");
        properties.put("hibernate.generate_statistics", "true");

        em.setJpaPropertyMap(properties);
        return em;
    }
}
