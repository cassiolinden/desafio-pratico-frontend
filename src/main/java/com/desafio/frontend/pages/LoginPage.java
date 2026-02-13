package com.desafio.frontend.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class LoginPage extends BasePage {

    private final By username = By.id("username");
    private final By password = By.id("password");
    private final By loginBtn = By.cssSelector("button[type='submit']");
    private final By flash = By.id("flash");

    @Step("Abrir página de Login")
    public LoginPage open() {
        visit("/login");
        return this;
    }

    @Step("Autenticar como '{user}'")
    public SecureAreaPage loginAs(String user, String pass) {
        type(username, user);
        type(password, pass);
        click(loginBtn);
        return new SecureAreaPage();
    }

    public String flashMessage() {
        return find(flash).getText();
    }
}
