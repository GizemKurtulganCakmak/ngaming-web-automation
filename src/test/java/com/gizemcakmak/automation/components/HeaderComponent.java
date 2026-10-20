package com.gizemcakmak.automation.components;

import com.gizemcakmak.automation.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HeaderComponent extends BasePage {
    public void goToIddaa() { navigate("/iddaa"); }
    public void goToLiveScores() { navigate("/iddaa/canli-skor/futbol"); }
    public void goToMinuteBet() { navigate("/iddaa/dakika-bahis"); }
    private void navigate(String path) {

        closeTutorialIfPresent();
        click(By.cssSelector("a[href='" + path + "']"));
        closeTutorialIfPresent();
        wait.until(ExpectedConditions.urlContains(path));
    }
}
