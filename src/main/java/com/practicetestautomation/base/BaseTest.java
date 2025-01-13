package com.practicetestautomation.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

public class BaseTest {

    protected WebDriver driver;
    protected Logger log;

    @Parameters({"browser", "environment"})
    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method, @Optional("chrome") String browser, @Optional("local") String environment, ITestContext ctx) {
        log = LogManager.getLogger(ctx.getCurrentXmlTest().getSuite().getName());
//		driver = new BrowserDriverFactory(browser, log).createDriver();

        switch (environment) {
            case "local":
                driver = driver = new BrowserDriverFactory(browser, log).createDriver();
                break;

            case "grid":
                driver = driver = new GridFactory(browser, log).createDriver();
                break;

            default:
                driver = driver = new BrowserDriverFactory(browser, log).createDriver();
        }

        driver.manage().window().maximize();

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("chrome");

        try {
            driver = new RemoteWebDriver(new URL("http://localhost:4444"), capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        driver.manage().window().maximize();
    }


    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        log.info("Close driver");
        driver.quit();
    }

}