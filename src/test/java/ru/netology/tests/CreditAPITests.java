package ru.netology.tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DBHelper;
import ru.netology.data.DataHelper;
import ru.netology.page.CreditPage;
import ru.netology.page.MainPage;

import java.util.List;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DBHelper.cleanDatabase;

public class CreditAPITests {

    private static List<DBHelper.PaymentEntity> payments;
    private static List<DBHelper.CreditRequestEntity> credits;
    private static List<DBHelper.OrderEntity> orders;
    private static final String creditUrl = "/api/v1/credit";

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
    @Feature(value = "Оплата тура в кредит")
    @Story(value = "Позитивный. Покупка тура в кредит с действующей карты, создание записи в таблице credit_request_entity")
    @Test
    public void shouldValidTestCreditCardApprovedEntityAdded() {
        var cardInfo = DataHelper.getValidCardApproved();
        DBHelper.getBody(cardInfo, creditUrl,200);
        payments = DBHelper.getPayments();
        credits = DBHelper.getCreditRequests();

        assertEquals(0, payments.size());
        assertEquals(1, credits.size());
        assertEquals("APPROVED", DBHelper.getCreditStatus());
    }
    @Epic(value = "API-тесты")
    @Feature(value = "Оплата тура в кредит")
    @Story(value = "Позитивный. Покупка тура в кредит с действующей карты, создание записи в таблице order_entity")
    @Test
    public void shouldValidTestCreditCardApprovedOrdersAdded() {
        var cardInfo = DataHelper.getValidCardApproved();
        DBHelper.getBody(cardInfo, creditUrl, 200);
        var creditRequests = DBHelper.getCreditRequests();
        var orders = DBHelper.getOrders();

        assertAll(
                () -> assertEquals(1, creditRequests.size()),
                () -> assertEquals(DBHelper.getCreditStatus(), creditRequests.get(0).getStatus()),
                () -> assertEquals(DBHelper.getBankIDForCredit(), creditRequests.get(0).getBank_id()),
                () -> assertEquals(1, orders.size()));
    }

    @Epic(value = "API-тесты")
    @Feature(value = "Оплата тура в кредит")
    @Story(value = "Позитивный. Покупка тура в кредит с недействующей карты, создание записи в таблице credit_request_entity")
    @Test
    public void shouldValidTestCreditCardDeclinedEntityAdded() {
        var cardInfo = DataHelper.getValidCardDeclined();
        DBHelper.getBody(cardInfo, creditUrl, 200);
        payments = DBHelper.getPayments();
        credits = DBHelper.getCreditRequests();

        assertEquals(0, payments.size());
        assertEquals(1, credits.size());
        assertEquals("DECLINED", DBHelper.getCreditStatus());
    }

    @Epic(value = "API-тесты")
    @Feature(value = "Оплата тура в кредит")
    @Story(value = "Отправка пустого POST запроса кредита")
    @Test
    public void shouldCreditPOSTBodyEmpty() {
        var cardInfo = DataHelper.getAllEmpty();
        DBHelper.getBody(cardInfo, creditUrl, 400);
        payments = DBHelper.getPayments();
        credits = DBHelper.getCreditRequests();
        orders = DBHelper.getOrders();

        assertEquals(0, payments.size());
        assertEquals(0, credits.size());
        assertEquals(0, orders.size());
    }

    @Epic(value = "API-тесты")
    @Feature(value = "Оплата тура в кредит")
    @Story(value = "Отправка POST запроса кредита с пустым значением number")
    @Test
    public void shouldCreditPOSTNumberEmpty() {
        var cardInfo = DataHelper.getCardEmpty();
        DBHelper.getBody(cardInfo, creditUrl, 400);
        payments = DBHelper.getPayments();
        credits = DBHelper.getCreditRequests();
        orders = DBHelper.getOrders();

        assertEquals(0, payments.size());
        assertEquals(0, credits.size());
        assertEquals(0, orders.size());
    }

    @Epic(value = "API-тесты")
    @Feature(value = "Оплата тура в кредит")
    @Story(value = "Отправка POST запроса кредита с пустым значением month")
    @Test
    public void shouldCreditPOSTMonthEmpty() {
        var cardInfo = DataHelper.getMonthEmpty();
        DBHelper.getBody(cardInfo, creditUrl, 400);
        payments = DBHelper.getPayments();
        credits = DBHelper.getCreditRequests();
        orders = DBHelper.getOrders();

        assertEquals(0, payments.size());
        assertEquals(0, credits.size());
        assertEquals(0, orders.size());
    }

    @Epic(value = "API-тесты")
    @Feature(value = "Оплата тура в кредит")
    @Story(value = "Отправка POST запроса кредита с пустым значением year")
    @Test
    public void shouldCreditPOSTYearEmpty() {
        var cardInfo = DataHelper.getYearEmpty();
        DBHelper.getBody(cardInfo, creditUrl, 400);
        payments = DBHelper.getPayments();
        credits = DBHelper.getCreditRequests();
        orders = DBHelper.getOrders();

        assertEquals(0, payments.size());
        assertEquals(0, credits.size());
        assertEquals(0, orders.size());
    }

    @Epic(value = "API-тесты")
    @Feature(value = "Оплата тура в кредит")
    @Story(value = "Отправка POST запроса кредита с пустым значением holder")
    @Test
    public void shouldCreditPOSTHolderEmpty() {
        var cardInfo = DataHelper.getHolderEmpty();
        DBHelper.getBody(cardInfo, creditUrl, 400);
        payments = DBHelper.getPayments();
        credits = DBHelper.getCreditRequests();
        orders = DBHelper.getOrders();

        assertEquals(0, payments.size());
        assertEquals(0, credits.size());
        assertEquals(0, orders.size());
    }

    @Epic(value = "API-тесты")
    @Feature(value = "Оплата тура в кредит")
    @Story(value = "Отправка POST запроса кредита с пустым значением cvc")
    @Test
    public void shouldCreditPOSTCvcEmpty() {
        var cardInfo = DataHelper.getCvcEmpty();
        DBHelper.getBody(cardInfo, creditUrl, 400);
        payments = DBHelper.getPayments();
        credits = DBHelper.getCreditRequests();
        orders = DBHelper.getOrders();

        assertEquals(0, payments.size());
        assertEquals(0, credits.size());
        assertEquals(0, orders.size());
    }
    @Epic(value = "API-тесты")
    @Feature(value = "Credit API")
    @Story(value = "Негативный. Номер карты не может быть длиннее 20 символов, записи о платеже и заказе не проводятся, статус 500")
    @Test
    public void shouldNotCardNumberOverChar() {
        var cardInfo = DataHelper.getRandomCard20char();
        DBHelper.getBody(cardInfo, creditUrl, 500);
        var credits = DBHelper.getCreditRequests();
        assertEquals(0, credits.size());
    }
}