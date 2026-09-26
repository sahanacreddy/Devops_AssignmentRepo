package Devops_Assignment.Devops_Assignment;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class AmazonTest {

    WebDriver driver;
    String browserName;

    @Parameters("browser")
    @BeforeMethod
    public void setup(String browser) throws MalformedURLException {

        browserName = browser;

        DesiredCapabilities capabilities = new DesiredCapabilities();

        if (browser.equalsIgnoreCase("chrome")) {

            capabilities.setBrowserName("chrome");

        } else if (browser.equalsIgnoreCase("firefox")) {

            capabilities.setBrowserName("firefox");

        } else if (browser.equalsIgnoreCase("edge")) {

            capabilities.setBrowserName("MicrosoftEdge");

        } else {

            throw new IllegalArgumentException(
                    "Invalid browser: " + browser
            );
        }

        driver = new RemoteWebDriver(
                new URL("http://localhost:4444"),
                capabilities
        );

        driver.manage().window().maximize();
    }

    @Test
    public void amazonSearchTest() {

        driver.get("https://www.amazon.in/");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        // Wait for Amazon search box
        WebElement searchBox = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.id("twotabsearchtextbox")
            )
        );

        System.out.println("Amazon page loaded on browser: " + browserName);

        searchBox.sendKeys("laptop");

        driver.findElement(By.id("nav-search-submit-button")).click();

        // Wait for search results page
        wait.until(ExpectedConditions.urlContains("laptop"));

        System.out.println("Search completed on browser: " + browserName);

        Assert.assertTrue(
            driver.getCurrentUrl().toLowerCase().contains("laptop"),
            "Amazon search results were not displayed"
        );

        System.out.println("Test Passed on browser: " + browserName);
    }
    
    @AfterMethod
    public void tearDown() {

        if (driver != null) {
            driver.quit();
        }
    }
}
