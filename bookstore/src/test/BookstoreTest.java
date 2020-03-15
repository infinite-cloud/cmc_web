package test;

import daoimpl.*;
import entity.AccountEntity;
import entity.AuthorEntity;
import entity.BookAuthorId;
import entity.BookEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.Arrays;
import java.sql.Date;
import java.util.List;

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

    @Test(priority = 0, groups = "account")
    public void getAccount() {
        AccountDAOImpl accountDAO = new AccountDAOImpl();
        accountDAO.setSession(testSession);

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
        AccountDAOImpl accountDAO = new AccountDAOImpl();
        accountDAO.setSession(testSession);

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
        AccountDAOImpl accountDAO = new AccountDAOImpl();
        accountDAO.setSession(testSession);

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
        AccountDAOImpl accountDAO = new AccountDAOImpl();
        accountDAO.setSession(testSession);

        Transaction tx = testSession.beginTransaction();

        String eMail = "test@test.test";
        AccountEntity account = accountDAO.getByEMail(eMail);
        Assert.assertNotNull(account);
        accountDAO.delete(account);

        tx.commit();

        Assert.assertNull(accountDAO.getByEMail(eMail));
    }

    @Test(priority = 0, groups = "book")
    public void getBook() {
        BookDAOImpl bookDAO = new BookDAOImpl();
        bookDAO.setSession(testSession);

        List<BookEntity> books = bookDAO.getByPublicationDate(
                Date.valueOf("2018-12-01"), Date.valueOf("2019-02-01"));
        Assert.assertEquals(books.size(), 1);
        Assert.assertEquals(books.iterator().next(), bookDAO.getById((long) 1));
        books = bookDAO.getByPublicationDate(
                Date.valueOf("2019-02-01"), Date.valueOf("2018-12-01"));
        Assert.assertTrue(books.isEmpty());

        Assert.assertTrue(bookDAO.getByName("Test").isEmpty());
        books = bookDAO.getByName("Идиот");
        Assert.assertEquals(books.size(), 1);
        Assert.assertEquals(books.iterator().next(), bookDAO.getById((long) 2));

        Assert.assertTrue(bookDAO.getByPageCount(0, -1).isEmpty());
        books = bookDAO.getByPageCount(700, 800);
        Assert.assertEquals(books.size(), 1);
        Assert.assertEquals(books.iterator().next(), bookDAO.getById((long) 4));

        Assert.assertTrue(bookDAO.getByPrice(0., -1.).isEmpty());
        books = bookDAO.getByPrice(100., 200.);
        Assert.assertEquals(books.size(), 1);
        Assert.assertEquals(books.iterator().next(), bookDAO.getById((long) 5));

        Assert.assertEquals(bookDAO.getByAvailability(false).size(), 0);

        Assert.assertTrue(bookDAO.getByPublisherId((long) 12).isEmpty());
        books = bookDAO.getByPublisherId((long) 1);
        Assert.assertEquals(books.size(), 1);
        Assert.assertEquals(books.iterator().next(), bookDAO.getById((long) 5));

        Assert.assertTrue(bookDAO.getByGenreId((long) 12).isEmpty());
        books = bookDAO.getByGenreId((long) 1);
        Assert.assertEquals(books.size(), 1);
        Assert.assertEquals(books.iterator().next(), bookDAO.getById((long) 3));

        Assert.assertTrue(bookDAO.getByCoverTypeId((long) 12).isEmpty());
        books = bookDAO.getByCoverTypeId((long) 1);
        Assert.assertEquals(books.size(), 1);
        Assert.assertEquals(books.iterator().next(), bookDAO.getById((long) 4));
    }

    @Test(priority = 1, groups = "book", dependsOnGroups = "account")
    public void addBook() {
        BookDAOImpl bookDAO = new BookDAOImpl();
        bookDAO.setSession(testSession);
        AuthorDAOImpl authorDAO = new AuthorDAOImpl();
        authorDAO.setSession(testSession);

        Transaction tx = testSession.beginTransaction();

        List<String> authors = Arrays.asList("Пушкин Александр Сергеевич", "Test");
        BookEntity book = new BookEntity();
        book.setBookName("Test");
        book.setPublicationDate(Date.valueOf("2020-01-01"));
        book.setPageCount(100);
        book.setBookPrice(100.);
        book.setAvailableCount(1);
        book.setDescription("TEST");

        bookDAO.save(book, authors, (long) 1, (long) 1, (long) 1);

        tx.commit();

        List<BookEntity> testBooks = bookDAO.getByName("Test");
        Assert.assertEquals(testBooks.size(), 1);
        Long bookId = testBooks.iterator().next().getBookId();
        Assert.assertEquals(testBooks.iterator().next(), book);

        tx = testSession.beginTransaction();

        bookDAO.delete(book);

        tx.commit();

        BookAuthorDAOImpl bookAuthorDAO = new BookAuthorDAOImpl();
        bookAuthorDAO.setSession(testSession);

        Long authorId = authorDAO.getByExactName("Test").getAuthorId();

        Assert.assertEquals(bookDAO.getByName("Test").size(), 0);
        Assert.assertNull(bookAuthorDAO.getByCompositeId(bookId, authorId));
        Assert.assertNull(bookAuthorDAO.getByCompositeId(bookId, (long) 1));

        tx = testSession.beginTransaction();


        authorDAO.delete(authorDAO.getByExactName("Test"));

        tx.commit();
    }

    @Test(priority = 0, groups = "author")
    public void getAuthor() {
        AuthorDAOImpl authorDAO = new AuthorDAOImpl();
        authorDAO.setSession(testSession);

        Assert.assertTrue(authorDAO.getByName("Test").isEmpty());
        Assert.assertEquals(authorDAO.getByName("").size(), 6);
        List<AuthorEntity> authors = authorDAO.getByName("Пушкин");
        Assert.assertEquals(authors.size(), 1);
        Assert.assertEquals(authors.iterator().next(), authorDAO.getById((long) 1));
        Assert.assertEquals(authors.iterator().next(), authorDAO.getByExactName("Пушкин Александр Сергеевич"));
        Assert.assertNull(authorDAO.getByExactName("Толстой Лев Николаевич"));
    }

    @AfterSuite
    public void shutDownSession() {
        testSession.close();
        testSessionFactory.close();
    }
}
