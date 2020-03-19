package test;

import daoimpl.AuthorDAOImpl;
import daoimpl.BookAuthorDAOImpl;
import daoimpl.BookDAOImpl;
import entity.AuthorEntity;
import entity.BookEntity;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class AuthorTest extends GenericTest {
    private AuthorDAOImpl authorDAO;
    private BookAuthorDAOImpl bookAuthorDAO;
    private BookDAOImpl bookDAO;

    @BeforeClass(dependsOnMethods = "setUpSession")
    protected void setUpDAO() {
        authorDAO = new AuthorDAOImpl();
        bookAuthorDAO = new BookAuthorDAOImpl();
        bookDAO = new BookDAOImpl();
        authorDAO.setSession(testSession);
        bookAuthorDAO.setSession(testSession);
        bookDAO.setSession(testSession);
    }

    @Test(priority = 0, groups = "author")
    public void getAuthor() {
        Assert.assertTrue(authorDAO.getByName("Test").isEmpty());
        Assert.assertEquals(authorDAO.getByName("").size(), 6);
        List<AuthorEntity> authors = authorDAO.getByName("Пушкин");
        Assert.assertEquals(authors.size(), 1);
        Assert.assertEquals(authors.get(0), authorDAO.getById((long) 1));
        Assert.assertEquals(authors.get(0),
                authorDAO.getByExactName("Пушкин Александр Сергеевич"));
        Assert.assertNull(authorDAO.getByExactName("Толстой Лев Николаевич"));

        Assert.assertNull(authorDAO.getByExactName("Test"));
        Assert.assertNull(authorDAO.getByExactName(""));
        Assert.assertEquals(authorDAO.getByExactName("Пушкин Александр Сергеевич"),
                authorDAO.getById((long) 1));

        authors = bookAuthorDAO.getAuthorsByBook(bookDAO.getById((long) 1));
        Assert.assertEquals(authors.size(), 1);
        Assert.assertEquals(authors.get(0), authorDAO.getById((long) 1));
    }
}
