package Devops_Assignment.Devops_Assignment;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class AmazonTest {

    WebDriver driver;
    String browserName;

    @Parameters("browser")
    @BeforeMethod
    public void setup(@Optional("chrome") String browser) throws MalformedURLException {

        browserName = browser;

        DesiredCapabilities capabilities = new DesiredCapabilities();

        if (browser.equalsIgnoreCase("chrome")) {
            capabilities.setBrowserName("chrome");
        } 
        else if (browser.equalsIgnoreCase("firefox")) {
            capabilities.setBrowserName("firefox");
        } 
        else if (browser.equalsIgnoreCase("edge")) {
            capabilities.setBrowserName("MicrosoftEdge");
        } 
        else {
            throw new IllegalArgumentException("Invalid browser: " + browser);
        }

        driver = new RemoteWebDriver(
                new URL("http://localhost:4444"),
                capabilities
        );

        driver.manage().timeouts()
              .pageLoadTimeout(Duration.ofSeconds(60));

        driver.manage().window().maximize();
    }

    @Test
    public void amazonNavigationTest() {

        System.out.println(
                "Navigating to Amazon on browser: " + browserName);

        driver.get("https://www.amazon.in/");

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(45));

        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.tagName("body")));

        String title = driver.getTitle();

        System.out.println(
                "Page Title on " + browserName + ": " + title);

        System.out.println(
                "Amazon page loaded successfully on: " + browserName);
    }

    @AfterMethod
    public void tearDown() {

        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                System.out.println(
                        "Browser cleanup warning on "
                        + browserName + ": " + e.getMessage());
            }
        }
    }
}