package test;

import daoimpl.AccountDAOImpl;
import entity.AccountEntity;
import org.hibernate.Transaction;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class AccountTest extends GenericTest {
    private AccountDAOImpl accountDAO;

    @BeforeClass(dependsOnMethods = "setUpSession")
    protected void setUpDAO() {
        accountDAO = new AccountDAOImpl();
        accountDAO.setSession(testSession);
    }

    @Test(priority = 0, groups = "account")
    public void getAccount() {
        AccountEntity account = accountDAO.getById((long) 1);
        Assert.assertNotNull(account);
        Assert.assertEquals((long) account.getUserId(), 1);
        Assert.assertEquals(account.getUserName(), "Иванов Иван Иванович");
        Assert.assertNull(account.getHomeAddress());
        Assert.assertNull(account.getPhoneNumber());
        Assert.assertEquals(account.geteMail(), "admin@namelessbookstore.com");
        Assert.assertEquals(account.getPasswordHash(), "t7z6kgTsq+VdcsxNGWb4o0M2e/IwpOc3oczibsS6+Aw=");
        Assert.assertTrue(account.isAdmin());

        Assert.assertNull(accountDAO.getById((long) 12));

        List<AccountEntity> allAccounts = accountDAO.getAll();
        Assert.assertFalse(allAccounts.contains(new AccountEntity()));
        List<AccountEntity> testAccounts = Arrays.asList(
                accountDAO.getById((long) 1),
                accountDAO.getById((long) 2),
                accountDAO.getById((long) 3),
                accountDAO.getById((long) 4),
                accountDAO.getById((long) 5)
        );
        Assert.assertEquals(testAccounts.size(), allAccounts.size());
        for (AccountEntity a : allAccounts) {
            Assert.assertTrue(testAccounts.contains(a));
        }

        Assert.assertEquals(account, accountDAO.getByEMail("admin@namelessbookstore.com"));
        Assert.assertNull(accountDAO.getByEMail("test@test.test"));
    }

    @Test(priority = 1, dependsOnMethods = "getAccount", groups = "account")
    public void addAccount() {
        Transaction tx = testSession.beginTransaction();

        String eMail = "test@test.test";
        Assert.assertNull(accountDAO.getByEMail(eMail));
        AccountEntity account = new AccountEntity();
        account.setUserName("Test");
        account.setHomeAddress("Test");
        account.setPhoneNumber("Test");
        account.seteMail(eMail);
        account.setPasswordHash("TEST");
        account.setAdmin(false);
        accountDAO.save(account);

        tx.commit();

        Assert.assertNotNull(accountDAO.getByEMail(eMail));
    }

    @Test(priority = 2, dependsOnMethods = "addAccount", groups = "account")
    public void updateAccount() {
        Transaction tx = testSession.beginTransaction();

        String eMail = "test@test.test";
        AccountEntity account = accountDAO.getByEMail(eMail);
        Assert.assertNotNull(account);
        Long accountId = account.getUserId();
        account.setUserName("Update Test");
        accountDAO.update(account);

        tx.commit();

        account = accountDAO.getByEMail(eMail);
        Assert.assertNotNull(account);
        Assert.assertEquals(account.getUserName(), "Update Test");
        Assert.assertEquals((long) account.getUserId(), (long) accountId);
        Assert.assertEquals(account.getHomeAddress(), "Test");
        Assert.assertEquals(account.getPhoneNumber(), "Test");
        Assert.assertEquals(account.geteMail(), eMail);
        Assert.assertEquals(account.getPasswordHash(), "TEST");
        Assert.assertFalse(account.isAdmin());
    }

    @Test(priority = 3, dependsOnMethods = "updateAccount", groups = "account")
    public void deleteAccount() {
        Transaction tx = testSession.beginTransaction();

        String eMail = "test@test.test";
        AccountEntity account = accountDAO.getByEMail(eMail);
        Assert.assertNotNull(account);
        accountDAO.delete(account);

        tx.commit();

        Assert.assertNull(accountDAO.getByEMail(eMail));
    }
}
