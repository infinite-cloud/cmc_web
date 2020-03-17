package test;

import daoimpl.OrderedBookDAOImpl;
import entity.OrderedBookEntity;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class OrderedBookTest extends GenericTest {
    private OrderedBookDAOImpl orderedBookDAO;

    @BeforeClass(dependsOnMethods = "setUpSession")
    protected void setUpDAO() {
        orderedBookDAO = new OrderedBookDAOImpl();
        orderedBookDAO.setSession(testSession);
    }

    @Test(priority = 0, groups = "orderedBook")
    public void getOrderedBook() {
        Assert.assertNull(orderedBookDAO.getByCompositeId((long) -1, (long) 1));
        OrderedBookEntity orderedBook = orderedBookDAO.getByCompositeId((long) 2, (long) 1);
        Assert.assertNotNull(orderedBook);
        Assert.assertEquals((int) orderedBook.getBookCount(), 1);
        Assert.assertEquals((long) orderedBook.getBookId().getBookId(), 1);
        Assert.assertEquals((long) orderedBook.getOrder().getOrderId(), 2);

        List<OrderedBookEntity> orders = orderedBookDAO.getByOrderId((long) 2);
        Assert.assertFalse(orders.isEmpty());
        Assert.assertEquals(orders.get(0), orderedBook);
    }
}
