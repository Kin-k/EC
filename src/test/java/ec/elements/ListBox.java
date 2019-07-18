package ec.elements;

import com.codeborne.selenide.SelenideElement;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

import javax.annotation.Nonnull;

import static com.codeborne.selenide.Condition.exactTextCaseSensitive;

public interface ListBox<SELF extends ListBox<SELF>>
        extends BaseElement<SELF> {

    SelenideElement getSelectedElement();

    SelenideElement getElementWithValue(@Nonnull String value);

    @CanIgnoreReturnValue
    default SELF select(@Nonnull String value) {
        SelenideElement selectedElement = getSelectedElement();
        if (selectedElement.isDisplayed() && value.equals(selectedElement.getText())) {
            return self();
        }

        getElementWithValue(value).click();
        selectedElement.shouldHave(exactTextCaseSensitive(value));
        return self();
    }
}
