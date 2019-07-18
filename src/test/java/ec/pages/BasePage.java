package ec.pages;

import com.codeborne.selenide.SelenideElement;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import ec.utils.Selfable;

import static com.codeborne.selenide.Selenide.$;

abstract class BasePage<SELF extends BasePage<SELF>> implements Selfable<SELF> {

    @CanIgnoreReturnValue
    public SELF acceptCookiesPolicyIfNeeded() {
        SelenideElement button = $("#floatVerticalPanel span");
        if (button.isDisplayed()) {
            button.click();
        }
        return self();
    }
}
