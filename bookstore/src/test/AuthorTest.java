package test;

import daoimpl.AuthorDAOImpl;
import entity.AuthorEntity;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class AuthorTest extends GenericTest {
    private AuthorDAOImpl authorDAO;

    @BeforeClass(dependsOnMethods = "setUpSession")
    protected void setUpDAO() {
        authorDAO = new AuthorDAOImpl();
        authorDAO.setSession(testSession);
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
    }
}
