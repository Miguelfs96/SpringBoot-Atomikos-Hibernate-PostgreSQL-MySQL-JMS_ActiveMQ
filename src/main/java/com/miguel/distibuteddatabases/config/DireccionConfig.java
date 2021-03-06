package com.miguel.distibuteddatabases.config;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Properties;

@SuppressWarnings("ContextJavaBeanUnresolvedMethodsInspection")
@Configuration
@DependsOn("transactionManager")
@EnableJpaRepositories(basePackages = "com.miguel.distibuteddatabases.repository.direccion", entityManagerFactoryRef = "twoEntityManager")
//@EnableConfigurationProperties
public class DireccionConfig {

    @Autowired
    @Qualifier(value = "psqlJpaVendorAdapter")
    private JpaVendorAdapter jpaVendorAdapter;

    @Bean(name = "twoDataSource", initMethod = "init", destroyMethod = "close")
    public DataSource twoDataSource() {
        AtomikosDataSourceBean ds = new AtomikosDataSourceBean();
        ds.setUniqueResourceName("postgres");
        ds.setXaDataSourceClassName("org.postgresql.xa.PGXADataSource");
        Properties p = new Properties();
        p.setProperty ( "user" , "postgres" );
        p.setProperty ( "password" , "qwerty" );
        p.setProperty ( "serverName" , "localhost" );
        p.setProperty ( "portNumber" , "5432" );
        p.setProperty ( "databaseName" , "two" );
        ds.setXaProperties ( p );
        return ds;
    }

    @Bean(name = "twoEntityManager")
    @DependsOn("transactionManager")
    public LocalContainerEntityManagerFactoryBean orderEntityManger() {

        HashMap<String,Object> properties = new HashMap<>();
        properties.put("hibernate.transaction.jta.platform", AtomikosJtaPlatform.class.getName());
        properties.put("javax.persistence.transactionType", "JTA");

        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setJtaDataSource(twoDataSource());
        entityManager.setJpaVendorAdapter(jpaVendorAdapter);   //No deberia ser asi
        entityManager.setPackagesToScan("com.miguel.distibuteddatabases.model");
        entityManager.setPersistenceUnitName("twoPersistenceUnit");
        entityManager.setJpaPropertyMap(properties);
        return entityManager;
    }
}
