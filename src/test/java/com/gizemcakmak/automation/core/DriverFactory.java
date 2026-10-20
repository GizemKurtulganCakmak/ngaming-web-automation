package com.gizemcakmak.automation.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.time.Duration;

public final class DriverFactory
{
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();
    private DriverFactory() {}
    public static void createDriver() {
        if (DRIVER.get() != null) throw new IllegalStateException("Driver zaten mevcut");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized", "--incognito");
        if (Boolean.getBoolean("headless")) options.addArguments("--headless=new", "--window-size=1440,900");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        DRIVER.set(driver);
    }
    public static WebDriver getDriver() {
        WebDriver driver = DRIVER.get();
        if (driver == null) throw new IllegalStateException("Driver @Before hook öncesinde istendi");
        return driver;
    }
    public static void quitDriver() {
        WebDriver driver = DRIVER.get();
        try { if (driver != null) driver.quit(); }
        finally { DRIVER.remove(); }
    }
}
