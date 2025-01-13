package com.practicetestautomation.base;

import java.util.logging.Level;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class BrowserDriverFactory {

	private ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	private String browser;
	private Logger log;

	public BrowserDriverFactory(String browser, Logger log) {
		this.browser = browser.toLowerCase();
		this.log = log;
	}

	public WebDriver createDriver() {
		log.info("Create local driver: " + browser);

		switch (browser) {
			case "chrome" -> {
				System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
				driver.set(new ChromeDriver());
			}
			case "firefox" -> {
				FirefoxOptions options = new FirefoxOptions();
				driver.set(new FirefoxDriver(options));
			}
			default -> {
				log.debug("Unknown browser: " + browser + ", starting chrome by default.");
				System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
				driver.set(new ChromeDriver());
			}
		}

		// Suppress Selenium logs
		java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(Level.SEVERE);
		return driver.get();
	}
}
