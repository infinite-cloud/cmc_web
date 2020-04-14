package selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

abstract public class GenericTest {
    private final String chromeDriver = ""; // chromeDriver path
    protected final String appURL = "http://localhost:8080/bookstore/";
    protected WebDriver webDriver;

    @BeforeClass
    protected void setUpDriver() {
        System.setProperty("webdriver.chrome.driver", chromeDriver);
        webDriver = new ChromeDriver();
    }

    @AfterClass
    protected void shutDownDriver() {
        webDriver.quit();
    }
}
