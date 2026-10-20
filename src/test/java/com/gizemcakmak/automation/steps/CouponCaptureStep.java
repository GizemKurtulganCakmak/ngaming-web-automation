package com.gizemcakmak.automation.steps;

import com.gizemcakmak.automation.context.ScenarioContext;
import com.gizemcakmak.automation.model.CouponSnapshot;
import com.gizemcakmak.automation.pages.BasketPage;
import com.gizemcakmak.automation.pages.PopularCouponsPage;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import static org.junit.jupiter.api.Assertions.*;


public class CouponCaptureStep
{
    private final ScenarioContext context;
    public CouponCaptureStep(ScenarioContext context) { this.context = context; }
    @When("ilk uygun kuponun bilgileri kaydedilir")
    public void capture() {
        PopularCouponsPage page = new PopularCouponsPage();

        String id = page.getFirstAvailableCouponId();
        CouponSnapshot coupon = page.captureCoupon(id);
        context.setSelectedCouponId(id);
        context.setSelectedCoupon(coupon);
    }
    @Then("seçilen kuponun etkinlik bilgileri dolu olmalıdır")
    public void checkCapture() {
        assertFalse(context.getSelectedCoupon().events().isEmpty());
        context.getSelectedCoupon().events().forEach(event -> {
            assertFalse(event.name().isBlank());
            assertFalse(event.dateTime().isBlank());
        });
    }
    @When("seçilen kupon sepete eklenir")
    public void addToBasket() {
        new PopularCouponsPage().addSelectedCouponToBasket(context.getSelectedCouponId());
        new BasketPage().waitForExpectedEventCount(context.getSelectedCoupon().events().size());
    }
}
