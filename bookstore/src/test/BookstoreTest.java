package test;

import daoimpl.BookDAOImpl;
import entity.BookEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.testng.annotations.*;

public class BookstoreTest {
    private SessionFactory testSessionFactory;
    private Session testSession;

    @BeforeSuite
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

    @AfterSuite
    public void shutDownSession() {
        testSession.close();
        testSessionFactory.close();
    }
}
