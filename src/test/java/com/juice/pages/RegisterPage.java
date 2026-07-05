package com.juice.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Pagina de registro de usuario (#/register).
 * Locators verificados contra la instancia real (localhost:3000, es-ES).
 */
public class RegisterPage extends BasePage {

    @FindBy(id = "emailControl")
    private WebElement emailInput;

    @FindBy(id = "passwordControl")
    private WebElement passwordInput;

    @FindBy(id = "repeatPasswordControl")
    private WebElement repeatPasswordInput;

    @FindBy(css = "mat-select[aria-label*='pregunta de seguridad'], mat-select[aria-label*='security question']")
    private WebElement securityQuestionDropdown;

    @FindBy(id = "securityAnswerControl")
    private WebElement securityAnswerInput;

    @FindBy(id = "registerButton")
    private WebElement registerButton;

    @FindBy(css = ".error, .mat-mdc-snack-bar-label")
    private WebElement errorMessage;

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    public void register(String email, String password, String securityAnswer) {
        type(emailInput, email);
        type(passwordInput, password);
        type(repeatPasswordInput, password);

        click(securityQuestionDropdown);
        // Selecciona la primera opcion disponible del listado desplegado por Angular Material
        WebElement firstOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("mat-option")));
        firstOption.click();

        type(securityAnswerInput, securityAnswer);
        click(registerButton);
        log.info("Registro enviado para el usuario: {}", email);
    }

    public boolean isRegisterButtonEnabled() {
        return registerButton.isEnabled();
    }
}
