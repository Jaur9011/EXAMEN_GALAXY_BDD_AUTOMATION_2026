package com.juice.tests;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.juice.factory.DriverFactory;
import com.juice.log.LogManager;
import com.juice.pages.HomePage;
import com.juice.pages.OrderHistoryPage;
import com.juice.pages.SearchResultsPage;
import com.juice.utils.ScreenshotUtils;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;

/** Steps de la Historia de Usuario "Historial de ordenes" (order_history.feature). */
public class OrderHistorySteps {

    private static final Logger log = LogManager.getLogger(OrderHistorySteps.class);

    @Dado("que un usuario registrado completo 2 pedidos usando la segunda direccion y el primer metodo de pago")
    public void usuario_completo_2_pedidos() {
        ShoppingSteps shoppingSteps = new ShoppingSteps();

        // Deja al usuario logueado, con 2 direcciones y 2 metodos de pago guardados
        shoppingSteps.prepararUsuarioConDireccionesYPagos();

        // Pedido 1: busqueda de manzana / banana / camiseta
        HomePage homePage = new HomePage(DriverFactory.getDriver());
        SearchResultsPage resultsPage = new SearchResultsPage(DriverFactory.getDriver());

        homePage.searchProduct("apple");
        resultsPage.addFirstResultToCart();
        homePage.searchProduct("banana");
        resultsPage.addFirstResultToCart();
        homePage.searchProduct("shirt");
        resultsPage.addFirstResultToCart();
        shoppingSteps.completarPedidoConSegundaDireccionYPrimerPago();
        log.info("Pedido 1 completado");

        // Pedido 2: 2 productos aleatorios del catalogo
        homePage.open();
        resultsPage.addRandomProductToCart();
        resultsPage.addRandomProductToCart();
        shoppingSteps.completarPedidoConSegundaDireccionYPrimerPago();
        log.info("Pedido 2 completado");
    }

    @Cuando("el usuario abre su historial de pedidos")
    public void el_usuario_abre_su_historial_de_pedidos() {
        new OrderHistoryPage(DriverFactory.getDriver()).open();
    }

    @Entonces("el historial muestra al menos 2 pedidos completados")
    public void el_historial_muestra_al_menos_2_pedidos() {
        OrderHistoryPage orderHistoryPage = new OrderHistoryPage(DriverFactory.getDriver());
        int count = orderHistoryPage.waitForOrders(2);
        Assert.assertTrue(count >= 2, "Se esperaban al menos 2 pedidos en el historial, se encontraron " + count);
        log.info("El historial muestra {} pedidos", count);
    }

    @Entonces("se captura evidencia screenshot de cada uno de los 2 pedidos")
    public void se_captura_evidencia_de_cada_pedido() {
        OrderHistoryPage orderHistoryPage = new OrderHistoryPage(DriverFactory.getDriver());
        for (int i = 1; i <= 2; i++) {
            WebElement orderBlock = orderHistoryPage.getOrderBlock(i);
            ((org.openqa.selenium.JavascriptExecutor) DriverFactory.getDriver())
                    .executeScript("arguments[0].scrollIntoView({block:'center'});", orderBlock);
            ScreenshotUtils.takeScreenshot(DriverFactory.getDriver(), "historial_pedido_" + i);
        }
    }
}
