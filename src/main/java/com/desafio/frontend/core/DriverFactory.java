package com.desafio.frontend.core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

/**
 * Cria e gerencia instâncias do WebDriver com base nas configs.
 */
public class DriverFactory {

    private static final ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        WebDriver driver = tlDriver.get();
        if (driver == null) {
            driver = createDriver();
            tlDriver.set(driver);
        }
        return driver;
    }

    public static void quitDriver() {
        WebDriver driver = tlDriver.get();
        if (driver != null) {
            driver.quit();
            tlDriver.remove();
        }
    }

    private static WebDriver createDriver() {
        String browser = Config.browser();
        boolean headless = Config.headless();
        int implicitWait = Config.implicitWaitSeconds();

        WebDriver driver;
        if (browser == null || browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            if (headless) options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--disable-gpu", "--no-sandbox", "--disable-dev-shm-usage");
            driver = new ChromeDriver(options);
        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxDriver.builder();
            FirefoxOptions options = new FirefoxOptions();
            if (headless) options.addArguments("-headless");
            options.addArguments("-window-size=1920,1080");
            options.addArguments("-safe-mode");
            driver = new FirefoxDriver(options);
        } else {
            throw new IllegalArgumentException("Navegador não suportado: " + browser);
        }

        if (implicitWait > 0) {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
        }
        driver.manage().window().maximize();
        return driver;
    }
}
