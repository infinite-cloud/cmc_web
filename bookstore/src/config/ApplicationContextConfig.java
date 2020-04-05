package config;

import daoimpl.BookDAOImpl;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

@Configuration
@ComponentScan("config")
@ComponentScan("controller")
@EnableTransactionManagement
@PropertySource("classpath:/webapp/WEB-INF/database.properties")
public class ApplicationContextConfig {
    @Autowired
    private Environment env;

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();

        resourceBundleMessageSource.setBasenames("/resources/messages/validator");

        return resourceBundleMessageSource;
    }

    @Bean(name = "viewResolver")
    public InternalResourceViewResolver getViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

        viewResolver.setPrefix("/pages/");
        viewResolver.setSuffix(".jsp");

        return viewResolver;
    }

    @Bean(name = "dataSource")
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty("postgresql.driver")));
        dataSource.setUrl(Objects.requireNonNull(env.getProperty("postgresql.localhost")) +
                Objects.requireNonNull(env.getProperty("postgresql.database")));
        dataSource.setUsername(Objects.requireNonNull(env.getProperty("postgresql.user")));
        dataSource.setPassword(Objects.requireNonNull(env.getProperty("postgresql.password")));

        return dataSource;
    }

    @Autowired
    @Bean(name = "sessionFactory")
    public SessionFactory getSessionFactory(DataSource dataSource) throws Exception {
        Properties properties = new Properties();

        properties.put("hibernate.dialect",
                Objects.requireNonNull(env.getProperty("hibernate.dialect")));
        properties.put("hibernate.show_sql",
                Objects.requireNonNull(env.getProperty("hibernate.show_sql")));
        properties.put("current_session_context_class",
                Objects.requireNonNull(env.getProperty("current_session_context_class")));
        properties.put("hibernate.hbm2ddl.auto",
                Objects.requireNonNull(env.getProperty("hibernate.hbm2ddl.auto")));

        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();

        factoryBean.setPackagesToScan("entity");
        factoryBean.setDataSource(dataSource);
        factoryBean.setHibernateProperties(properties);
        factoryBean.afterPropertiesSet();

        return factoryBean.getObject();
    }

    @Autowired
    @Bean(name = "transactionManager")
    public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }

    @Bean(name = "bookDAO")
    public BookDAOImpl getBookDAO() {
        return new BookDAOImpl();
    }
}
