package ec.elements.implementations;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import javax.annotation.Nonnull;


public class WrappedElement {

    private final SelenideElement selenideElement;

    protected WrappedElement(@Nonnull SelenideElement selenideElement) {
        this.selenideElement = selenideElement;
    }

    public SelenideElement $(@Nonnull String css) {
        return selenideElement.$(css);
    }

    public SelenideElement $(@Nonnull String css, int index) {
        return selenideElement.$(css, index);
    }

    public SelenideElement $x(@Nonnull String xpath) {
        return selenideElement.$x(xpath);
    }

    public SelenideElement $x(@Nonnull String xpath, int index) {
        return selenideElement.$x(xpath, index);
    }

    public ElementsCollection $$(@Nonnull String css) {
        return selenideElement.$$(css);
    }

    public ElementsCollection $$x(@Nonnull String xpath) {
        return selenideElement.$$x(xpath);
    }

    public boolean isDisplayed() {
        return selenideElement.isDisplayed();
    }
}
