package test;

import daoimpl.BookDAOImpl;
import entity.BookEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class BookstoreTest {
    private SessionFactory testSessionFactory;
    private Session testSession;

    @BeforeTest
    public void setUpSession() {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();

            testSessionFactory = configuration.buildSessionFactory();
            testSession = testSessionFactory.openSession();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError();
        }
    }
}
