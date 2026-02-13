package com.desafio.frontend.pages;

import com.desafio.frontend.core.Config;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RoomPage extends BasePage{
    // Locators
    private final By calendar = By.xpath("//*[@class='rbc-month-view']");
    private final By firstName = By.xpath("//input[@name='firstname']");
    private final By lastName = By.xpath("//input[@name='lastname']");
    private final By email = By.xpath("//input[@name='email']");
    private final By phone = By.xpath("//input[@name='phone']");
    private final By alert = By.xpath("//div[@role='alert']");
    private final By cancel = By.xpath("//button[contains(.,'Cancel')]");
    private final By reserveNowButton = By.xpath("//button[contains(.,'Reserve Now')]");
    protected final By bookingConfirmedFrame = By.xpath("//div//h2[contains(.,'Booking Confirmed')]");
    private final By bookingDates = By.xpath("//h2[contains(.,'Booking Confirmed')]/..//p[@class='text-center pt-2']");

    // Ações
    @Step("Selecionar dias no componente de calendário")
    @Severity(SeverityLevel.NORMAL)
    public RoomPage selectDays(int enter, int leave){
        WebElement draggable = driver.findElement(By.xpath("//*[@class='rbc-month-view']//button[contains(., '"+enter+"')]/.."));
        WebElement droppable = driver.findElement(By.xpath("//*[@class='rbc-month-view']//button[contains(., '"+leave+"')]/.."));

        new Actions(driver)
                .dragAndDrop(draggable, droppable)
                .perform();
        return this;
    }

    @Step("Clicar no botão 'Reserve Now'")
    @Severity(SeverityLevel.CRITICAL)
    public RoomPage reserveNow(){
        if(Config.browser().equals("chrome")) scrollIntoView(reserveNowButton);
        click(reserveNowButton);
        return this;
    }

    @Step("Preencher dados pessoais")
    @Severity(SeverityLevel.CRITICAL)
    public RoomPage fillData(String firstName, String lastName, String email, String phone) {
        type(this.firstName, firstName);
        type(this.lastName, lastName);
        type(this.email, email);
        type(this.phone, phone);
        return this;
    }

    @Step("Cancelar preenchimento de dados pessoais")
    @Severity(SeverityLevel.NORMAL)
    public RoomPage cancel(){
        if(Config.browser().equals("chrome")) scrollIntoView(cancel);
        click(cancel);
        return this;
    }

    // Utils

    public List<String> getAlertList(){
        List<String> list = find(alert).getText().lines().toList();
        List<String> orderedList = new ArrayList<>(list);
        Collections.sort(orderedList);
        return orderedList;
    }

    private List<String> getBookingDates(){
        String dates = find(bookingDates).getText();
        List<String> datesList = Arrays.stream(dates.split(" ")).toList();
        return datesList;
    }

    public String getCheckInDate(){
        return getBookingDates().get(0);
    }

    public String getCheckOutDate(){
        return getBookingDates().get(2);
    }

    public String getBookingConfirmed(){
        return find(bookingConfirmedFrame).getText();
    }

    public boolean daysAreSelected(int enter, int leave){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='rbc-month-view']//button[contains(., '"+enter+"')]/../../..//*[@title='Selected']")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='rbc-month-view']//button[contains(., '"+leave+"')]/../../..//*[@title='Selected']")));
        return true;
    }

}
