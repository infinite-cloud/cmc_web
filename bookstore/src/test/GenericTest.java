package test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

abstract public class GenericTest {
    private SessionFactory testSessionFactory;
    protected Session testSession;

    @BeforeClass
    protected void setUpSession() {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();

            testSessionFactory = configuration.buildSessionFactory();
            testSession = testSessionFactory.openSession();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError();
        }
    }

    @BeforeClass(dependsOnMethods = "setUpSession")
    abstract protected void setUpDAO();

    @AfterClass
    protected void shutDownSession() {
        testSession.close();
        testSessionFactory.close();
    }
}
