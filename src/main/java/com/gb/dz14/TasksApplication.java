package com.gb.dz14;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

@SpringBootApplication
@EnableAutoConfiguration(exclude = { //
        DataSourceAutoConfiguration.class, //
        DataSourceTransactionManagerAutoConfiguration.class, //
        HibernateJpaAutoConfiguration.class})

public class TasksApplication {

    @Autowired
    private Environment env;

    public static void main(String[] args) {
        SpringApplication.run(TasksApplication.class, args);
    }

    @Bean(name = "dataSource")
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        // See: application.properties
        dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));

        System.out.println("## getDataSource: " + dataSource);

        return dataSource;
    }

    @Autowired
    @Bean(name = "sessionFactory")
    public SessionFactory getSessionFactory(DataSource dataSource) throws Exception {
        Properties properties = new Properties();

        try {
            // See: application.properties
            properties.put("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));
            properties.put("hibernate.show_sql", env.getProperty("spring.jpa.show-sql"));
            properties.put("current_session_context_class", //
                    env.getProperty("spring.jpa.properties.hibernate.current_session_context_class"));


            // Fix Postgres JPA Error:
            // Method org.postgresql.jdbc.PgConnection.createClob() is not yet implemented.
            // properties.put("hibernate.temp.use_jdbc_metadata_defaults",false);

            LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();

            // Package contain entity classes
            factoryBean.setPackagesToScan(new String[]{""});
            factoryBean.setDataSource(dataSource);
            factoryBean.setHibernateProperties(properties);
            factoryBean.afterPropertiesSet();
            //
            SessionFactory sf = factoryBean.getObject();
            return sf;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Autowired
    @Bean(name = "transactionManager")
    public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {

        try {
            final HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager(sessionFactory);
            return hibernateTransactionManager;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }

    }

}
