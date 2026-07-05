package com.juice.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.juice.log.LogManager;

import io.qameta.allure.Attachment;

/**
 * Utilidad para capturar screenshots. Se usa desde los Hooks de Cucumber
 * cuando un escenario falla (ver Hooks#tearDown, anotado con @After).
 *
 * La anotacion @Attachment (AspectJ weaving via allure-java-commons) adjunta
 * automaticamente el array de bytes retornado al reporte de Allure como imagen.
 * Adicionalmente la imagen se guarda en /screenShots del proyecto.
 */
public final class ScreenshotUtils {

    private static final Logger log = LogManager.getLogger(ScreenshotUtils.class);
    private static final DateTimeFormatter TS = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    private ScreenshotUtils() {
    }

    /**
     * Toma un screenshot, lo adjunta al reporte de Allure via @Attachment y lo guarda en disco.
     *
     * @param driver       instancia activa del WebDriver
     * @param scenarioName nombre usado para el archivo (se sanea de caracteres invalidos)
     * @return bytes de la imagen capturada (adjuntados automaticamente por @Attachment)
     */
    @Attachment(value = "Screenshot on Failure", type = "image/png", fileExtension = ".png")
    public static byte[] takeScreenshot(WebDriver driver, String scenarioName) {
        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

        // Guardar en la carpeta screenShots/ del proyecto
        try {
            String safeName = scenarioName.replaceAll("[^a-zA-Z0-9_-]", "_");
            String fileName = safeName + "_" + LocalDateTime.now().format(TS) + ".png";
            Path folder = Path.of(ConfigReader.getScreenshotPath());
            Files.createDirectories(folder);
            Path destination = folder.resolve(fileName);
            Files.write(destination, screenshot);
            log.info("Screenshot guardado en {}", destination.toAbsolutePath());
        } catch (IOException e) {
            log.error("No se pudo guardar el screenshot en disco", e);
        }

        return screenshot;
    }
}
