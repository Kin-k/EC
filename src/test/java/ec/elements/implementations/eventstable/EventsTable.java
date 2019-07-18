package ec.elements.implementations.eventstable;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ec.data.Currency;
import ec.data.Importance;
import ec.data.Period;
import ec.elements.Table;
import ec.elements.implementations.WrappedElement;

import javax.annotation.Nonnull;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.exactTextCaseSensitive;
import static com.codeborne.selenide.Condition.exist;
import static ec.data.Importance.HOLIDAYS;
import static ec.utils.XpathUtils.*;

public class EventsTable extends WrappedElement
        implements Table<EventRow, EventsTable> {

    private static final int FIRST_ROW_NUMBER = 1;
    private static final String ROW_CSS = ".ec-table__item";
    private static final String IMPORTANCE_CELL_CLASS = "ec-table__importance";

    private final SelenideElement selectedPeriod = $(".ec-table__nav__item_current");

    public EventsTable(@Nonnull SelenideElement selenideElement) {
        super(selenideElement);
    }

    public ElementsCollection getEventsLinksThatHaveDetails() {
        return $$(".ec-table__col_event a");
    }

    public void periodShouldBe(@Nonnull Period period) {
        selectedPeriod.shouldHave(exactTextCaseSensitive(period.toIntervalDescription()));
    }

    @Override
    public ElementsCollection getHeaders() {
        return $$(".ec-table__header .ec-table__col");
    }

    @Override
    public int getRowsCount() {
        return $$(ROW_CSS).size();
    }

    @Override
    public EventRow getRow(int i) {
        return new EventRow($(ROW_CSS, i - FIRST_ROW_NUMBER));
    }

    public void shouldContainsEventsOnlyWithImportance(@Nonnull Importance... importances) {
        String[] importancesClasses = Stream.of(importances)
                                            .map(EventsTable::importanceCellClass)
                                            .toArray(String[]::new);
        $x(".//*" + hasClass(IMPORTANCE_CELL_CLASS) + hasNoClasses(importancesClasses))
                .shouldNotBe(exist);
    }

    private static String importanceCellClass(@Nonnull Importance importance) {
        return importance == HOLIDAYS ? IMPORTANCE_CELL_CLASS + "_none"
                                      : IMPORTANCE_CELL_CLASS + "_" + importance.toString()
                                                                                .toLowerCase();
    }

    public void shouldContainsEventsOnlyWithCurrency(@Nonnull Currency... currency) {
        String[] currenciesNames = Stream.of(currency)
                                         .map(Currency::toString)
                                         .toArray(String[]::new);
        $x(".//*" + hasClass("ec-table__curency-name") + hasStringDifferentFrom(currenciesNames))
                .shouldNotBe(exist);
    }
}
