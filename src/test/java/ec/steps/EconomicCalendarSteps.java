package ec.steps;

import com.codeborne.selenide.logevents.SelenideLogger;
import com.google.common.collect.ImmutableList;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import ec.data.Currency;
import ec.data.Importance;
import ec.data.Period;
import ec.pages.EconomicCalendarPage;
import ec.pages.EventPage;
import ec.utils.UrlShortener;

import io.qameta.allure.Allure;
import io.qameta.allure.selenide.AllureSelenide;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.util.List;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.url;
import static ec.utils.AsciiTableUtils.formatTable;
import static java.lang.System.lineSeparator;
import static java.util.stream.Collectors.toList;

public class EconomicCalendarSteps {

    public static final String USER_AGENT = "Googlebot";

    private static final List<String> necessaryHistoryColumns = ImmutableList.of("Date (GMT)", "Actual", "Forecast", "Previous");

    private final Logger log = LoggerFactory.getLogger(getClass());

    private Period period;
    private Importance importance;
    private Currency currency;
    private EconomicCalendarPage economicCalendarPage;
    private EventPage eventPage;

    @Before
    public void before() {
        // For routing Selenide logs to slf4j
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true)
                                                                         .savePageSource(true));

        System.setProperty("chromeoptions.args", "--user-agent=" + USER_AGENT);
    }

    @Given("^period (.+), importance (.+) and currency (.+)$")
    public void periodImportanceAndCurrency(Period period, Importance importance, Currency currency) {
        this.period = period;
        this.importance = importance;
        this.currency = currency;
    }

    @When("^open the economic calendar$")
    public void openEconomicCalendar() {
        economicCalendarPage = open(EconomicCalendarPage.URL, EconomicCalendarPage.class).acceptCookiesPolicyIfNeeded();
    }

    @When("^filter by period$")
    public void filterByPeriod() {
        economicCalendarPage.filterByPeriod(period);
    }

    @When("^filter by importance$")
    public void filterByImportance() {
        economicCalendarPage.filterByImportance(importance);
    }

    @When("^filter by currency$")
    public void filterByCurrency() {
        economicCalendarPage.filterByCurrency(currency);
    }

    @When("^open the first event$")
    public void openFirstEvent() {
        eventPage = economicCalendarPage.goToEvent(0);
    }

    @Then("event importance same")
    public void eventImportanceSame() {
        eventPage.importanceShouldBe(importance);
    }

    @Then("event country correspond to the currency")
    public void eventCountryCorrespondToCurrency() {
        eventPage.countryShouldBe(currency.getCountry());
    }

    @Then("log event history")
    public void logEventHistory() {
        if (log.isInfoEnabled()) {
            List<String> formattedColumnsNames = necessaryHistoryColumns.stream()
                                                                        .map(String::toUpperCase)
                                                                        .collect(toList());
            List<List<String>> eventHistory = eventPage.getHistory(formattedColumnsNames);
            String formattedHistory = formatTable(necessaryHistoryColumns, eventHistory);
            log.info("History:" + lineSeparator() + formattedHistory);
            Allure.getLifecycle()
                  .addAttachment("Event history", "text/text", "txt", formattedHistory.getBytes());
        }
    }

    @Then("log event short link")
    public void logEventShortLink() {
        if (log.isInfoEnabled()) {
            UrlShortener urlShortener = new UrlShortener();
            String shortUrl = urlShortener.shortUrl(url());
            log.info("Event: {}", shortUrl);
            Allure.getLifecycle()
                  .addAttachment("Event:", "text/uri-list", "", shortUrl.getBytes());
            urlShortener.delete(shortUrl);
        }
    }
}
