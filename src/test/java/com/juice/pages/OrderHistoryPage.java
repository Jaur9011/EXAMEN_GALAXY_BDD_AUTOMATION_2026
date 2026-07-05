package com.juice.pages;

import java.util.List;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.juice.log.LogManager;
import com.juice.utils.ConfigReader;

/** Pagina "Historial de pedidos" (#/order-history). */
public class OrderHistoryPage extends BasePage {

    private static final Logger log = LogManager.getLogger(OrderHistoryPage.class);

    private static final By ORDERS_CONTAINER = By.cssSelector(".orders-container");
    private static final By ORDER_BLOCKS = By.cssSelector(".orders-container .border");
    private static final By CONFIRMATION_HEADER = By.cssSelector("h1.confirmation");
    // Selector fallback: en caso de que la pagina de confirmacion se quede abierta
    //private static final By CONFIRMATION_HEADER = By.xpath("//h1[@class='confirmation' and @translate]");

    public OrderHistoryPage(WebDriver driver) {
        super(driver);
    }

    public OrderHistoryPage open() {
        driver.get(ConfigReader.getAppUrl() + "/#/order-history");

        // Intentar esperar por el contenedor principal
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(ORDERS_CONTAINER));
            log.info("Página de historial de pedidos cargada exitosamente");
        } catch (TimeoutException e) {
            // Si no se encuentra .orders-container, verificar si se quedó en la página de confirmación
            log.warn("No se encontró .orders-container, verificando si está en página de confirmación");

            try {
                WebElement confirmationHeader = driver.findElement(CONFIRMATION_HEADER);
                if (confirmationHeader.isDisplayed()) {
                    log.warn("Detectada página de confirmación. Navegando manualmente al historial...");
                    // Esperar un momento y reintentar la navegación
                    Thread.sleep(1000);
                    driver.get(ConfigReader.getAppUrl() + "/#/order-history");
                    wait.until(ExpectedConditions.visibilityOfElementLocated(ORDERS_CONTAINER));
                    log.info("Página de historial recargada exitosamente tras detectar confirmación");
                }
            } catch (NoSuchElementException | InterruptedException ex) {
                log.error("No se encontró página de confirmación como fallback. Elemento .orders-container tampoco existe.");
                throw new TimeoutException("No se pudo cargar el historial de pedidos: .orders-container no encontrado y página de confirmación no detectada", e);
            }
        }

        return this;
    }

    public int getOrderCount() {
        return driver.findElements(ORDER_BLOCKS).size();
    }

    /**
     * Espera hasta que se hayan renderizado al menos minCount pedidos. El contenedor
     * ".orders-container" puede quedar visible antes de que Angular termine de pintar
     * las filas obtenidas del backend, por lo que leer getOrderCount() inmediatamente
     * despues de open() puede capturar un conteo parcial.
     */
    public int waitForOrders(int minCount) {
        try {
            return wait.until(d -> {
                int count = d.findElements(ORDER_BLOCKS).size();
                return count >= minCount ? count : null;
            });
        } catch (TimeoutException e) {
            return getOrderCount();
        }
    }

    public List<WebElement> getOrderBlocks() {
        return driver.findElements(ORDER_BLOCKS);
    }

    /** Devuelve el bloque (fila) del pedido en la posicion indicada (1 = mas reciente). */
    public WebElement getOrderBlock(int position) {
        List<WebElement> blocks = getOrderBlocks();
        if (position < 1 || position > blocks.size()) {
            throw new IllegalArgumentException("No existe el pedido en la posicion " + position);
        }
        return blocks.get(position - 1);
    }
}
