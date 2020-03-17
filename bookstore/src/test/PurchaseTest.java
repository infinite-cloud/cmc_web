package test;

import daoimpl.AccountDAOImpl;
import daoimpl.BookDAOImpl;
import daoimpl.OrderedBookDAOImpl;
import daoimpl.PurchaseDAOImpl;
import entity.BookEntity;
import entity.OrderedBookEntity;
import entity.PurchaseEntity;
import javafx.util.Pair;
import org.hibernate.Transaction;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PurchaseTest extends GenericTest {
    private PurchaseDAOImpl purchaseDAO;
    private AccountDAOImpl accountDAO;
    private BookDAOImpl bookDAO;
    private OrderedBookDAOImpl orderedBookDAO;

    @BeforeClass(dependsOnMethods = "setUpSession")
    protected void setUpDAO() {
        purchaseDAO = new PurchaseDAOImpl();
        accountDAO = new AccountDAOImpl();
        bookDAO = new BookDAOImpl();
        orderedBookDAO = new OrderedBookDAOImpl();
        purchaseDAO.setSession(testSession);
        accountDAO.setSession(testSession);
        bookDAO.setSession(testSession);
        orderedBookDAO.setSession(testSession);
    }

    @Test(priority = 0, groups = "purchase")
    public void getPurchase() {
        List<PurchaseEntity> purchases = purchaseDAO.getRelevant();
        Assert.assertEquals(purchases.size(), 3);

        for (PurchaseEntity purchase : purchases) {
            Assert.assertTrue(Arrays.asList((long) 1, (long) 3, (long) 4)
                    .contains(purchase.getOrderId()));
        }

        Assert.assertTrue(purchaseDAO.getByUserId((long) -1).isEmpty());
        purchases = purchaseDAO.getByUserId((long) 3);
        Assert.assertEquals(purchases.size(), 2);
        Assert.assertTrue(Arrays.asList((long) 2, (long) 3)
                .contains(purchases.get(0).getOrderId()));
        Assert.assertTrue(Arrays.asList((long) 2, (long) 3)
                .contains(purchases.get(1).getOrderId()));
    }

    @SuppressWarnings("unchecked")
    @Test(priority = 4, groups = "purchase", dependsOnGroups = "book")
    public void addPurchase() {
        Transaction tx = testSession.beginTransaction();

        PurchaseEntity purchase = new PurchaseEntity();
        purchase.setOrderDate(new Timestamp(System.currentTimeMillis()));
        purchase.setDeliveryAddress("Test");
        purchase.setDeliveryDate(new Timestamp(System.currentTimeMillis() + 100000));
        purchase.setTotalPrice(100.);
        purchase.setOrderStatus(PurchaseEntity.OrderStatus.IN_PROCESSING);
        purchase.setUserId(accountDAO.getById((long) 1));

        purchaseDAO.save(purchase, new ArrayList<>());

        tx.commit();

        List<PurchaseEntity> allPurchases = purchaseDAO.getAll();
        Assert.assertEquals(allPurchases.size(), 4);

        tx = testSession.beginTransaction();

        List<Pair<BookEntity, Integer>> purchaseList =
                Arrays.asList(new Pair(bookDAO.getById((long) 1), 20));

        purchaseDAO.save(purchase, purchaseList);
        Long id = purchase.getOrderId();

        tx.commit();

        Assert.assertEquals(purchaseDAO.getAll().size(), 5);
        Assert.assertEquals(purchaseDAO.getById(id), purchase);

        List<OrderedBookEntity> orderedBooks = orderedBookDAO.getByOrderId(id);

        Assert.assertEquals(orderedBooks.size(), 1);
        Assert.assertEquals(orderedBooks.get(0).getBookId(), bookDAO.getById((long) 1));

        tx = testSession.beginTransaction();

        purchaseDAO.delete(purchase);

        tx.commit();

        testSession.clear();
        List<PurchaseEntity> newAllPurchases = purchaseDAO.getAll();
        Assert.assertEquals(newAllPurchases.size(), 4);
        Assert.assertEquals(newAllPurchases, allPurchases);
    }
}
