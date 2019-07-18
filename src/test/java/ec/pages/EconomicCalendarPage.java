package ec.pages;

import com.codeborne.selenide.ElementsCollection;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import ec.data.Currency;
import ec.data.Importance;
import ec.data.Period;
import ec.elements.implementations.CurrencyFilter;
import ec.elements.implementations.ImportanceFilter;
import ec.elements.implementations.PeriodFilter;
import ec.elements.implementations.eventstable.EventsTable;

import javax.annotation.Nonnull;

import static com.codeborne.selenide.Selenide.$;
import static org.assertj.core.api.Assertions.assertThat;

public class EconomicCalendarPage extends BasePage<EconomicCalendarPage> {

    public static final String URL = "https://www.mql5.com/en/economic-calendar";

    private final PeriodFilter periodFilter = new PeriodFilter($("#economicCalendarFilterDate"));
    private final ImportanceFilter importanceFilter = new ImportanceFilter($("#economicCalendarFilterImportance"));
    private final CurrencyFilter currencyFilter = new CurrencyFilter($("#economicCalendarFilterCurrency"));
    private final EventsTable eventsTable = new EventsTable($("#economicCalendarTable"));

    @CanIgnoreReturnValue
    public EconomicCalendarPage filterByPeriod(@Nonnull Period period) {
        periodFilter.select(period);
        eventsTable.periodShouldBe(period);
        return this;
    }

    @CanIgnoreReturnValue
    public EconomicCalendarPage filterByImportance(@Nonnull Importance... importance) {
        importanceFilter.selectOnly(importance);
        eventsTable.shouldContainsEventsOnlyWithImportance(importance);
        return this;
    }

    @CanIgnoreReturnValue
    public EconomicCalendarPage filterByCurrency(@Nonnull Currency... currency) {
        currencyFilter.selectOnly(currency);
        eventsTable.shouldContainsEventsOnlyWithCurrency(currency);
        return this;
    }

    private ElementsCollection getEventsLinksThatHaveDetails() {
        return eventsTable.getEventsLinksThatHaveDetails();
    }

    public EventPage goToEvent(int eventNumber) {
        ElementsCollection events = getEventsLinksThatHaveDetails();

        assertThat(events.size()).overridingErrorMessage("No events with details available")
                                 .isGreaterThan(eventNumber);

        events.get(eventNumber)
              .click();

        return new EventPage();
    }
}
