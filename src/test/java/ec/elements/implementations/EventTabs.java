package ec.elements.implementations;

import com.codeborne.selenide.SelenideElement;
import ec.elements.ListBox;

import javax.annotation.Nonnull;

import static ec.utils.XpathUtils.hasString;

public class EventTabs extends WrappedElement
        implements ListBox<EventTabs> {

    public EventTabs(@Nonnull SelenideElement selenideElement) {
        super(selenideElement);
    }

    @Override
    public SelenideElement getSelectedElement() {
        return $("li.selected");
    }

    @Override
    public SelenideElement getElementWithValue(@Nonnull String value) {
        return $x("./li" + hasString(value));
    }
}
