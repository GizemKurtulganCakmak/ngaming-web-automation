package com.gizemcakmak.automation.steps;

import com.gizemcakmak.automation.components.HeaderComponent;
import com.gizemcakmak.automation.core.DriverFactory;

import com.gizemcakmak.automation.pages.BasePage;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import java.net.URI;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class NavigationStep
{
    @When("kullanıcı İddaa sayfasına gider")
    public void iddaa() { new HeaderComponent().goToIddaa();  }
    @When("kullanıcı Canlı Sonuçlar sayfasına gider")
    public void scores() { new HeaderComponent().goToLiveScores(); }
    @When("kullanıcı Dakika Bahis sayfasına gider")
    public void minute() { new HeaderComponent().goToMinuteBet(); }
    @Then("açılan sayfanın yolu {string} olmalıdır")
    public void path(String expected) {
        assertEquals(expected, URI.create(DriverFactory.getDriver().getCurrentUrl()).getPath());
    }
}
