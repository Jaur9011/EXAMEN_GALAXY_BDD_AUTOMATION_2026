package com.juice.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.juice.utils.ConfigReader;

/**
 * Pagina principal / catalogo de productos. Tambien centraliza el cierre
 * de los overlays que Juice Shop muestra al primer ingreso (dialogo de
 * bienvenida y banner de cookies), ya que interfieren con cualquier otra
 * interaccion si no se cierran primero.
 */
public class HomePage extends BasePage {

    // #navbarSearch es estable; los contains cubren distintas traducciones del aria-label
    @FindBy(xpath = "//*[@id='navbarSearch' or (self::button and (contains(@aria-label,'earch') or contains(@aria-label,'squeda')))]")
    private WebElement searchIcon;

    @FindBy(css = "#searchQuery input")
    private WebElement searchInput;

    @FindBy(css = "button[aria-label='Mostrar/ocultar menú de cuenta'], button#navbarAccount")
    private WebElement accountMenuButton;

    @FindBy(css = ".mat-mdc-menu-panel a[routerlink='/basket'], a[href='#/basket']")
    private WebElement basketLink;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage open() {
        driver.get(ConfigReader.getAppUrl());
        dismissPopups();
        return this;
    }

    /**
     * Cierra el dialogo de bienvenida y el banner de cookies si aparecen.
     * Se usa un WebDriverWait corto y se ignoran los timeouts porque estos
     * overlays no siempre se muestran (por ejemplo, en sesiones ya usadas).
     */
    public void dismissPopups() {
        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(4));
        try {
            WebElement welcomeClose = shortWait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("button.close-dialog, button[aria-label='Close Welcome Banner']")));
            welcomeClose.click();
            log.info("Dialogo de bienvenida cerrado");
        } catch (Exception e) {
            log.debug("No aparecio el dialogo de bienvenida");
        }
        try {
            List<WebElement> cookieBtn = driver.findElements(
                    By.cssSelector("button[aria-label='descartar mensaje de cookies'], .cc-dismiss, #cookieconsent button"));
            if (!cookieBtn.isEmpty()) {
                cookieBtn.get(0).click();
                log.info("Banner de cookies cerrado");
            }
        } catch (Exception e) {
            log.debug("No aparecio el banner de cookies");
        }
    }

    public void searchProduct(String term) {
        // Si el input ya es visible (busqueda previa abierta), lo usamos directamente.
        // Si no, hacemos click en el icono toggle y esperamos explicitamente a que
        // el panel de busqueda se expanda antes de intentar escribir.
        // Esto evita el TimeoutException en la 2da/3ra busqueda consecutiva, cuando
        // Angular puede dejar el input en DOM-pero-invisible tras una transicion de pagina.
        By searchInputLocator = By.cssSelector("#searchQuery input");

        boolean searchAlreadyOpen = driver.findElements(searchInputLocator)
                .stream().anyMatch(WebElement::isDisplayed);

        if (!searchAlreadyOpen) {
            click(searchIcon);
            // Espera explicita hasta que el input sea visible tras el click en el toggle
            wait.until(ExpectedConditions.visibilityOfElementLocated(searchInputLocator));
        }

        type(searchInput, term);
        searchInput.sendKeys(org.openqa.selenium.Keys.ENTER);
        log.info("Busqueda de producto: {}", term);
    }

    public void goToRegister() {
        driver.get(ConfigReader.getAppUrl() + "/#/register");
        dismissPopups();
    }

    public void goToLogin() {
        driver.get(ConfigReader.getAppUrl() + "/#/login");
        dismissPopups();
    }

    public void goToBasket() {
        driver.get(ConfigReader.getAppUrl() + "/#/basket");
        dismissPopups();
    }
}
