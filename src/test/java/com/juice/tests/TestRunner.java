package com.juice.tests;

import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

/**
 * Runner principal. Ejecuta todos los .feature bajo src/test/resources/features
 * usando el glue com.juice (steps + hooks) y publica los resultados en:
 *  - pretty / summary por consola
 *  - Cucumber HTML/JSON/JUnit XML en target/cucumber-reports
 *  - Allure results en target/allure-results (via el plugin AllureCucumber7Jvm)
 */
@CucumberOptions(
    features = "src/test/resources/features",
    glue = "com.juice",
    plugin = {
        "pretty",
        "summary",
        "html:target/cucumber-reports/cucumber.html",
        "json:target/cucumber-reports/cucumber.json",
        "junit:target/cucumber-reports/cucumber.xml",
        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
    },
    monochrome = true,
    publish = false,
    dryRun = false,
    tags = "@regression"
)
public class TestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
