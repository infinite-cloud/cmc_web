package test;

import daoimpl.PublisherDAOImpl;
import entity.PublisherEntity;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class PublisherTest extends GenericTest {
    private PublisherDAOImpl publisherDAO;

    @BeforeClass(dependsOnMethods = "setUpSession")
    protected void setUpDAO() {
        publisherDAO = new PublisherDAOImpl();
        publisherDAO.setSession(testSession);
    }

    @Test(priority = 0, groups = "publisher")
    public void getPublisher() {
        Assert.assertEquals(publisherDAO.getByName("").size(), 4);
        Assert.assertTrue(publisherDAO.getByName("Test").isEmpty());
        List<PublisherEntity> publishers = publisherDAO.getByName("лабиринт");
        Assert.assertEquals(publishers.size(), 1);
        Assert.assertEquals((long) publishers.get(0).getPublisherId(), 3);
    }
}
