package com.desafio.frontend.tests;

import com.desafio.frontend.core.AutoScreenshotExtension;
import com.desafio.frontend.core.DriverFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.RegisterExtension;

public class BaseTest {
    @RegisterExtension
    AutoScreenshotExtension screenshotManager = new AutoScreenshotExtension(
            c -> DriverFactory.getDriver());

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
