package com.juice.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import com.juice.utils.ConfigReader;

import io.qameta.allure.Step;

/**
 * Pagina "Mis opciones de pago" (#/saved-payment-methods).
 * El mes y el año de vencimiento se renderizan como &lt;select&gt; HTML
 * nativos (no mat-select), por lo que se manejan con la clase Select de Selenium.
 */
public class PaymentPage extends BasePage {

    @FindBy(css = "mat-expansion-panel-header")
    private WebElement addNewCardHeader;

    @FindBy(xpath = "//mat-form-field[.//mat-label[contains(., 'Nombre') or contains(., 'Name')]]//input")
    private WebElement nameInput;

    @FindBy(xpath = "//mat-form-field[.//mat-label[contains(., 'Número de tarjeta') or contains(., 'Card Number')]]//input")
    private WebElement cardNumberInput;

    @FindBy(css = ".mat-expansion-panel-body select")
    private List<WebElement> selects; // [0] = mes de vencimiento, [1] = año de caducidad

    @FindBy(id = "submitButton")
    private WebElement submitButton;

    public PaymentPage(WebDriver driver) {
        super(driver);
    }

    @Step("Abrir pagina de metodos de pago")
    public PaymentPage open() {
        driver.get(ConfigReader.getAppUrl() + "/#/saved-payment-methods");
        // Esperar a que el panel de expansion este listo
        wait.until(ExpectedConditions.elementToBeClickable(addNewCardHeader));
        return this;
    }

    @Step("Agregar tarjeta: titular={holderName}, terminada en {cardNumber}")
    public void addCard(String holderName, String cardNumber, String month, String year) {
        int countBefore = getSavedCardCount();

        // Verificar si el panel ya esta expandido antes de hacer click.
        // Juice Shop puede mostrar el panel abierto por defecto (ej: cuando no hay tarjetas guardadas)
        // o cerrado (cuando ya existe al menos una). Hacer click en un panel ya abierto lo cerraria.
        List<WebElement> panelBodies = driver.findElements(By.cssSelector(".mat-expansion-panel-body"));
        boolean panelAlreadyOpen = !panelBodies.isEmpty() && panelBodies.get(0).isDisplayed();

        if (!panelAlreadyOpen) {
            // Panel esta cerrado: usar JS click para expandirlo sin interferencia de overlays
            WebElement header = wait.until(ExpectedConditions.elementToBeClickable(addNewCardHeader));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", header);
            // Esperar a que el cuerpo del panel se vuelva visible (la clase es CSS, no elemento HTML)
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(".mat-expansion-panel-body")));
        }

        type(nameInput, holderName);
        type(cardNumberInput, cardNumber);

        wait.until(ExpectedConditions.visibilityOfAllElements(selects));
        new Select(selects.get(0)).selectByValue(month);
        new Select(selects.get(1)).selectByValue(year);

        click(submitButton);
        // Esperar a que la nueva tarjeta aparezca en la lista (confirma guardado en la BD)
        wait.until(d -> getSavedCardCount() > countBefore);
        log.info("Tarjeta agregada terminada en: {}", cardNumber.substring(cardNumber.length() - 4));
    }

    public int getSavedCardCount() {
        return driver.findElements(By.cssSelector("mat-table mat-row")).size();
    }
}
