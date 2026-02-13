package com.desafio.frontend.pages;

import com.desafio.frontend.core.Config;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import org.openqa.selenium.*;

import java.time.LocalDate;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class HomePage extends BasePage {

    // ======= Locators =======
    // Navbar
    private final By navBar = By.cssSelector("nav");
    private final By adminLink = By.cssSelector("a[href='/admin']");
    private final By contactMenuLink = By.xpath("//nav//a[normalize-space()='Contact']");

    // Seção "Send Us a Message" (Contact)
    private final By contactSectionTitle = By.xpath("//section//h3[contains(.,'Send Us a Message')]");
    private final By postContactSectionTitle = By.xpath("//section//h3[contains(.,'Thanks for getting in touch')]");
    private final By nameInput = By.xpath("//*[@data-testid='ContactName']");
    private final By emailInput = By.xpath("//*[@data-testid='ContactEmail']");
    private final By phoneInput = By.xpath("//*[@data-testid='ContactPhone']");
    private final By subjectInput = By.xpath("//*[@data-testid='ContactSubject']");
    private final By messageText = By.xpath("//*[@data-testid='ContactDescription']");
    private final By submitButton = By.xpath("//button[normalize-space()='Submit']");

    // Cards de quartos (ex.: Single, Double, Suite)
    private final By roomCards = By.cssSelector("[class*='row'] .col-lg-4 [class*='room-card'] .card-body"); //original: [class*='row'] .col-sm-4, [class*='rooms'] .card
    private final By bookNowButton = By.xpath("//a[contains(.,'Book Now')]");

    // Check Availability & Book Your Stay
    private final By checkInDate = By.xpath("//label[@for='checkin']/..//input");
    private final By checkOutDate = By.xpath("//label[@for='checkout']/..//input");
    private final By date = By.xpath("//input[@class='form-control react-datepicker-ignore-onclickoutside']");
    private final By checkAvailabilityButton = By.xpath("//button[contains(.,'Check Availability')]");

    // ======= Garantia de carregamento da Home =======
    private void ensureLoaded() {
        wait.until(visibilityOfElementLocated(navBar));
        scrollIntoView(contactSectionTitle);
        wait.until(visibilityOfElementLocated(contactSectionTitle));
    }

    // ======= Ações de alto nível =======

    @Step("Abrir home")
    @Severity(SeverityLevel.CRITICAL)
    public HomePage open() {
        visit("");
        return this;
    }

    public HomePage goToAdmin() {
        waitAndClick(adminLink);
        return this;
    }

    @Step("Preencher data de check-in")
    public HomePage setCheckInDate(LocalDate date){
        String ds = dateCalendarConverter(date);
        for (int i = 0; i <= ds.length(); i++)
            find(checkInDate).sendKeys(Keys.BACK_SPACE);
        type(checkInDate, ds);
        return this;
    }

    @Step("Preencher data de check-out")
    public HomePage setCheckOutDate(LocalDate date){
        String ds = dateCalendarConverter(date);
        for (int i = 0; i <= ds.length(); i++)
            find(checkOutDate).sendKeys(Keys.BACK_SPACE);
        type(checkOutDate, ds);
        return this;
    }

    @Step("Verificar disponibilidade")
    public HomePage checkAvailability(){
        click(checkAvailabilityButton);
        scrollIntoView(By.xpath("//h2[contains(.,'Our Location')]"));
        find(roomCards);/*
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*/
        return this;
    }

    @Step("Ir à seção de quartos")
    public HomePage goToRoomSection(){
        click(bookNowButton);
        if(Config.browser().equals("chrome")) scrollIntoView(roomCards);
        return this;
    }

    @Step("Selecionar quarto")
    public HomePage selectRoom(String room){
        click(By.xpath("//h5[contains(., '"+room+"')]/../..//a[contains(., 'Book now')]"));
        return this;
    }

    public WebElement roomLoaded(String room){
        return wait.until(visibilityOfElementLocated(By.xpath("//h1[contains(., '"+room+"')]")));
    }

    public boolean roomNotLoaded(String room){
        return elementNotFound(By.xpath("//h5[contains(., '"+room+"')]"));
    }

    // =============

    @Step("Ir à seção de Contato")
    @Severity(SeverityLevel.NORMAL)
    public HomePage goToContactSection() {
        wait.until(visibilityOfElementLocated(contactMenuLink));
        waitAndClick(contactMenuLink);
        wait.until(visibilityOfElementLocated(contactSectionTitle));
        return this;
    }

    @Step("Preencher formulário de contato")
    @Severity(SeverityLevel.NORMAL)
    public HomePage fillContactForm(String name, String email, String phone, String subject, String message) {
        type(nameInput, name);
        type(emailInput, email);
        type(phoneInput, phone);
        type(subjectInput, subject);
        type(messageText, message);
        return this;
    }

    @Step("Enviar contato")
    @Severity(SeverityLevel.NORMAL)
    public HomePage submitContact() {
        if(Config.browser().equals("chrome")) scrollIntoView(submitButton);
        waitAndClick(submitButton);
        return this;
    }

    public boolean postContact(){
        wait.until(visibilityOfElementLocated(postContactSectionTitle));
        return true;
    }
}