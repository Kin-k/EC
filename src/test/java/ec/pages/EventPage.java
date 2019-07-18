package ec.pages;

import com.codeborne.selenide.SelenideElement;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import ec.data.Country;
import ec.data.Importance;
import ec.elements.implementations.EventTabs;
import ec.elements.implementations.eventhistorytable.EventHistoryTable;

import javax.annotation.Nonnull;
import java.util.List;

import static com.codeborne.selenide.Condition.exactTextCaseSensitive;
import static com.codeborne.selenide.Selenide.$;

public class EventPage extends BasePage<EventPage> {

    private final SelenideElement importanceElement = $(".event-table__importance");
    private final SelenideElement countryElement = $(".ec-event__header__content a");
    private final EventTabs tabs = new EventTabs($("#calendar-tabs"));
    private final EventHistoryTable history = new EventHistoryTable($("#tab_content_history"));

    @CanIgnoreReturnValue
    public EventPage importanceShouldBe(@Nonnull Importance importance) {
        importanceElement.shouldHave(exactTextCaseSensitive(importance.toString().toUpperCase()));
        return this;
    }

    @CanIgnoreReturnValue
    public EventPage countryShouldBe(@Nonnull Country country) {
        countryElement.shouldHave(exactTextCaseSensitive(country.toString()));
        return this;
    }

    public List<List<String>> getHistory(@Nonnull List<String> columns) {
        tabs.select("History");
        return history.parseAllPages(columns);
    }
}
