package com.juice.tests;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

/** Suite parcial: Historia de usuario "Agregar Direccion" + "Metodos de Pago" (usada por testng3.xml). */
@CucumberOptions(
    features = "src/test/resources/features",
    glue = "com.juice",
    plugin = {
        "pretty", "summary",
        "html:target/cucumber-reports/direccion-pago.html",
        "json:target/cucumber-reports/direccion-pago.json",
        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
    },
    monochrome = true,
    publish = false,
    tags = "@direccion or @pago"
)
public class DireccionPagoRunner extends AbstractTestNGCucumberTests {
}
