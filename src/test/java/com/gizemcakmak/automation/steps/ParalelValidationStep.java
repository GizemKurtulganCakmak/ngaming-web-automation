package com.gizemcakmak.automation.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import static org.junit.jupiter.api.Assertions.*;

public class ParalelValidationStep
{
    private String scenarioId;
    private long threadId;
    @Given("paralel test senaryosu {string} başlar")
    public void start(String id)
    {
        scenarioId = id; threadId = Thread.currentThread().threadId();
        System.out.println("START " + id + " | Thread: " + threadId);
    }
    @Then("paralel test senaryosu {string} tamamlanır")
    public void finish(String id)
    {
        assertEquals(scenarioId, id);
        assertEquals(threadId, Thread.currentThread().threadId());
        System.out.println("END " + id + " | Thread: " + threadId);
    }
}
