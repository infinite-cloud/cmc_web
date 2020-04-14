package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class GuestTest extends GenericTest {
    @Test(priority = 0, groups = "guest")
    public void browse() {
        webDriver.get(appURL);

        WebElement webElement = webDriver.findElement(By.xpath("/html/body/div[1]/div[1]/a"));
        webElement.click();
        Assert.assertEquals(webDriver.getCurrentUrl(), appURL);
        webElement = webDriver.findElement(By.xpath("/html/body/div[2]/a"));
        webElement.click();
        Assert.assertEquals(webDriver.getCurrentUrl(), appURL);

        Assert.assertEquals(webDriver.findElements(By.xpath("//*[@href=\"/bookstore/account\"]")).size(), 0);
        Assert.assertEquals(webDriver.findElements(By.xpath("//*[@href=\"/bookstore/logout\"]")).size(), 0);
        Assert.assertEquals(webDriver.findElements(By.xpath("//*[@href=\"/bookstore/cart\"]")).size(), 0);
        Assert.assertEquals(webDriver.findElements(By.xpath("//*[@href=\"/bookstore/orderList\"]")).size(), 0);
        Assert.assertEquals(webDriver.findElements(By.xpath("/html/body/div[2]/select")).size(), 0);

        webElement = webDriver.findElement(By.xpath("//*[@id=\"toggleText\"]"));
        Assert.assertEquals(webElement.getCssValue("display"), "none");
        webElement = webDriver.findElement(By.xpath("//*[@id=\"displayText\"]"));
        webElement.click();
        webElement = webDriver.findElement(By.xpath("//*[@id=\"toggleText\"]"));
        Assert.assertEquals(webElement.getCssValue("display"), "block");

        webElement = webDriver.findElement(By.xpath("//*[@id=\"filterForm\"]/table/tbody/tr[10]/td[2]/input"));
        webElement.click();
        List<WebElement> rows = webDriver.findElements(By.xpath("/html/body/table/tbody/tr"));
        Assert.assertEquals(rows.size(), 6);
        webElement = webDriver.findElement(By.xpath("//*[@id=\"toggleText\"]"));
        Assert.assertEquals(webElement.getCssValue("display"), "none");

        webElement = webDriver.findElement(By.xpath("//*[@id=\"displayText\"]"));
        webElement.click();
        webElement = webDriver.findElement(By.xpath("//*[@id=\"name\"]"));
        webElement.sendKeys("уравнения");
        Select dropdown = new Select(webDriver.findElement(By.xpath("//*[@id=\"genre\"]")));
        dropdown.selectByIndex(4);
        dropdown = new Select(webDriver.findElement(By.xpath("//*[@id=\"cover\"]")));
        dropdown.selectByIndex(1);
        dropdown = new Select(webDriver.findElement(By.xpath("//*[@id=\"publisher\"]")));
        dropdown.selectByIndex(2);
        webElement = webDriver.findElement(By.xpath("//*[@id=\"textBox0\"]"));
        webElement.sendKeys("Тихонов");
        webElement = webDriver.findElement(By.xpath("//*[@id=\"addButton\"]"));
        webElement.click();
        webElement = webDriver.findElement(By.xpath("//*[@id=\"textBox1\"]"));
        Assert.assertNotNull(webElement);
        webElement.sendKeys("Пушкин");
        webElement = webDriver.findElement(By.xpath("//*[@id=\"minPrice\"]"));
        webElement.sendKeys("300");
        webElement = webDriver.findElement(By.xpath("//*[@id=\"maxPrice\"]"));
        webElement.sendKeys("400");
        webElement = webDriver.findElement(By.xpath("//*[@id=\"minPages\"]"));
        webElement.sendKeys("700");
        webElement = webDriver.findElement(By.xpath("//*[@id=\"maxPages\"]"));
        webElement.sendKeys("800");
        webElement = webDriver.findElement(By.xpath("//*[@id=\"minDate\"]"));
        webElement.sendKeys("01012013");
        webElement = webDriver.findElement(By.xpath("//*[@id=\"maxDate\"]"));
        webElement.sendKeys("01012015");
        webElement = webDriver.findElement(By.xpath("//*[@id=\"availability1\"]"));
        webElement.click();
        webElement = webDriver.findElement(By.xpath("//*[@id=\"filterForm\"]/table/tbody/tr[10]/td[2]/input"));
        webElement.click();
        rows = webDriver.findElements(By.xpath("/html/body/table/tbody/tr"));
        Assert.assertEquals(rows.size(), 2);

        webElement = webDriver.findElement(By.xpath("//*[@id=\"displayText\"]"));
        webElement.click();
        webElement = webDriver.findElement(By.xpath("//*[@id=\"removeButton\"]"));
        webElement.click();
        Assert.assertEquals(webDriver.findElements(By.xpath("//*[@id=\"textBox1\"]")).size(), 0);

        webElement = webDriver.findElement(By.xpath("//*[@id=\"reset\"]"));
        webElement.click();
        rows = webDriver.findElements(By.xpath("/html/body/table/tbody/tr"));
        Assert.assertEquals(rows.size(), 6);

        webElement = webDriver.findElement(By.xpath("/html/body/table/tbody/tr[2]/td[1]/a"));
        webElement.click();
        Assert.assertEquals(webDriver.getCurrentUrl(), appURL + "book?id=1");
        Assert.assertEquals(webDriver.findElements(By.xpath("//*[@href=\"/bookstore/addToCart/1\"]")).size(), 0);
        Assert.assertEquals(webDriver.findElements(By.xpath("//*[@href=\"/bookstore/deleteBook/1\"]")).size(), 0);
        Assert.assertEquals(webDriver.findElements(By.xpath("//*[@href=\"/bookstore/editBook?id=1\"]")).size(), 0);
    }

    @Test(priority = 1, groups = "guest")
    public void register() {
        webDriver.get(appURL);

        WebElement webElement = webDriver.findElement(By.xpath("/html/body/div[1]/div[2]/a[2]"));
        webElement.click();
        Assert.assertEquals(webDriver.getCurrentUrl(), appURL + "register");

        webElement = webDriver.findElement(By.xpath("/html/body/form/table/tbody/tr[7]/td[2]/label/input"));
        webElement.submit();
        webElement = webDriver.findElement(By.xpath("//*[@id=\"eMail.errors\"]"));
        Assert.assertEquals(webElement.getText(), "Поле не должно быть пустым");
        webElement = webDriver.findElement(By.xpath("//*[@id=\"userName.errors\"]"));
        Assert.assertEquals(webElement.getText(), "Поле не должно быть пустым");
        webElement = webDriver.findElement(By.xpath("//*[@id=\"password.errors\"]"));
        Assert.assertEquals(webElement.getText(), "Поле не должно быть пустым");
        webElement = webDriver.findElement(By.xpath("//*[@id=\"repeatedPassword.errors\"]"));
        Assert.assertEquals(webElement.getText(), "Поле не должно быть пустым");

        webElement = webDriver.findElement(By.xpath("//*[@id=\"eMail\"]"));
        webElement.clear();
        webElement.sendKeys("test@a.a");
        webElement = webDriver.findElement(By.xpath("/html/body/form/table/tbody/tr[7]/td[2]/label/input"));
        webElement.click();
        webElement = webDriver.findElement(By.xpath("//*[@id=\"eMail.errors\"]"));
        Assert.assertEquals(webElement.getText(), "Некорректный адрес электронной почты");

        webElement = webDriver.findElement(By.xpath("//*[@id=\"eMail\"]"));
        webElement.clear();
        webElement.sendKeys("admin@namelessbookstore.com");
        webElement = webDriver.findElement(By.xpath("/html/body/form/table/tbody/tr[7]/td[2]/label/input"));
        webElement.click();
        webElement = webDriver.findElement(By.xpath("//*[@id=\"eMail.errors\"]"));
        Assert.assertEquals(webElement.getText(), "Учётная запись уже существует");

        webElement = webDriver.findElement(By.xpath("//*[@id=\"phoneNumber\"]"));
        webElement.sendKeys("test");
        webElement = webDriver.findElement(By.xpath("/html/body/form/table/tbody/tr[7]/td[2]/label/input"));
        webElement.click();
        webElement = webDriver.findElement(By.xpath("//*[@id=\"phoneNumber.errors\"]"));
        Assert.assertEquals(webElement.getText(), "Ввод должен иметь формат +71234567890");

        webElement = webDriver.findElement(By.xpath("//*[@id=\"password\"]"));
        webElement.sendKeys("test");
        webElement = webDriver.findElement(By.xpath("/html/body/form/table/tbody/tr[7]/td[2]/label/input"));
        webElement.click();
        webElement = webDriver.findElement(By.xpath("//*[@id=\"repeatedPassword.errors\"]"));
        Assert.assertEquals(webElement.getText(), "Поле не должно быть пустым\nПароли не совпадают");

        webElement = webDriver.findElement(By.xpath("//*[@id=\"eMail\"]"));
        webElement.clear();
        webElement.sendKeys("test@test.com");
        webElement = webDriver.findElement(By.xpath("//*[@id=\"userName\"]"));
        webElement.sendKeys("test");
        webElement = webDriver.findElement(By.xpath("//*[@id=\"userForm\"]/table/tbody/tr[3]/td[1]/label/textarea"));
        webElement.sendKeys("test");
        webElement = webDriver.findElement(By.xpath("//*[@id=\"phoneNumber\"]"));
        webElement.clear();
        webElement.sendKeys("+74951234567");
        webElement = webDriver.findElement(By.xpath("//*[@id=\"repeatedPassword\"]"));
        webElement.sendKeys("test");
        webElement = webDriver.findElement(By.xpath("/html/body/form/table/tbody/tr[7]/td[2]/label/input"));
        webElement.click();
        webElement = webDriver.findElement(By.xpath("/html/body/div[4]"));
        Assert.assertEquals(webElement.getText(), "Регистрация успешна");
    }

    @Test(priority = 2, groups = "guest")
    public void login() {
        webDriver.get(appURL);

        WebElement webElement = webDriver.findElement(By.xpath("/html/body/div[1]/div[2]/a[1]"));
        webElement.click();
        Assert.assertEquals(webDriver.getCurrentUrl(), appURL + "login");

        webElement = webDriver.findElement(By.xpath("/html/body/form/table/tbody/tr[3]/td[2]/label/input"));
        webElement.click();
        webElement = webDriver.findElement(By.xpath("/html/body/div[4]"));
        Assert.assertEquals(webElement.getText(), "Ошибка: Bad credentials");

        webElement = webDriver.findElement(By.xpath("/html/body/form/table/tbody/tr[1]/td[2]/label/input"));
        webElement.sendKeys("test@test.com");
        webElement = webDriver.findElement(By.xpath("/html/body/form/table/tbody/tr[2]/td[2]/label/input"));
        webElement.sendKeys("test");
        webElement = webDriver.findElement(By.xpath("/html/body/form/table/tbody/tr[3]/td[2]/label/input"));
        webElement.click();
        Assert.assertEquals(webDriver.getCurrentUrl(), appURL);
        Assert.assertEquals(webDriver.findElements(By.xpath("//*[@href=\"/bookstore/login\"]")).size(), 0);
        Assert.assertEquals(webDriver.findElements(By.xpath("//*[@href=\"/bookstore/register\"]")).size(), 0);
        Assert.assertEquals(webDriver.findElements(By.xpath("//*[@href=\"/bookstore/account\"]")).size(), 1);
        Assert.assertEquals(webDriver.findElements(By.xpath("//*[@href=\"/bookstore/logout\"]")).size(), 1);
        Assert.assertEquals(webDriver.findElements(By.xpath("//*[@href=\"/bookstore/cart\"]")).size(), 1);
    }
}
