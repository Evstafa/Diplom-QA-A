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
import ru.netology.page.CreditPage;
import ru.netology.page.MainPage;
import ru.netology.page.PaymentPage;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DBHelper.cleanDatabase;

public class CreditUITests {
    @BeforeAll
    public static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void setUp() {
        open("http://localhost:8080/");
    }

    @BeforeEach
    public void tearDown() {
        cleanDatabase();
    }

    @AfterAll
    public static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Epic(value = "UI-тесты")
    @Feature(value = "Credit-UI")
    @Story(value = "Позитивный. Покупка тура с действующей карты (номер с пробелами)")
    @Test
    public void shouldValidTestCardApproved() {
        MainPage mainPage = new MainPage();
        var cardInfo = DataHelper.getValidCardApproved();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(cardInfo);
        creditPage.getSuccessNotification();
        assertEquals("APPROVED", DBHelper.getCreditStatus());
    }

    @Epic(value = "UI-тесты")
    @Feature(value = "Credit-UI")
    @Story(value = "Негативный. Поле Номер карты 11 цифр")
    @Test
    public void shouldNotCardNumberInsufficientChar() {
        MainPage mainPage = new MainPage();
        var cardInfo = DataHelper.getRandomCard11char();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(cardInfo);
        creditPage.getInputInvalid("Неверный формат");
    }

    @Epic(value = "UI-тесты")
    @Feature(value = "Credit-UI")
    @Story(value = "Негативный. Поле Номер карты 20 цифр")
    @Test
    public void shouldNotCardNumberOverChar() {
        MainPage mainPage = new MainPage();
        var cardInfo = DataHelper.getRandomCard20char();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(cardInfo);
        creditPage.getErrorNotification();
    }

    @Epic(value = "UI-тесты")
    @Feature(value = "Credit-UI")
    @Story(value = "Позитивный. Поле Номер карты 16 цифр")
    @Test
    public void shouldCardNumberSixteenChar() {
        MainPage mainPage = new MainPage();
        var cardInfo = DataHelper.getRandomCard16char();
        CreditPage CreditPage = mainPage.creditButtonClick();
        CreditPage.inputData(cardInfo);
        CreditPage.getErrorNotification();
    }

    @Epic(value = "UI-тесты")
    @Feature(value = "Credit-UI")
    @Story(value = "Негативный. Поле Номер карты содержит не-цифры")
    @Test
    public void shouldNotCardNumberSymbols() {
        MainPage mainPage = new MainPage();
        var cardInfo = DataHelper.getRandomCardSymbols();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(cardInfo);
        creditPage.getInputInvalid("Неверный формат");
    }

    @Epic(value = "UI-тесты")
    @Feature(value = "Credit-UI")
    @Story(value = "Негативный. Поле Номер карты не может быть пустым")
    @Test
    public void shouldNotCardNumberEmpty() {
        MainPage mainPage = new MainPage();
        var cardInfo = DataHelper.getCardEmpty();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(cardInfo);
        creditPage.getInputInvalid("Неверный формат");
    }

    @Epic(value = "UI-тесты")
    @Feature(value = "Credit-UI")
    @Story(value = "Негативный. Поле Месяц не может содержать значения свыше 12")
    @Test
    public void shouldNotMonthUpTwelve() {
        MainPage mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidMonthOver12();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(cardInfo);
        creditPage.getInputInvalid("Неверно указан срок действия карты");
    }

