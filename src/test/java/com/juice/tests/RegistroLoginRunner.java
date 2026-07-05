package com.juice.tests;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

/** Suite parcial: Historia de usuario "Registro" + "Inicio de sesion" (usada por testng2.xml). */
@CucumberOptions(
    features = "src/test/resources/features",
    glue = "com.juice",
    plugin = {
        "pretty", "summary",
        "html:target/cucumber-reports/registro-login.html",
        "json:target/cucumber-reports/registro-login.json",
        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
    },
    monochrome = true,
    publish = false,
    tags = "@registro or @login"
)
public class RegistroLoginRunner extends AbstractTestNGCucumberTests {
}
