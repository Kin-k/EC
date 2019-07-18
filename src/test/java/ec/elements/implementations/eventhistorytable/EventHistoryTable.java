package ec.elements.implementations.eventhistorytable;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ec.elements.MultiPagedTable;
import ec.elements.implementations.WrappedElement;

import javax.annotation.Nonnull;

public class EventHistoryTable extends WrappedElement
        implements MultiPagedTable<EventHistoryRow, EventHistoryPaginator, EventHistoryTable> {

    private static final String ROW_CSS = ".event-table-history__item";
    private static final int FIRST_ROW_NUMBER = 1;

    public EventHistoryTable(@Nonnull SelenideElement selenideElement) {
        super(selenideElement);
    }

    @Override
    public ElementsCollection getHeaders() {
        return $$(".event-table-history__header > div");
    }

    @Override
    public int getRowsCount() {
        return $$(ROW_CSS).size();
    }

    @Override
    public EventHistoryRow getRow(int i) {
        return new EventHistoryRow($(ROW_CSS, i - FIRST_ROW_NUMBER));

    }

    @Override
    public EventHistoryPaginator getPaginator() {
        return new EventHistoryPaginator($(".paginatorEx"));
    }
}