    @Epic(value = "UI-тесты")
    @Feature(value = "Credit-UI")
    @Story(value = "Негативный. Поле Месяц не может состоять из одной цифры")
    @Test
    public void shouldNotMonthSingleDigit() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getMonth1char();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getInputInvalid("Неверный формат");
    }

    @Epic(value = "UI-тесты")
    @Feature(value = "Credit-UI")
    @Story(value = "Негативный. Поле Месяц не содержать математические символы")
    @Test
    public void shouldNotMonthSymbols() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getInvalidMonthSymbols();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getInputInvalid("Неверный формат");
    }

    @Epic(value = "UI-тесты")
    @Feature(value = "Credit-UI")
    @Story(value = "Негативный. Поле Месяц не может быть отрицательным")
    @Test
    public void shouldNotMonthNegative() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getNegativeMonth();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getInputInvalid("Неверный формат");
    }

    @Epic(value = "UI-тесты")
    @Feature(value = "Credit-UI")
    @Story(value = "Негативный. Поле Месяц не может быть пустым")
    @Test
    public void shouldNotMonthEmpty() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getMonthEmpty();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getInputInvalid("Неверный формат");
    }

    @Epic(value = "UI-тесты")
    @Feature(value = "Credit-UI")
    @Story(value = "Негативный. Значение поля Год не может быть меньше текущего")
    @Test
    public void shouldNotYearFieldLessCurrent() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getInvalidYearLessCurrent();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getInputInvalid("Истёк срок действия карты");
    }

    @Epic(value = "UI-тесты")
    @Feature(value = "Credit-UI")
    @Story(value = "Негативный. Поле Год не может быть равным нулю")
    @Test
    public void shouldNotYearZero() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getYearNull();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getInputInvalid("Истёк срок действия карты");
    }

    @Epic(value = "UI-тесты")
    @Feature(value = "Credit-UI")
    @Story(value = "Негативный. Поле Год не может быть пустым")
    @Test
    public void shouldNotYearEmpty() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getYearEmpty();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getInputInvalid("Неверный формат");
    }

    @Epic(value = "UI-тесты")
    @Feature(value = "Credit-UI")
    @Story(value = "Позитивный. Поле Владелец может быть с пробелом в середине")
    @Test
    public void shouldHolderContainSpace() {
        MainPage mainPage = new MainPage();
        var cardInfo = DataHelper.getValidHolderWithSpaceMiddle();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(cardInfo);
        creditPage.getSuccessNotification();
    }

    @Epic(value = "UI-тесты")
    @Feature(value = "Credit-UI")
    @Story(value = "Позитивный. Поле Владелец может быть в нижнем регистре")
    @Test
    public void shouldHolderLowercase() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getHolderLowercase();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getSuccessNotification();
    }

    @Epic(value = "UI-тесты")
    @Feature(value = "Credit-UI")
    @Story(value = "Позитивный. Поле Владелец может быть с дефисом в середине")
    @Test
    public void shouldHolderContainDash() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getValidHolderWithDashMiddle();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getSuccessNotification();
    }

    @Epic(value = "UI-тесты")
    @Feature(value = "Credit-UI")
    @Story(value = "Негативный. Поле Владелец не может быть пустым")
    @Test
    public void shouldNotHolderEmpty() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getHolderEmpty();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getInputInvalid("Поле обязательно для заполнения");
    }

    @Epic(value = "UI-тесты")
    @Feature(value = "Credit-UI")
    @Story(value = "Негативный. Поле Владелец не может состоять из пробела")
    @Test
    public void shouldNotHolderOnlySpace() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getHolderSpace();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getInputInvalid("Поле обязательно для заполнения");
    }

    @Epic(value = "UI-тесты")
    @Feature(value = "Credit-UI")
    @Story(value = "Негативный. Поле CVC/CVV не может быть меньше 3 цифр")
    @Test
    public void shouldNotCVCLessThreeChar() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getInvalidCvc2char();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getInputInvalid("Неверный формат");
    }

    @Epic(value = "UI-тесты")
    @Feature(value = "Credit-UI")
    @Story(value = "Негативный. Поле CVC/CVV не может быть больше 3 цифр")
    @Test
    public void shouldNotCvcMoreThreeChar() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getInvalidCvc4char();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getSuccessNotification();
    }

    @Epic(value = "UI-тесты")
    @Feature(value = "Credit-UI")
    @Story(value = "Негативный. Поле CVC/CVV не может содержать символы, отличные от цифр ")
    @Test
    public void shouldNotCvcNonDigits() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getInvalidCvcSymbols();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getInputInvalid("Поле обязательно для заполнения");
    }

    @Epic(value = "UI-тесты")
    @Feature(value = "Credit-UI")
    @Story(value = "Негативный. Поле CVC/CVV не может быть пустым")
    @Test
    public void shouldNotCvcEmpty() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getCvcEmpty();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getInputInvalid("Поле обязательно для заполнения");
    }

    @Epic(value = "UI-тесты")
    @Feature(value = "Credit-UI")
    @Story(value = "Негативный. Поля не могут быть пустыми")
    @Test
    public void shouldAllFieldsEmpty() {
        MainPage mainPage = new MainPage();
        var CardInfo = DataHelper.getAllEmpty();
        CreditPage creditPage = mainPage.creditButtonClick();
        creditPage.inputData(CardInfo);
        $(byText("Номер карты")).parent().$(".input__sub").shouldBe(visible).
                shouldHave(text("Неверный формат"));
        $(byText("Месяц")).parent().$(".input__sub").shouldBe(visible).
                shouldHave(text("Неверный формат"));
        $(byText("Год")).parent().$(".input__sub").shouldBe(visible).
                shouldHave(text("Неверный формат"));
        $(byText("Владелец")).parent().$(".input__sub").shouldBe(visible).
                shouldHave(text("Поле обязательно для заполнения"));
        $(byText("CVC/CVV")).parent().$(".input__sub").shouldBe(visible).
                shouldHave(text("Неверный формат"));
    }
}