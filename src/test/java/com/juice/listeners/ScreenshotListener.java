package com.juice.listeners;

import org.apache.logging.log4j.Logger;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.juice.log.LogManager;

/**
 * Listener TestNG de nivel de suite: complementa el hook de Cucumber
 * (com.juice.tests.Hooks) dejando trazabilidad en el log de arranque/cierre
 * de cada metodo de test y del resultado final (paso/fallo).
 *
 * Se registra en los testng*.xml con &lt;listeners&gt;.
 */
public class ScreenshotListener implements ITestListener {

    private static final Logger log = LogManager.getLogger(ScreenshotListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        log.info(">>> Iniciando: {}", result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("<<< EXITO: {}", result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        log.error("<<< FALLO: {} - {}", result.getMethod().getMethodName(), result.getThrowable());
        // La captura de pantalla real se realiza en com.juice.tests.Hooks (@After),
        // porque alli se tiene acceso directo al WebDriver del escenario de Cucumber.
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log.warn("<<< OMITIDO: {}", result.getMethod().getMethodName());
    }
}
