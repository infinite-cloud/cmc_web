package test;

import daoimpl.AuthorDAOImpl;
import daoimpl.BookAuthorDAOImpl;
import daoimpl.BookDAOImpl;
import entity.BookEntity;
import org.hibernate.Transaction;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

public class BookTest extends GenericTest {
    private BookDAOImpl bookDAO;
    private AuthorDAOImpl authorDAO;
    private BookAuthorDAOImpl bookAuthorDAO;

    @BeforeClass(dependsOnMethods = "setUpSession")
    protected void setUpDAO() {
        bookDAO = new BookDAOImpl();
        authorDAO = new AuthorDAOImpl();
        bookAuthorDAO = new BookAuthorDAOImpl();
        bookDAO.setSession(testSession);
        authorDAO.setSession(testSession);
        bookAuthorDAO.setSession(testSession);
    }

    @Test(priority = 0, groups = "book")
    public void getBook() {
        List<BookEntity> books = bookDAO.getByPublicationDate(
                Date.valueOf("2018-12-01"), Date.valueOf("2019-02-01"));
        Assert.assertEquals(books.size(), 1);
        Assert.assertEquals(books.get(0), bookDAO.getById((long) 1));
        books = bookDAO.getByPublicationDate(
                Date.valueOf("2019-02-01"), Date.valueOf("2018-12-01"));
        Assert.assertTrue(books.isEmpty());

        Assert.assertTrue(bookDAO.getByName("Test").isEmpty());
        books = bookDAO.getByName("Идиот");
        Assert.assertEquals(books.size(), 1);
        Assert.assertEquals(books.get(0), bookDAO.getById((long) 2));

        Assert.assertTrue(bookDAO.getByPageCount(0, -1).isEmpty());
        books = bookDAO.getByPageCount(700, 800);
        Assert.assertEquals(books.size(), 1);
        Assert.assertEquals(books.get(0), bookDAO.getById((long) 4));

        Assert.assertTrue(bookDAO.getByPrice(0., -1.).isEmpty());
        books = bookDAO.getByPrice(100., 200.);
        Assert.assertEquals(books.size(), 1);
        Assert.assertEquals(books.get(0), bookDAO.getById((long) 5));

        Assert.assertEquals(bookDAO.getByAvailability(false).size(), 1);

        Assert.assertTrue(bookDAO.getByPublisherId((long) 12).isEmpty());
        books = bookDAO.getByPublisherId((long) 1);
        Assert.assertEquals(books.size(), 1);
        Assert.assertEquals(books.get(0), bookDAO.getById((long) 5));

        Assert.assertTrue(bookDAO.getByGenreId((long) 12).isEmpty());
        books = bookDAO.getByGenreId((long) 1);
        Assert.assertEquals(books.size(), 1);
        Assert.assertEquals(books.get(0), bookDAO.getById((long) 3));

        Assert.assertTrue(bookDAO.getByCoverTypeId((long) 12).isEmpty());
        books = bookDAO.getByCoverTypeId((long) 1);
        Assert.assertEquals(books.size(), 1);
        Assert.assertEquals(books.get(0), bookDAO.getById((long) 4));

        books = bookDAO.getByParameters("",
                Date.valueOf("1980-05-05"), Date.valueOf("1990-06-06"),
                -100, -200,
                -10.1, -12.2,
                true,
                (long) -1,
                (long) -1,
                (long) -1);
        Assert.assertTrue(books.isEmpty());

        books = bookDAO.getByParameters("ИДИОТ",
                Date.valueOf("2000-01-01"), Date.valueOf("2020-01-01"),
                0, 1000,
                0.0, 2000.0,
                true,
                (long) 4,
                (long) 3,
                (long) 3);
        Assert.assertEquals(books.size(), 1);
        Assert.assertEquals(books.get(0), bookDAO.getById((long) 2));

        books = bookDAO.getByParameters("",
                null, null,
                null,null,
                null,null,
                null,
                null,
                null,
                null);
        Assert.assertEquals(books.size(), 5);

        books = bookDAO.getByParameters("",
                null, null,
                null,null,
                null,null,
                false,
                null,
                null,
                null);
        Assert.assertEquals(books.size(), 1);

        books = bookDAO.getByParameters("Онегин",
                Date.valueOf("2010-01-01"), null,
                null,null,
                null,5000.0,
                null,
                null,
                (long) 2,
                null);
        Assert.assertEquals(books.size(), 1);
        Assert.assertEquals(books.get(0), bookDAO.getById((long) 1));

        books = bookDAO.getByParameters(null,
                null, null,
                null,null,
                null,null,
                null,
                null,
                null,
                null);
        Assert.assertEquals(books.size(), 5);
    }

    @Test(priority = 1, groups = "book", dependsOnGroups = "account")
    public void addBook() {
        Transaction tx = testSession.beginTransaction();

        List<String> authors = Arrays.asList("Пушкин Александр Сергеевич", "Test");
        BookEntity book = new BookEntity();
        book.setBookName("Test");
        book.setPublicationDate(Date.valueOf("2020-01-01"));
        book.setPageCount(100);
        book.setBookPrice(100.);
        book.setAvailableCount(1);
        book.setDescription("TEST");

        bookDAO.save(book, authors, (long) 1, (long) 1, (long) 1, "");

        tx.commit();

        List<BookEntity> testBooks = bookDAO.getByName("Test");
        Assert.assertEquals(testBooks.size(), 1);
        Long bookId = testBooks.get(0).getBookId();
        Assert.assertEquals(testBooks.get(0), book);

        tx = testSession.beginTransaction();

        bookDAO.delete(book);

        tx.commit();

        testSession.clear();

        Long authorId = authorDAO.getByExactName("Test").getAuthorId();

        Assert.assertEquals(bookDAO.getByName("Test").size(), 0);
        Assert.assertNull(bookAuthorDAO.getByCompositeId(bookId, authorId));
        Assert.assertNull(bookAuthorDAO.getByCompositeId(bookId, (long) 1));

        tx = testSession.beginTransaction();

        authorDAO.delete(authorDAO.getByExactName("Test"));

        tx.commit();
    }
}
