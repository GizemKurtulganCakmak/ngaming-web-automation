package com.gizemcakmak.automation.hooks;

import com.gizemcakmak.automation.core.DriverFactory;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Hooks
{
    @Before("@browser")
    public void startBrowser() { DriverFactory.createDriver(); }
    @After("@browser")
    public void closeBrowser(Scenario scenario)
    {
        try
        {
            if (scenario.isFailed())
            {
                try
                {
                    byte[] png = ((TakesScreenshot) DriverFactory.getDriver())
                            .getScreenshotAs(OutputType.BYTES);
                    scenario.attach(png, "image/png", "Failure Screenshot");
                } catch (RuntimeException screenshotError)
                {
                    System.err.println("Ekran görüntüsü alınamadı: " + screenshotError.getMessage());
                }
            }
        }
        finally { DriverFactory.quitDriver(); }
    }
}
