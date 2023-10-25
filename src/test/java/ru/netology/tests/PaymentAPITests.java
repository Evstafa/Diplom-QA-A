package ru.netology.tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DBHelper;
import ru.netology.data.DataHelper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DBHelper.cleanDatabase;


public class PaymentAPITests {

    private static final String paymentUrl = "/api/v1/pay";

    @BeforeAll
    public static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void tearDown() {
        cleanDatabase();
    }

    @AfterAll
    public static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Epic(value = "API-тесты")
    @Feature(value = "Payment API-тесты")
    @Story(value = "Позитивный. Все данные корректны, создание записи в payment_entity, статус 200")
    @Test
    public void shouldProceedPaymentWithAllDataCorrect() {
        var cardInfo = DataHelper.getValidCardApproved();
        DBHelper.getBody(cardInfo, paymentUrl, 200);
        var payments = DBHelper.getPayments();

        assertEquals(1, payments.size());
        assertEquals("APPROVED", DBHelper.getPaymentStatus());
    }

    @Epic(value = "API-тесты")
    @Feature(value = "Payment API-тесты")
    @Story(value = "Позитивный. Все данные корректны, создание записи в order_entity, статус 200")
    @Test
    public void shouldPlacedOrderWithAllDataCorrect() {
        var cardInfo = DataHelper.getValidCardApproved();
        DBHelper.getBody(cardInfo, paymentUrl, 200);
        var orders = DBHelper.getOrders();

        assertEquals(1, orders.size());
        assertEquals("APPROVED", DBHelper.getPaymentStatus());
    }

    @Epic(value = "API-тесты")
    @Feature(value = "Payment API")
    @Story(value = "Негативный. Случайный номер карты, записи о платеже и заказе не проводятся, статус 500")
    @Test
    public void shouldNotProceedPaymentWithRandomCardNumber() {
        var cardInfo = DataHelper.getRandomCard16char();
        DBHelper.getBody(cardInfo, paymentUrl, 500);
        var payments = DBHelper.getPayments();
        var orders = DBHelper.getOrders();

        assertEquals(0, payments.size());
        assertEquals(0, orders.size());
    }

    @Epic(value = "API-тесты")
    @Feature(value = "Payment API")
    @Story(value = "Негативный. Короткий номер карты, записи о платеже и заказе не проводятся, статус 500")
    @Test
    public void shouldNotCardNumberInsufficientDigits() {
        var cardInfo = DataHelper.getRandomCard11char();
        DBHelper.getBody(cardInfo, paymentUrl, 500);
        var payments = DBHelper.getPayments();
        var orders = DBHelper.getOrders();

        assertEquals(0, payments.size());
        assertEquals(0, orders.size());
    }

    @Epic(value = "API-тесты")
    @Feature(value = "Payment API")
    @Story(value = "Негативный. Пустой номер карты, записи о платеже и заказе не проводятся, статус 500")
    @Test
    public void shouldNotEmptyCardNumber() {
        var cardInfo = DataHelper.getCardEmpty();
        DBHelper.getBody(cardInfo, paymentUrl, 500);
        var payments = DBHelper.getPayments();
        var orders = DBHelper.getOrders();

        assertEquals(0, payments.size());
        assertEquals(0, orders.size());
    }

    @Epic(value = "API-тесты")
    @Feature(value = "Payment API")
    @Story(value = "Негативный. Форма пуста, записи о платеже и заказе не проводятся, статус 500")
    @Test
    public void shouldNotFormEmpty() {
        var cardInfo = DataHelper.getAllEmpty();
        DBHelper.getBody(cardInfo, paymentUrl, 500);
        var payments = DBHelper.getPayments();
        var orders = DBHelper.getOrders();

        assertEquals(0, payments.size());
        assertEquals(0, orders.size());
    }
}