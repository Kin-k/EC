package ec.elements.implementations.eventhistorytable;

import com.codeborne.selenide.SelenideElement;
import ec.elements.Row;
import ec.elements.implementations.WrappedElement;

import javax.annotation.Nonnull;

public class EventHistoryRow extends WrappedElement
        implements Row<EventHistoryRow> {

    EventHistoryRow(@Nonnull SelenideElement selenideElement) {
        super(selenideElement);
    }

    @Override
    public SelenideElement getCell(int i) {
        return $x("./div", i);
    }
}
