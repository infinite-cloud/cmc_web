package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class UserTest extends GenericTest {
    @BeforeClass(dependsOnGroups = "guest")
    private void login() {
        webDriver.get(appURL + "login");
        WebElement webElement = webDriver.findElement(By.xpath("/html/body/form/table/tbody/tr[1]/td[2]/label/input"));
        webElement.sendKeys("test@test.com");
        webElement = webDriver.findElement(By.xpath("/html/body/form/table/tbody/tr[2]/td[2]/label/input"));
        webElement.sendKeys("test");
        webElement = webDriver.findElement(By.xpath("/html/body/form/table/tbody/tr[3]/td[2]/label/input"));
        webElement.click();
    }

    @Test(priority = 0, groups = "user")
    public void purchase() {
        webDriver.get(appURL + "book?id=1");
        Assert.assertEquals(webDriver.findElements(By.xpath("//*[@href=\"/bookstore/cart\"]")).size(), 1);
        WebElement webElement = webDriver.findElement(By.xpath("/html/body/div[5]/a"));
        webElement.click();
        webElement = webDriver.findElement(By.xpath("/html/body/div[5]"));
        Assert.assertEquals(webElement.getText(), "В корзину На складе недостаточно экземпляров");

        webDriver.get(appURL + "book?id=5");

        for (int i = 0; i < 12; ++i) {
            webElement = webDriver.findElement(By.xpath("/html/body/div[5]/a"));
            webElement.click();
            webElement = webDriver.findElement(By.xpath("/html/body/div[5]"));
            Assert.assertEquals(webElement.getText(), "В корзину Товар добавлен в корзину");
        }

        webElement = webDriver.findElement(By.xpath("/html/body/div[5]/a"));
        webElement.click();
        webElement = webDriver.findElement(By.xpath("/html/body/div[5]"));
        Assert.assertEquals(webElement.getText(), "В корзину На складе недостаточно экземпляров");

        webElement = webDriver.findElement(By.xpath("/html/body/div[2]/a[2]"));
        webElement.click();
        Assert.assertEquals(webDriver.getCurrentUrl(), appURL + "cart");

        List<WebElement> rows = webDriver.findElements(By.xpath("/html/body/table/tbody/tr"));
        Assert.assertEquals(rows.size(), 3);
        webElement = webDriver.findElement(By.xpath("//*[@id=\"bookList0.quantity\"]"));
        Assert.assertEquals(webElement.getAttribute("value"), "12");
        webElement.clear();
        webElement.sendKeys("11");
        webElement = webDriver.findElement(By.xpath("/html/body/table/tbody/tr[3]/td[4]/input"));
        webElement.click();
        webElement = webDriver.findElement(By.xpath("//*[@id=\"bookList0.quantity\"]"));
        Assert.assertEquals(webElement.getAttribute("value"), "11");
        webElement.clear();
        webElement.sendKeys("13");
        webElement = webDriver.findElement(By.xpath("/html/body/table/tbody/tr[3]/td[4]/input"));
        webElement.click();
        webElement = webDriver.findElement(By.xpath("//*[@id=\"bookList0.quantity\"]"));
        Assert.assertEquals(webElement.getAttribute("value"), "11");
        webElement = webDriver.findElement(By.xpath("//*[@id=\"bookList0.quantity.errors\"]"));
        Assert.assertEquals(webElement.getText(), "На складе недостаточно экземпляров");

        webElement = webDriver.findElement(By.xpath("/html/body/table/tbody/tr[2]/td[6]/a"));
        webElement.click();
        rows = webDriver.findElements(By.xpath("/html/body/table/tbody/tr"));
        Assert.assertEquals(rows.size(), 2);

        webDriver.get(appURL + "book?id=5");
        webElement = webDriver.findElement(By.xpath("/html/body/div[5]/a"));
        webElement.click();
        webElement = webDriver.findElement(By.xpath("/html/body/div[5]"));
        Assert.assertEquals(webElement.getText(), "В корзину Товар добавлен в корзину");
        webDriver.get(appURL + "book?id=4");
        webElement = webDriver.findElement(By.xpath("/html/body/div[5]/a"));
        webElement.click();
        webElement = webDriver.findElement(By.xpath("/html/body/div[5]"));
        Assert.assertEquals(webElement.getText(), "В корзину Товар добавлен в корзину");

        webDriver.get(appURL + "cart");
        rows = webDriver.findElements(By.xpath("/html/body/table/tbody/tr"));
        Assert.assertEquals(rows.size(), 4);
        webElement = webDriver.findElement(By.xpath("//*[@id=\"bookList0.quantity\"]"));
        Assert.assertEquals(webElement.getAttribute("value"), "1");
        webElement = webDriver.findElement(By.xpath("//*[@id=\"bookList1.quantity\"]"));
        Assert.assertEquals(webElement.getAttribute("value"), "1");

        webElement = webDriver.findElement(By.xpath("/html/body/table/tbody/tr[4]/td[6]/a"));
        webElement.click();
        rows = webDriver.findElements(By.xpath("/html/body/table/tbody/tr"));
        Assert.assertEquals(rows.size(), 2);

        webElement = webDriver.findElement(By.xpath("/html/body/a"));
        webElement.click();
        Assert.assertEquals(webDriver.getCurrentUrl(), appURL + "cart?isEmpty=true");
        webElement = webDriver.findElement(By.xpath("/html/body"));
        Assert.assertEquals(webElement.getText(), "Книжный магазин\n" +
                "Личный кабинет  |  Выход\n" +
                "Каталог  |  Корзина\n" +
                "Корзина\n" +
                "Ваша корзина пуста\n" +
                "\n" +
                "№ Название Цена Количество Итог\n" +
                "0.0 руб. Очистить\n" +
                "Оформить заказ\n" +
                "© infinite-cloud");

        webDriver.get(appURL + "book?id=5");
        webElement = webDriver.findElement(By.xpath("/html/body/div[5]/a"));
        webElement.click();
        webDriver.get(appURL + "cart");
        webElement = webDriver.findElement(By.xpath("/html/body/a"));
        webElement.click();
        Assert.assertEquals(webDriver.getCurrentUrl(), appURL + "placeOrder");

        webElement = webDriver.findElement(By.xpath("//*[@id=\"orderForm\"]/table/tbody/tr[3]/td[2]/label/input"));
        webElement.click();
        webElement = webDriver.findElement(By.xpath("//*[@id=\"deliveryDate.errors\"]"));
        Assert.assertEquals(webElement.getText(), "Поле не должно быть пустым");

        webElement = webDriver.findElement(By.xpath("//*[@id=\"deliveryDate\"]"));
        webElement.sendKeys("01012010");
        webElement.sendKeys(Keys.RIGHT);
        webElement.sendKeys("1230");
        webElement = webDriver.findElement(By.xpath("//*[@id=\"orderForm\"]/table/tbody/tr[3]/td[2]/label/input"));
        webElement.click();
        webElement = webDriver.findElement(By.xpath("//*[@id=\"deliveryAddress.errors\"]"));
        Assert.assertEquals(webElement.getText(), "Поле не должно быть пустым");
        webElement = webDriver.findElement(By.xpath("//*[@id=\"deliveryDate.errors\"]"));
        Assert.assertEquals(webElement.getText(), "Некорректная дата");

        webElement = webDriver.findElement(By.xpath("//*[@id=\"orderForm\"]/table/tbody/tr[1]/td[2]/label/textarea"));
        webElement.sendKeys("test");
        webElement = webDriver.findElement(By.xpath("//*[@id=\"deliveryDate\"]"));
        webElement.sendKeys("01082021");
        webElement.sendKeys(Keys.RIGHT);
        webElement.sendKeys("1230");
        webElement = webDriver.findElement(By.xpath("//*[@id=\"orderForm\"]/table/tbody/tr[3]/td[2]/label/input"));
        webElement.click();
        webElement = webDriver.findElement(By.xpath("/html/body"));
        Assert.assertEquals(webElement.getText(), "Книжный магазин\n" +
                "Личный кабинет  |  Выход\n" +
                "Каталог  |  Корзина\n" +
                "Оформление заказа\n" +
                "Заказ оформлен\n" +
                "Номер вашего заказа: 5\n" +
                "© infinite-cloud");
    }

    @Test(priority = 1, groups = "user")
    public void viewAccount() {

    }

    @Test(priority = 2, groups = "user")
    public void viewOrders() {

    }

    @Test(priority = 3, groups = "user")
    public void logout() {
        webDriver.get(appURL);

        WebElement webElement = webDriver.findElement(By.xpath("/html/body/div[1]/div[2]/a[2]"));
        webElement.click();
        Assert.assertEquals(webDriver.getCurrentUrl(), appURL);
        Assert.assertEquals(webDriver.findElements(By.xpath("//*[@href=\"/bookstore/login\"]")).size(), 1);
        Assert.assertEquals(webDriver.findElements(By.xpath("//*[@href=\"/bookstore/register\"]")).size(), 1);
        Assert.assertEquals(webDriver.findElements(By.xpath("//*[@href=\"/bookstore/account\"]")).size(), 0);
        Assert.assertEquals(webDriver.findElements(By.xpath("//*[@href=\"/bookstore/logout\"]")).size(), 0);
        Assert.assertEquals(webDriver.findElements(By.xpath("//*[@href=\"/bookstore/cart\"]")).size(), 0);
    }

    @Test(priority = 4, groups = "user")
    public void deleteAccount() {

    }
}
