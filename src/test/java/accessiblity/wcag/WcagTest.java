package accessiblity.wcag;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import accessiblity.utilities.DriverManager;

public class WcagTest {
    WebDriver driver;
    Wcag wcag;
    

    @BeforeClass
    @Parameters({"browser"})
    public void setUp(@Optional("chrome") String browser) {
        driver = DriverManager.getDriver(browser);
        wcag = new Wcag(driver);
    }

    @Test
    public void testAccessibility() {
        wcag.testAccessibility("https://www.flipkart.com/");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
    
}
