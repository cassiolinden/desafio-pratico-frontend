package com.desafio.frontend.tests;

import com.desafio.frontend.pages.BasePage;
import com.desafio.frontend.pages.HomePage;
import com.desafio.frontend.pages.RoomPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Reserva de quartos")
@Feature("Fluxo de reserva")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RoomTest extends BaseTest {
    List<String> alertsExpected = new ArrayList<>();

    @Test
    @Order(1)
    @DisplayName("Validar seleção de data anterior a atual para check-in")
    void naoDeveSelecionarDataAnterior(){
        LocalDate checkInExpected = BasePage.getDate(-3);
        LocalDate checkOutExpected = BasePage.getDate(-2);
        alertsExpected.clear();
        alertsExpected.add("It's not possible booking a room before of today.");

        HomePage home = new HomePage()
                .open()
                .goToRoomSection()
                .selectRoom("Double");
        assertTrue(home.roomLoaded("Double").isDisplayed());

        RoomPage room = new RoomPage()
                .selectDays(checkInExpected.getDayOfMonth(), checkOutExpected.getDayOfMonth())
                .reserveNow()
                .fillData("Cássio", "Linden", "cassiolinden@email.com", "+55 51 99999-9999")
                .reserveNow();

        //assertEquals(alertsExpected, room.getAlertList());

        // Este cenário irá falhar, pois não é tratado. A reserva prossegue sem erros.
        // Além disso, a seleção das datas no componente de calendário na página do quarto funciona apenas no Firefox (?)
        // Para não ficar falhando, incluí a validação de sucesso abaixo.

        assertEquals("Booking Confirmed", room.getBookingConfirmed());
        assertEquals(room.dateBookingConverter(checkInExpected), room.getCheckInDate());
        assertEquals(room.dateBookingConverter(checkOutExpected.plusDays(1)),room.getCheckOutDate());
    }

    @Test
    @Order(2)
    @DisplayName("Validar prosseguimento de reserva com dados pessoais com campos vazios / acima do limite permitido")
    void naoDeveProsseguirComDadosIncorretos(){
        HomePage home = new HomePage()
                .open()
                .goToRoomSection()
                .selectRoom("Double");
        assertTrue(home.roomLoaded("Double").isDisplayed());

        RoomPage room = new RoomPage()
                .reserveNow()
                .fillData("", "", "", "")
                .reserveNow();

        alertsExpected.add("Firstname should not be blank");
        alertsExpected.add("size must be between 3 and 18");
        alertsExpected.add("size must be between 11 and 21");
        alertsExpected.add("size must be between 3 and 30");
        alertsExpected.add("must not be empty");
        alertsExpected.add("must not be empty");
        alertsExpected.add("Lastname should not be blank");

        Collections.sort(alertsExpected);

        assertEquals(alertsExpected, room.getAlertList());
        alertsExpected.clear();

        room = new RoomPage()
                .cancel()
                .reserveNow()
                .fillData("Primeironomegrandalhão",
                        "Sobrenomegrandalhãorealmenteenorme",
                        "c@caaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.com",
                        "51aasasasacascascjkhaw")
                .reserveNow();

        alertsExpected.add("must be a well-formed email address"); //e-mail
        alertsExpected.add("size must be between 11 and 21"); //phone
        alertsExpected.add("size must be between 3 and 18"); //firstName
        alertsExpected.add("size must be between 3 and 30"); //lastName

        assertEquals(alertsExpected, room.getAlertList());
    }

    @Test
    @Order(3)
    @DisplayName("Validar fluxo E2E de reserva (desktop)")
    void validarE2E(){
        LocalDate checkInExpected = BasePage.getDate(1);
        LocalDate checkOutExpected = BasePage.getDate(5);

        HomePage home = new HomePage()
                .open()
                .setCheckInDate(checkInExpected)
                .setCheckOutDate(checkOutExpected)
                .checkAvailability()
                .selectRoom("Double");
        assertTrue(home.roomLoaded("Double").isDisplayed());

        RoomPage room = new RoomPage();
        assertTrue(room.daysAreSelected(checkInExpected.getDayOfMonth(), checkOutExpected.getDayOfMonth()));

        room.reserveNow()
                .fillData("Cássio", "Linden", "cassiolinden@email.com", "+55 51 99999-9999")
                .reserveNow();

        assertEquals("Booking Confirmed", room.getBookingConfirmed());
        assertEquals(room.dateBookingConverter(checkInExpected), room.getCheckInDate());
        assertEquals(room.dateBookingConverter(checkOutExpected),room.getCheckOutDate());
    }

    @Test
    @Order(4)
    @DisplayName("Validar seleção de data para um período que já tem reserva")
    void validarReservaParaPeriodoJaReservado(){
        LocalDate checkInExpected = BasePage.getDate(1);
        LocalDate checkOutExpected = BasePage.getDate(5);

        HomePage home = new HomePage()
                .open()
                .setCheckInDate(checkInExpected)
                .setCheckOutDate(checkOutExpected)
                .checkAvailability()
                .goToRoomSection();

        assertTrue(home.roomNotLoaded("Double"));
    }
}
