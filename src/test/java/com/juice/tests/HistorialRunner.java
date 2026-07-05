package com.juice.tests;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
/** Runner específico para ejecutar solo la historia con tag @historial. */
@CucumberOptions(
    features = "src/test/resources/features",
    glue = "com.juice",
    plugin = { "pretty", "summary", "html:target/cucumber-reports/historial.html", "json:target/cucumber-reports/historial.json", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm" },
    monochrome = true,
    publish = false,
    tags = "@historial"
)
public class HistorialRunner extends AbstractTestNGCucumberTests {
}