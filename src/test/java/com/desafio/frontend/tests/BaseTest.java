package com.desafio.frontend.tests;

import com.desafio.frontend.core.DriverFactory;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(AllureJunit5.class)
public class BaseTest {

    @BeforeEach
    void setUp() {
        // Inicializa o driver ao acessar a primeira página
        // (Driver é lazy via PageObjects)
        // Opcionalmente, poderíamos chamar DriverFactory.getDriver() aqui.
    }

    @AfterEach
    void tearDown() {
        DriverFactory.quitDriver();
    }
}
