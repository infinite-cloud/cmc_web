package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;

public class AdminTest extends GenericTest {
    @BeforeClass
    private void login() {
        webDriver.get(appURL + "login");
        WebElement webElement = webDriver.findElement(By.xpath("/html/body/form/table/tbody/tr[1]/td[2]/label/input"));
        webElement.sendKeys("admin@namelessbookstore.com");
        webElement = webDriver.findElement(By.xpath("/html/body/form/table/tbody/tr[2]/td[2]/label/input"));
        webElement.sendKeys("password0");
        webElement = webDriver.findElement(By.xpath("/html/body/form/table/tbody/tr[3]/td[2]/label/input"));
        webElement.click();
    }

    @Test(priority = 0, groups = "admin")
    public void editCatalog() {
        webDriver.get(appURL);

        Assert.assertEquals(webDriver.findElements(By.xpath("//*[@href=\"/bookstore/orderList\"]")).size(), 1);
        Assert.assertEquals(webDriver.findElements(By.xpath("/html/body/div[2]/select")).size(), 1);

        Select select = new Select(webDriver.findElement(By.xpath("/html/body/div[2]/select")));
        select.selectByIndex(2);
        Assert.assertEquals(webDriver.getCurrentUrl(), appURL + "addItem?author");

        WebElement webElement = webDriver.findElement(By.xpath(
                "//*[@id=\"itemForm\"]/table/tbody/tr[2]/td[2]/label/input"));
        webElement.click();
        webElement = webDriver.findElement(By.xpath("//*[@id=\"value.errors\"]"));
        Assert.assertEquals(webElement.getText(), "Поле не должно быть пустым");

        webElement = webDriver.findElement(By.xpath("//*[@id=\"value\"]"));
        webElement.sendKeys("test");
        webElement = webDriver.findElement(By.xpath(
                "//*[@id=\"itemForm\"]/table/tbody/tr[2]/td[2]/label/input"));
        webElement.click();
        Assert.assertEquals(webDriver.getCurrentUrl(), appURL + "?itemAdded=true");
        webElement = webDriver.findElement(By.xpath("/html/body/div[3]"));
        Assert.assertEquals(webElement.getText(), "Элемент добавлен в каталог");

        select = new Select(webDriver.findElement(By.xpath("/html/body/div[2]/select")));
        select.selectByIndex(1);
        Assert.assertEquals(webDriver.getCurrentUrl(), appURL + "addBook");

        webElement = webDriver.findElement(By.xpath("//*[@id=\"bookForm\"]/table/tbody/tr[12]/td[2]/label/input"));
        webElement.click();
        webElement = webDriver.findElement(By.xpath("//*[@id=\"bookName.errors\"]"));
        Assert.assertEquals(webElement.getText(), "Поле не должно быть пустым");
        webElement = webDriver.findElement(By.xpath("//*[@id=\"description.errors\"]"));
        Assert.assertEquals(webElement.getText(), "Поле не должно быть пустым");
        webElement = webDriver.findElement(By.xpath("//*[@id=\"genreId.errors\"]"));
        Assert.assertEquals(webElement.getText(), "Поле не должно быть пустым");
        webElement = webDriver.findElement(By.xpath("//*[@id=\"coverTypeId.errors\"]"));
        Assert.assertEquals(webElement.getText(), "Поле не должно быть пустым");
        webElement = webDriver.findElement(By.xpath("//*[@id=\"publisherId.errors\"]"));
        Assert.assertEquals(webElement.getText(), "Поле не должно быть пустым");
        webElement = webDriver.findElement(By.xpath("//*[@id=\"bookAuthors.errors\"]"));
        Assert.assertEquals(webElement.getText(), "Книга должна иметь хотя бы одного автора");
        webElement = webDriver.findElement(By.xpath("//*[@id=\"pageCount.errors\"]"));
        Assert.assertEquals(webElement.getText(), "Поле не должно быть пустым");
        webElement = webDriver.findElement(By.xpath("//*[@id=\"publicationDate.errors\"]"));
        Assert.assertEquals(webElement.getText(), "Поле не должно быть пустым");
        webElement = webDriver.findElement(By.xpath("//*[@id=\"availableCount.errors\"]"));
        Assert.assertEquals(webElement.getText(), "Поле не должно быть пустым");
        webElement = webDriver.findElement(By.xpath("//*[@id=\"image.errors\"]"));
        Assert.assertEquals(webElement.getText(), "Поле не должно быть пустым");
        webElement = webDriver.findElement(By.xpath("//*[@id=\"bookPrice.errors\"]"));
        Assert.assertEquals(webElement.getText(), "Поле не должно быть пустым");

        webElement = webDriver.findElement(By.xpath("//*[@id=\"bookName\"]"));
        webElement.sendKeys("test");
        webElement = webDriver.findElement(By.xpath("//*[@id=\"description\"]"));
        webElement.sendKeys("test");
        select = new Select(webDriver.findElement(By.xpath("//*[@id=\"genre\"]")));
        select.selectByIndex(1);
        select = new Select(webDriver.findElement(By.xpath("//*[@id=\"cover\"]")));
        select.selectByIndex(1);
        select = new Select(webDriver.findElement(By.xpath("//*[@id=\"publisher\"]")));
        select.selectByIndex(1);
        select = new Select(webDriver.findElement(By.xpath("//*[@id=\"selector0\"]")));
        select.selectByIndex(7);
        Assert.assertEquals(webDriver.findElements(By.xpath("//*[@id=\"selector1\"]")).size(), 0);
        webElement = webDriver.findElement(By.xpath("//*[@id=\"addButton\"]"));
        webElement.click();
        Assert.assertEquals(webDriver.findElements(By.xpath("//*[@id=\"selector1\"]")).size(), 1);
        select = new Select(webDriver.findElement(By.xpath("//*[@id=\"selector1\"]")));
        select.selectByIndex(1);
        webElement = webDriver.findElement(By.xpath("//*[@id=\"addButton\"]"));
        webElement.click();
        select = new Select(webDriver.findElement(By.xpath("//*[@id=\"selector1\"]")));
        select.selectByIndex(1);
        webElement = webDriver.findElement(By.xpath("//*[@id=\"addButton\"]"));
        webElement.click();
        Assert.assertEquals(webDriver.findElements(By.xpath("//*[@id=\"selector3\"]")).size(), 1);
        webElement = webDriver.findElement(By.xpath("//*[@id=\"removeButton\"]"));
        webElement.click();
        Assert.assertEquals(webDriver.findElements(By.xpath("//*[@id=\"selector3\"]")).size(), 0);
        webElement = webDriver.findElement(By.xpath("//*[@id=\"pageCount\"]"));
        webElement.sendKeys("-1");
        webElement = webDriver.findElement(By.xpath("//*[@id=\"publicationDate\"]"));
        webElement.sendKeys("010102025");
        webElement = webDriver.findElement(By.xpath("//*[@id=\"availableCount\"]"));
        webElement.sendKeys("-1");
        webElement = webDriver.findElement(By.xpath("//*[@id=\"bookPrice\"]"));
        webElement.sendKeys("-1");

        webElement = webDriver.findElement(By.xpath("//*[@id=\"bookForm\"]/table/tbody/tr[12]/td[2]/label/input"));
        webElement.click();

        webElement = webDriver.findElement(By.xpath("//*[@id=\"pageCount.errors\"]"));
        Assert.assertEquals(webElement.getText(), "Значение должно быть положительным");
        webElement = webDriver.findElement(By.xpath("//*[@id=\"publicationDate.errors\"]"));
        Assert.assertEquals(webElement.getText(), "Некорректная дата");
        webElement = webDriver.findElement(By.xpath("//*[@id=\"availableCount.errors\"]"));
        Assert.assertEquals(webElement.getText(), "Значение должно быть неотрицательным");
        webElement = webDriver.findElement(By.xpath("//*[@id=\"bookPrice.errors\"]"));
        Assert.assertEquals(webElement.getText(), "Значение должно быть положительным");

        webElement = webDriver.findElement(By.xpath("//*[@id=\"pageCount\"]"));
        webElement.clear();
        webElement.sendKeys("10");
        webElement = webDriver.findElement(By.xpath("//*[@id=\"publicationDate\"]"));
        webElement.clear();
        webElement.sendKeys("010102020");
        webElement = webDriver.findElement(By.xpath("//*[@id=\"availableCount\"]"));
        webElement.clear();
        webElement.sendKeys("10");
        webElement = webDriver.findElement(By.xpath("//*[@id=\"image\"]"));
        System.out.println(System.getProperty("user.dir") + File.separator + "cringe.jpg");
        webElement.sendKeys(System.getProperty("user.dir") + File.separator + "cringe.jpg");
        webElement = webDriver.findElement(By.xpath("//*[@id=\"bookPrice\"]"));
        webElement.clear();
        webElement.sendKeys("10");

        webElement = webDriver.findElement(By.xpath("//*[@id=\"bookForm\"]/table/tbody/tr[12]/td[2]/label/input"));
        webElement.click();

        Assert.assertEquals(webDriver.getCurrentUrl(), appURL + "?bookAdded=true");
        webElement = webDriver.findElement(By.xpath("/html/body/div[3]"));
        Assert.assertEquals(webElement.getText(), "Книга добавлена в каталог");

        Assert.assertEquals(webDriver.findElements(By.xpath("//*[@href=\"/bookstore/book?id=6\"]")).size(), 1);
        webElement = webDriver.findElement(By.xpath("//*[@href=\"/bookstore/book?id=6\"]"));
        webElement.click();
        Assert.assertEquals(webDriver.findElements(By.xpath("//*[@href=\"/bookstore/deleteBook/6\"]")).size(), 1);
        Assert.assertEquals(webDriver.findElements(By.xpath("//*[@href=\"/bookstore/editBook?id=6\"]")).size(), 1);
        webElement = webDriver.findElement(By.xpath("/html/body/table/tbody/tr[5]/td"));
        Assert.assertEquals(webElement.getText(), "Пушкин Александр Сергеевич\ntest");

        webElement = webDriver.findElement(By.xpath("/html/body/div[5]/a[2]"));
        webElement.click();
        webDriver.switchTo().alert().dismiss();
        Assert.assertEquals(webDriver.getCurrentUrl(), appURL + "book?id=6");
        webElement = webDriver.findElement(By.xpath("/html/body/div[5]/a[2]"));
        webElement.click();
        webDriver.switchTo().alert().accept();
        Assert.assertEquals(webDriver.getCurrentUrl(), appURL + "?bookDeleted=true");
        webElement = webDriver.findElement(By.xpath("/html/body/div[3]"));
        Assert.assertEquals(webElement.getText(), "Книга удалена");

        select = new Select(webDriver.findElement(By.xpath("/html/body/div[2]/select")));
        select.selectByIndex(6);
        webElement = webDriver.findElement(By.xpath("//*[@href=\"/bookstore/removeItem/author/7\"]"));
        webElement.click();
        webDriver.switchTo().alert().dismiss();
        Assert.assertEquals(webDriver.findElements(By.xpath(
                "//*[@href=\"/bookstore/removeItem/author/7\"]")).size(), 1);
        webElement = webDriver.findElement(By.xpath("//*[@href=\"/bookstore/removeItem/author/7\"]"));
        webElement.click();
        webDriver.switchTo().alert().accept();
        Assert.assertEquals(webDriver.findElements(By.xpath(
                "//*[@href=\"/bookstore/removeItem/author/7\"]")).size(), 0);
    }

    @Test(priority = 1, groups = "admin")
    public void editBook() {

    }

    @Test(priority = 2, groups = "admin")
    public void viewOrders() {

    }
}
