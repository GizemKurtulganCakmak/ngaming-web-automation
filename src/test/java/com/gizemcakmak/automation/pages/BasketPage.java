package com.gizemcakmak.automation.pages;

import com.gizemcakmak.automation.model.CouponEvent;
import com.gizemcakmak.automation.model.CouponSnapshot;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class BasketPage extends BasePage
{
    private static final By PANEL = By.id("outerCoupon");
    private static final By ROWS = By.cssSelector("#basketCoupon .eventsContainer .couponRow.outer");
    public void waitForExpectedEventCount(int expected)
    {
        wait.until(driver ->
        {
            List<WebElement> panels = driver.findElements(PANEL);
            return !panels.isEmpty() && panels.get(0).isDisplayed()
                    && driver.findElements(ROWS).stream().filter(WebElement::isDisplayed).count() == expected;
        });
    }
    public int getDisplayedEventCount()
    {
        return Integer.parseInt(visible(By.cssSelector("#basketCoupon .numberOfEvents")).getText().trim());
    }
    public CouponSnapshot captureBasket()
    {
        visible(PANEL);
        List<CouponEvent> events = new ArrayList<>();
        for (WebElement row : driver.findElements(ROWS))
        {
            if (!row.isDisplayed()) continue;
            String name = row.findElement(By.cssSelector(".eventName")).getText().trim();
            String date = row.findElement(By.cssSelector(".eventDate")).getText().trim();
            events.add(new CouponEvent(name, date));
        }
        return new CouponSnapshot(events);
    }
    public boolean hasExpectedTitle() {
        return visible(By.cssSelector("#basketCoupon .couponTitle")).getText().contains("İDDAA KUPONUM");
    }
    public void ensureOpen() {

        By panel = By.id("outerCoupon");

        By basketToggle = By.cssSelector(
                "#coupon-info .event-count"
        );

// Sepet zaten açıksa tekrar tıklama.
        if (isBasketVisible(panel)) {
            return;
        }

// Sepet kapalıysa header'daki kupon alanına tıkla.
        wait.until(
                ExpectedConditions.elementToBeClickable(
                        basketToggle
                )
        ).click();

// Sepet açılana kadar bekle.
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        panel
                )
        );
    }

    private boolean isBasketVisible(By panel) {

        return driver.findElements(panel)
                .stream()
                .anyMatch(WebElement::isDisplayed);
    }

}
