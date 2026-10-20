package com.gizemcakmak.automation.pages;

import com.gizemcakmak.automation.core.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public abstract class BasePage
{
    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected BasePage()
    {
        driver = DriverFactory.getDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }
    protected WebElement visible(By by) { return wait.until(ExpectedConditions.visibilityOfElementLocated(by)); }
    protected WebElement clickable(By by) { return wait.until(ExpectedConditions.elementToBeClickable(by)); }
    protected void click(By by) { clickable(by).click(); }


    public void closeTutorialIfPresent() {

        By tutorial = By.id("tutorial");

        By closeButton = By.xpath(
                "//*[@id='tutorial']//button[normalize-space(.)='Kapat']"
        );

        WebDriverWait shortWait = new WebDriverWait(
                driver,
                Duration.ofSeconds(2)
        );

        shortWait.pollingEvery(Duration.ofMillis(200));

        try {
            WebElement button = shortWait.until(
                    ExpectedConditions.elementToBeClickable(closeButton)
            );

            button.click();

            wait.until(
                    ExpectedConditions.invisibilityOfElementLocated(tutorial)
            );

            System.out.println("Tutorial kapatıldı.");

        } catch (TimeoutException ignored) {
// Tutorial görünmediyse normal akışa devam et.
        }
    }


    public void acceptCookiesIfPresent() {

        By acceptButton = By.xpath(
                "//section[@id='cookiesPopup']" +
                        "//button[normalize-space(.)='Kabul Et']"
        );

        try {
            WebDriverWait shortWait = new WebDriverWait(
                    driver,
                    Duration.ofSeconds(3)
            );

            WebElement button = shortWait.until(
                    ExpectedConditions.elementToBeClickable(
                            acceptButton
                    )
            );

            button.click();

            shortWait.until(
                    ExpectedConditions.invisibilityOfElementLocated(
                            By.id("cookiesPopup")
                    )
            );

            System.out.println("Çerez bildirimi kabul edildi.");

        } catch (TimeoutException ignored) {
            // Çerez bildirimi çıkmadıysa devam et.
        }
    }


}

