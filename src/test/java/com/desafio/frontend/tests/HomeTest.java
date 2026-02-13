package com.desafio.frontend.tests;

import com.desafio.frontend.pages.HomePage;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Reserva de quartos")
@Feature("Tela inicial")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HomeTest extends BaseTest {

    @Test
    @DisplayName("Validar sucesso no envio de mensagem")
    void deveEnviarMensagemDeContato() {
        HomePage home = new HomePage()
                .open()
                .goToContactSection()
                .fillContactForm(
                        "Cássio",
                        "cassiolinden@email.com",
                        "+55 51 99999-9999",
                        "Dúvida sobre reservas",
                        "Olá, esta é uma mensagem de teste automatizado.")
                .submitContact();
        assertTrue(home.postContact());
    }

    @Test
    @DisplayName("Validar redirecionamento para quarto escolhido")
    void deveRedirecionarParaQuartoEscolhido(){
        HomePage home = new HomePage()
                .open()
                .goToRoomSection()
                .selectRoom("Single");
        assertTrue(home.roomLoaded("Single").isDisplayed());
    }
}