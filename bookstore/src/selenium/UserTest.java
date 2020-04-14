package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class UserTest extends GenericTest {
    @BeforeClass(dependsOnMethods = "setUpDriver", dependsOnGroups = "guest")
    private void login() {
        webDriver.get(appURL + "login");
        WebElement webElement = webDriver.findElement(By.xpath("/html/body/form/table/tbody/tr[1]/td[2]/label/input"));
        webElement.sendKeys("test@test.com");
        webElement = webDriver.findElement(By.xpath("/html/body/form/table/tbody/tr[2]/td[2]/label/input"));
        webElement.sendKeys("test");
        webElement = webDriver.findElement(By.xpath("/html/body/form/table/tbody/tr[3]/td[2]/label/input"));
        webElement.click();
    }

    @Test(groups = "user")
    public void purchase() {
        webDriver.get(appURL + "book?id=1");

        Assert.assertEquals(webDriver.findElements(By.xpath("//*[@href=\"/bookstore/orderList\"]")).size(), 0);
        Assert.assertEquals(webDriver.findElements(By.xpath(
                "//*[@href=\"/html/body/div[2]/select\"]")).size(), 0);
        Assert.assertEquals(webDriver.findElements(By.xpath("//*[@href=\"/bookstore/deleteBook/1\"]")).size(), 0);
        Assert.assertEquals(webDriver.findElements(By.xpath("//*[@href=\"/bookstore/editBook?id=1\"]")).size(), 0);

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

        webDriver.get(appURL + "book?id=5");
        webElement = webDriver.findElement(By.xpath("/html/body/table/tbody/tr[8]/td"));
        Assert.assertEquals(webElement.getText(), "11 шт.");
    }

    @Test(groups = "user", dependsOnMethods = "purchase")
    public void viewAccount() {
        webDriver.get(appURL + "account");

        WebElement webElement = webDriver.findElement(By.xpath("//*[@id=\"accountInfo\"]/table/tbody/tr[1]/td"));
        Assert.assertEquals(webElement.getText(), "test@test.com");
        webElement = webDriver.findElement(By.xpath("//*[@id=\"accountInfo\"]/table/tbody/tr[2]/td"));
        Assert.assertEquals(webElement.getText(), "test");
        webElement = webDriver.findElement(By.xpath("//*[@id=\"accountInfo\"]/table/tbody/tr[3]/td"));
        Assert.assertEquals(webElement.getText(), "test");
        webElement = webDriver.findElement(By.xpath("//*[@id=\"accountInfo\"]/table/tbody/tr[4]/td"));
        Assert.assertEquals(webElement.getText(), "+74951234567");

        webElement = webDriver.findElement(By.xpath("//*[@id=\"accountInfo\"]/table/tbody/tr[5]/td[2]/a"));
        webElement.click();

        webElement = webDriver.findElement(By.xpath("//*[@id=\"userAccountForm\"]/table/tbody/tr[1]/td"));
        Assert.assertEquals(webElement.getText(), "test@test.com");
        webElement = webDriver.findElement(By.xpath("//*[@id=\"userName\"]"));
        Assert.assertEquals(webElement.getAttribute("value"), "test");
        webElement = webDriver.findElement(By.xpath(
                "//*[@id=\"userAccountForm\"]/table/tbody/tr[3]/td[1]/label/textarea"));
        Assert.assertEquals(webElement.getAttribute("value"), "test");
        webElement = webDriver.findElement(By.xpath("//*[@id=\"phoneNumber\"]"));
        Assert.assertEquals(webElement.getAttribute("value"), "+74951234567");

        webElement = webDriver.findElement(By.xpath("//*[@id=\"userName\"]"));
        webElement.clear();
        webElement = webDriver.findElement(By.xpath(
                "//*[@id=\"userAccountForm\"]/table/tbody/tr[7]/td[2]/label/input"));
        webElement.click();
        webElement = webDriver.findElement(By.xpath("//*[@id=\"userName.errors\"]"));
        Assert.assertEquals(webElement.getText(), "Поле не должно быть пустым");

        webElement = webDriver.findElement(By.xpath("//*[@id=\"phoneNumber\"]"));
        webElement.sendKeys(Keys.BACK_SPACE);
        webElement = webDriver.findElement(By.xpath(
                "//*[@id=\"userAccountForm\"]/table/tbody/tr[7]/td[2]/label/input"));
        webElement.click();
        webElement = webDriver.findElement(By.xpath("//*[@id=\"phoneNumber.errors\"]"));
        Assert.assertEquals(webElement.getText(), "Ввод должен иметь формат +71234567890");

        webElement = webDriver.findElement(By.xpath("//*[@id=\"password\"]"));
        webElement.sendKeys("a");
        webElement = webDriver.findElement(By.xpath(
                "//*[@id=\"userAccountForm\"]/table/tbody/tr[7]/td[2]/label/input"));
        webElement.click();
        webElement = webDriver.findElement(By.xpath("//*[@id=\"repeatedPassword.errors\"]"));
        Assert.assertEquals(webElement.getText(), "Пароли не совпадают");

        webElement = webDriver.findElement(By.xpath("//*[@id=\"password\"]"));
        webElement.clear();
        webElement = webDriver.findElement(By.xpath(
                "//*[@id=\"userAccountForm\"]/table/tbody/tr[3]/td[1]/label/textarea"));
        webElement.clear();
        webElement = webDriver.findElement(By.xpath("//*[@id=\"userAccountForm\"]/table/tbody/tr[7]/td[2]/label/a"));
        webElement.click();
        webElement = webDriver.findElement(By.xpath("//*[@id=\"accountInfo\"]/table/tbody/tr[3]/td"));
        Assert.assertEquals(webElement.getText(), "test");

        webElement = webDriver.findElement(By.xpath("//*[@id=\"accountInfo\"]/table/tbody/tr[5]/td[2]/a"));
        webElement.click();

        webElement = webDriver.findElement(By.xpath(
                "//*[@id=\"userAccountForm\"]/table/tbody/tr[3]/td[1]/label/textarea"));
        webElement.clear();
        webElement = webDriver.findElement(By.xpath(
                "//*[@id=\"userAccountForm\"]/table/tbody/tr[7]/td[2]/label/input"));
        webElement.click();
        webElement = webDriver.findElement(By.xpath("//*[@id=\"accountInfo\"]/table/tbody/tr[3]/td"));
        Assert.assertEquals(webElement.getText(), "");
    }

    @Test(groups = "user", dependsOnMethods = "viewAccount")
    public void viewOrders() {
        webDriver.get(appURL + "account");

        WebElement webElement = webDriver.findElement(By.xpath("//*[@id=\"ui-id-2\"]"));
        webElement.click();
        List<WebElement> rows = webDriver.findElements(By.xpath("//*[@id=\"ordersInfo\"]/table/tbody/tr"));
        Assert.assertEquals(rows.size(), 2);
        webElement = webDriver.findElement(By.xpath("//*[@id=\"ordersInfo\"]/table/tbody/tr[2]/td[3]"));
        Assert.assertEquals(webElement.getText(), "В обработке");
        webElement = webDriver.findElement(By.xpath("//*[@id=\"ordersInfo\"]/table/tbody/tr[2]/td[8]/a"));
        webElement.click();
        webDriver.switchTo().alert().dismiss();
        webElement = webDriver.findElement(By.xpath("//*[@id=\"ordersInfo\"]/table/tbody/tr[2]/td[3]"));
        Assert.assertEquals(webElement.getText(), "В обработке");
        webElement = webDriver.findElement(By.xpath("//*[@id=\"ordersInfo\"]/table/tbody/tr[2]/td[8]/a"));
        webElement.click();
        webDriver.switchTo().alert().accept();
        webElement = webDriver.findElement(By.xpath("//*[@id=\"ordersInfo\"]/table/tbody/tr[2]/td[3]"));
        Assert.assertEquals(webElement.getText(), "Отменён");
        Assert.assertEquals(webDriver.findElements(
                By.xpath("//*[@id=\"ordersInfo\"]/table/tbody/tr[2]/td[8]/a")).size(), 0);
    }

    @Test(groups = "user", dependsOnMethods = "viewOrders")
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

    @Test(groups = "user", dependsOnMethods = "logout")
    public void deleteAccount() {
        login();
        webDriver.get(appURL + "account");
        WebElement webElement = webDriver.findElement(By.xpath("//*[@id=\"accountInfo\"]/table/tbody/tr[5]/td[2]/a"));
        webElement.click();
        webElement = webDriver.findElement(By.xpath("//*[@id=\"accountInfo\"]/a"));
        webElement.click();
        webDriver.switchTo().alert().dismiss();
        Assert.assertEquals(webDriver.getCurrentUrl(), appURL + "account?edit=true");
        webElement = webDriver.findElement(By.xpath("//*[@id=\"accountInfo\"]/a"));
        webElement.click();
        webDriver.switchTo().alert().accept();
        Assert.assertEquals(webDriver.getCurrentUrl(), appURL + "?accountDeleted=true");
        webElement = webDriver.findElement(By.xpath("/html/body/div[3]"));
        Assert.assertEquals(webElement.getText(), "Учётная запись удалена");
        Assert.assertEquals(webDriver.findElements(By.xpath("//*[@href=\"/bookstore/account\"]")).size(), 0);
        Assert.assertEquals(webDriver.findElements(By.xpath("//*[@href=\"/bookstore/logout\"]")).size(), 0);
        Assert.assertEquals(webDriver.findElements(By.xpath("//*[@href=\"/bookstore/cart\"]")).size(), 0);
        Assert.assertEquals(webDriver.findElements(By.xpath("//*[@href=\"/bookstore/login\"]")).size(), 1);
        Assert.assertEquals(webDriver.findElements(By.xpath("//*[@href=\"/bookstore/register\"]")).size(), 1);
    }
}
