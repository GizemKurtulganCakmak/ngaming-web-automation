package com.gizemcakmak.automation.pages;

import com.gizemcakmak.automation.model.CouponEvent;
import com.gizemcakmak.automation.model.CouponSnapshot;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class PopularCouponsPage extends BasePage {

    // Ana E2E locator'ları
    private static final By CONTAINER =
            By.id("popularCouponsViewContainer");

    private static final By CARDS =
            By.cssSelector(
                    "#popularCouponsViewContainer div[data-cs]"
            );

    private static final By ROWS =
            By.cssSelector("[data-testid^='event-row-']");

    private static final By PLAY =
            By.cssSelector(
                    "button[data-logid^='pplcpn-']"
            );

    // Serbest senaryo locator'ları
    private static final By TWO_MATCH_FILTER =
            By.xpath(
                    "//button[normalize-space(.)='2 Maç']"
            );

    private static final By WINNERS_TAB =
            By.xpath(
                    "//*[self::button or self::a]" +
                            "[normalize-space(.)='Kazananlar']"
            );

    private static final By PLAY_NOW_TAB =
            By.xpath(
                    "//*[self::button or self::a]" +
                            "[normalize-space(.)='Hemen Oyna']" +
                            "[not(ancestor::*[@id='popularCouponsViewContainer'])]"
            );

    public boolean isOpened() {
        return driver.getCurrentUrl()
                .contains("/iddaa/populer-kuponlar");
    }

    // --------------------------------------------------
    // Ortak yardımcı metotlar
    // --------------------------------------------------

    private String normalize(String text) {
        return text.replaceAll("\\s+", " ").trim();
    }

    private List<WebElement> availableCards() {

        visible(CONTAINER);

        // Sabit Thread.sleep yerine koşullu bekleme
        WebDriverWait cardWait = new WebDriverWait(
                driver,
                Duration.ofSeconds(15)
        );

        cardWait.until(webDriver ->
                webDriver.findElements(CARDS).stream()
                        .anyMatch(card ->
                                !card.findElements(ROWS).isEmpty()
                                        && !card.findElements(PLAY).isEmpty()
                        )
        );

        return driver.findElements(CARDS).stream()
                .filter(card ->
                        !card.findElements(ROWS).isEmpty()
                                && !card.findElements(PLAY).isEmpty()
                )
                .toList();
    }

    private WebElement findCard(String id) {

        return availableCards().stream()
                .filter(card ->
                        id.equals(card.getAttribute("data-cs"))
                )
                .findFirst()
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Kupon kayboldu: " + id
                        )
                );
    }

    // --------------------------------------------------
    // Ana E2E senaryosu
    // --------------------------------------------------

    public int getCouponCount() {
        return availableCards().size();
    }

    public String getFirstAvailableCouponId() {

        return availableCards().stream()
                .findFirst()
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Uygun kupon bulunamadı"
                        )
                )
                .getAttribute("data-cs");
    }

    public CouponSnapshot captureCoupon(String id) {

        WebElement card = findCard(id);

        List<CouponEvent> events = new ArrayList<>();

        for (WebElement row : card.findElements(ROWS)) {

            String name = normalize(
                    row.findElement(
                            By.cssSelector(
                                    "[data-testid='event-name']"
                            )
                    ).getText()
            );

            // Kaynak HTML'deki mevcut tarih locator'ı
            String date = normalize(
                    row.findElement(
                            By.xpath(
                                    ".//*[@data-testid='event-rate']" +
                                            "/../following-sibling::div[1]"
                            )
                    ).getText()
            );

            if (name.isBlank() || date.isBlank()) {
                throw new IllegalStateException(
                        "Boş etkinlik adı veya tarihi"
                );
            }

            events.add(new CouponEvent(name, date));
        }

        return new CouponSnapshot(events);
    }

    public void addSelectedCouponToBasket(String id) {

        WebElement card = findCard(id);

        card.findElement(PLAY).click();
    }

    // --------------------------------------------------
    // SERBEST SENARYO 1 - 2 Maç filtresi
    // --------------------------------------------------

    public void selectTwoMatchFilter() {

        click(TWO_MATCH_FILTER);

    }

    public boolean allVisibleCouponsHaveTwoEvents() {

        WebDriverWait filterWait = new WebDriverWait(
                driver,
                Duration.ofSeconds(10)
        );

        return filterWait.until(webDriver -> {

            List<WebElement> cards = availableCards().stream()
                    .filter(WebElement::isDisplayed)
                    .toList();

            if (cards.isEmpty()) {
                return false;
            }

            for (WebElement card : cards) {

                int eventCount = card.findElements(ROWS).size();

                if (eventCount != 2) {
                    return false;
                }
            }

            return true;
        });
    }

    // --------------------------------------------------
    // SERBEST SENARYO 2 - Sekme geçişleri
    // --------------------------------------------------

    public void goToWinnersTab() {
        click(WINNERS_TAB);
    }

    public void goToPlayNowTab() {
        click(PLAY_NOW_TAB);
    }

    public boolean isWinnersTabActive() {
        return isElementActive(WINNERS_TAB);
    }

    public boolean isPlayNowTabActive() {
        return isElementActive(PLAY_NOW_TAB);
    }

    // --------------------------------------------------
    // Ortak aktif element kontrolü
    // --------------------------------------------------

    private boolean isElementActive(By locator) {

        List<WebElement> elements =
                driver.findElements(locator);

        for (WebElement element : elements) {

            if (!element.isDisplayed()) {
                continue;
            }

            String dataactive =
                    element.getAttribute("data-active");

            if ("true".equals(dataactive))
            {

                return true;
            }
        }
        return false;
    }
}