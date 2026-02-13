package com.desafio.frontend.pages;

import org.openqa.selenium.By;

public class SecureAreaPage extends BasePage {

    private final By header = By.tagName("h2");
    private final By flash = By.id("flash");

    public String headerText() {
        return find(header).getText();
    }

    public String flashMessage() {
        return find(flash).getText();
    }
}
