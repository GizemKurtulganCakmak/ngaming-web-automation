package com.gizemcakmak.automation.steps;

import com.gizemcakmak.automation.context.ScenarioContext;
import com.gizemcakmak.automation.model.CouponSnapshot;
import com.gizemcakmak.automation.pages.BasketPage;
import io.cucumber.java.en.Then;
import static org.junit.jupiter.api.Assertions.*;

public class BasketStep
{
    private final ScenarioContext context;
    public BasketStep(ScenarioContext context) { this.context = context; }
    @Then("kupon sepeti görüntülenmelidir")
    public void basketVisible()
    {
        new BasketPage().waitForExpectedEventCount(context.getSelectedCoupon().events().size());
    }
    @Then("sepet başlığı doğru olmalıdır")
    public void title() { assertTrue(new BasketPage().hasExpectedTitle()); }
    @Then("sepette en az bir etkinlik bulunmalıdır")
    public void count() { assertTrue(new BasketPage().getDisplayedEventCount() > 0); }
    @Then("sepetteki etkinlik bilgileri seçilen kuponla aynı olmalıdır")
    public void compare()
    {
        BasketPage basket = new BasketPage();
        CouponSnapshot expected = context.getSelectedCoupon();
        basket.ensureOpen();
        basket.waitForExpectedEventCount(expected.events().size());
        CouponSnapshot actual = basket.captureBasket();
        assertEquals(expected.events().size(), basket.getDisplayedEventCount(), "Başlıktaki maç sayısı farklı");
        assertEquals(expected.events(), actual.events(), "Etkinlik veya tarih bilgileri farklı");

    }
}
