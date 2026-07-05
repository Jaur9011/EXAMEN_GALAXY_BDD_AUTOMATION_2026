package com.juice.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.juice.utils.ConfigReader;

import io.qameta.allure.Step;

/**
 * Pagina "Mis direcciones guardadas" (#/address/saved) y su formulario
 * de creacion (#/address/create).
 *
 * Nota sobre locators: Angular Material no deja ids fijos en varios de estos
 * campos (mat-input-N autogenerado), por lo que se localizan de forma
 * relativa a su mat-label, que si es estable.
 */
public class AddressPage extends BasePage {

    @FindBy(id = "address")
    private WebElement addressLine;

    @FindBy(id = "submitButton")
    private WebElement submitButton;

    public AddressPage(WebDriver driver) {
        super(driver);
    }

    public AddressPage openSavedAddresses() {
        driver.get(ConfigReader.getAppUrl() + "/#/address/saved");
        return this;
    }

    public AddressPage openNewAddressForm() {
        driver.get(ConfigReader.getAppUrl() + "/#/address/create");
        wait.until(ExpectedConditions.urlContains("/address/create"));
        waitVisible(submitButton);
        return this;
    }

    public void clickAddNewAddress() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(@aria-label,'nueva dirección') or contains(.,'Agregar nueva dirección') or contains(.,'Add new address')]")));
        btn.click();
    }

    private WebElement fieldByLabel(String... labelTexts) {
        StringBuilder xpath = new StringBuilder();
        for (String label : labelTexts) {
            if (xpath.length() > 0) xpath.append(" | ");
            xpath.append("//mat-form-field[.//mat-label[contains(., '").append(label).append("')]]//input")
                 .append(" | ")
                 .append("//mat-form-field[.//mat-label[contains(., '").append(label).append("')]]//textarea");
        }
        return wait.until(d -> {
            try { return d.findElement(By.xpath(xpath.toString())); }
            catch (org.openqa.selenium.NoSuchElementException e) { return null; }
        });
    }

    @Step("Agregar direccion: {country}, {city}, {state}")
    public void addAddress(String country, String name, String mobile, String zip,
                            String address, String city, String state) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".cdk-overlay-backdrop")));
        type(fieldByLabel("País", "Country"), country);
        type(fieldByLabel("Nombre", "Name"), name);
        type(fieldByLabel("Número de teléfono móvil", "Mobile Number"), mobile);
        type(fieldByLabel("Código postal", "ZIP Code"), zip);
        type(addressLine, address);
        type(fieldByLabel("Ciudad", "City"), city);
        type(fieldByLabel("Estado", "State"), state);
        wait.until(d -> submitButton.isDisplayed() && submitButton.isEnabled());
        click(submitButton);
        // Esperar a que Juice Shop navegue fuera del formulario (confirma que el POST fue enviado)
        wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("/address/create")));
        log.info("Direccion agregada: {}, {}", address, city);
    }

    public int getSavedAddressCount() {
        // Esperar presencia de mat-table (la clase .address-table puede no existir en esta pagina)
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("mat-table, .address-table")));
        return driver.findElements(By.cssSelector("mat-table mat-row, .address-table mat-row")).size();
    }
}
