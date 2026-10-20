package com.gizemcakmak.automation.steps;

import com.gizemcakmak.automation.pages.PopularCouponsPage;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FreeScenarioSteps {

    @When("kullanıcı {string} filtresini seçer")
    public void selectFilter(String name) {

        if (!"2 Maç".equals(name)) {
            throw new IllegalArgumentException(
                    "Desteklenmeyen filtre: " + name
            );
        }

        new PopularCouponsPage().selectTwoMatchFilter();
    }

    @Then("listelenen kuponların her biri 2 etkinlik içermelidir")
    public void verifyTwoMatches() {

        assertTrue(
                new PopularCouponsPage()
                        .allVisibleCouponsHaveTwoEvents(),
                "Bütün kuponlar 2 etkinlik içermiyor"
        );
    }

    @When("kullanıcı {string} sekmesine geçer")
    public void switchTab(String name) {

        PopularCouponsPage page =
                new PopularCouponsPage();

        switch (name) {
            case "Kazananlar" ->
                    page.goToWinnersTab();

            case "Hemen Oyna" ->
                    page.goToPlayNowTab();

            default ->
                    throw new IllegalArgumentException(
                            "Desteklenmeyen sekme: " + name
                    );
        }
    }

    @Then("{string} sekmesi aktif olmalıdır")
    public void verifyTab(String name) {

        PopularCouponsPage page =
                new PopularCouponsPage();

        boolean active = switch (name) {
            case "Kazananlar" ->
                    page.isWinnersTabActive();

            case "Hemen Oyna" ->
                    page.isPlayNowTabActive();

            default ->
                    throw new IllegalArgumentException(
                            "Desteklenmeyen sekme: " + name
                    );
        };

        assertTrue(
                active,
                "Sekme aktif değil: " + name
        );
    }
}