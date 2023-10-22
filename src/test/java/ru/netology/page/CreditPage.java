package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;
import java.time.LocalDate;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CreditPage {

    private final SelenideElement heading = $$("h3").find(text("Кредит по данным карты"));
    private final SelenideElement cardNumberField = $(byText("Номер карты")).parent().$(".input__control");
    private final SelenideElement monthField = $(byText("Месяц")).parent().$(".input__control");
    private final SelenideElement yearField = $(byText("Год")).parent().$(".input__control");
    private final SelenideElement holderField = $(byText("Владелец")).parent().$(".input__control");
    private final SelenideElement cvcField = $(byText("CVC/CVV")).parent().$(".input__control");
    private final SelenideElement continueButton = $$("button").find(exactText("Продолжить"));
    private final SelenideElement successNotification = $(".notification_status_ok");
    private final SelenideElement errorNotification = $(".notification_status_error");
    private final SelenideElement successButton = successNotification.$("button");
    private final SelenideElement errorButton = errorNotification.$("button");

    public CreditPage() {
        heading.shouldBe(visible);

        cardNumberField.shouldBe(visible);
        cardNumberField.shouldBe(matchText("^[0-9]{16}$"));

        monthField.shouldBe(visible);
        monthField.shouldBe(matchText("^(0?[0-1]|1[0-2])$"));

        yearField.shouldBe(visible);
        yearField.shouldBe(matchText(String.valueOf(LocalDate.now().plusYears(1).getYear())));

        holderField.shouldBe(visible);
        holderField.shouldBe(matchText("^[A-Za-z]+\\\\s[A-Za-z]+$"));

        cvcField.shouldBe(visible);
        cvcField.shouldBe(matchText("\\d{3}"));

        continueButton.shouldBe(visible);
        successNotification.shouldBe(hidden);
        errorNotification.shouldBe(hidden);
    }

    public void inputData(DataHelper.CardInfo card) {
        cardNumberField.setValue(card.getNumber());
        monthField.setValue(card.getMonth());
        yearField.setValue(card.getYear());
        holderField.setValue(card.getHolder());
        cvcField.setValue(card.getCvc());
        continueButton.click();
    }

    public void getSuccessNotification() {
        successNotification.should(visible, Duration.ofSeconds(15));
        successNotification.$("[class=notification__title]").should(text("Успешно"));
        successNotification.$("[class=notification__content]").should(text("Операция одобрена Банком."));
        successButton.click();
        successNotification.should(hidden);
    }

    public void getErrorNotification() {
        errorNotification.should(visible, Duration.ofSeconds(15));
        errorNotification.$("[class=notification__title]").should(text("Ошибка"));
        errorNotification.$("[class=notification__content]").should(text("Ошибка! Банк отказал в проведении операции."));
        errorButton.click();
        errorNotification.should(hidden);
    }
}
