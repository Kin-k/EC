package ec.elements.implementations.eventstable;

import com.codeborne.selenide.SelenideElement;
import ec.elements.Row;
import ec.elements.implementations.WrappedElement;

import javax.annotation.Nonnull;

public class EventRow extends WrappedElement
        implements Row<EventRow> {

    EventRow(@Nonnull SelenideElement selenideElement) {
        super(selenideElement);
    }

    @Override
    public SelenideElement getCell(int i) {
        throw new UnsupportedOperationException("Implement this method if you need it");
    }
}
