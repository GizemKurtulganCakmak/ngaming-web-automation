package com.gizemcakmak.automation.pages;

import org.openqa.selenium.By;

public class HomePage extends BasePage
{
    public static final String BASE_URL = "https://www.nesine.com";
    private static final By POPULAR = By.cssSelector("a[href='/iddaa/populer-kuponlar']");
    public void open() {
        driver.get(BASE_URL);
        acceptCookiesIfPresent();
        closeTutorialIfPresent(); }
    public void goToPopularCoupons() { click(POPULAR);}
}
