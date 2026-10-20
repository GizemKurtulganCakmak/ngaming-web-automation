package com.gizemcakmak.automation.steps;

import com.gizemcakmak.automation.pages.HomePage;
import com.gizemcakmak.automation.pages.PopularCouponsPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PopularCouponsStep
{
    @Given("kullanıcı ana sayfadadır")
    public void openHome() { new HomePage().open();}
    @When("Popüler Kuponlar menüsüne tıklar")
    public void openPopular() { new HomePage().goToPopularCoupons();}
    @Then("Popüler Kuponlar sayfası açılmalıdır")
    public void verifyPopular() { assertTrue(new PopularCouponsPage().isOpened()); }
    @Then("listede en az bir kupon bulunmalıdır")
    public void verifyCoupons() { assertTrue(new PopularCouponsPage().getCouponCount() > 0); }
}
