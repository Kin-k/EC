package ec.elements.implementations.eventhistorytable;

import com.codeborne.selenide.SelenideElement;
import ec.elements.Paginator;
import ec.elements.implementations.WrappedElement;

import javax.annotation.Nonnull;

import static com.codeborne.selenide.Condition.cssClass;
import static ec.utils.XpathUtils.hasClass;
import static java.lang.Integer.parseInt;

public class EventHistoryPaginator extends WrappedElement
        implements Paginator<EventHistoryPaginator> {

    private static final int FIRST_PAGE_NUMBER = 1;
    private static final String PAGE_BUTTON_TAG = "a";
    private static final String PAGE_BUTTON_XPATH = "./" + PAGE_BUTTON_TAG;
    private static final String SELECTED_CLASS = "selected";
    private static final String SELECTED_PAGE_BUTTON_XPATH = PAGE_BUTTON_XPATH + hasClass(SELECTED_CLASS);

    private final SelenideElement selectedPageButton = $x(SELECTED_PAGE_BUTTON_XPATH);
    private final SelenideElement nextPageButton = $x(SELECTED_PAGE_BUTTON_XPATH + "/following-sibling::" + PAGE_BUTTON_TAG);

    EventHistoryPaginator(@Nonnull SelenideElement selenideElement) {
        super(selenideElement);
    }

    @Override
    public EventHistoryPaginator goToPage(int i) {
        SelenideElement button = $x(PAGE_BUTTON_XPATH, i - FIRST_PAGE_NUMBER);
        button.click();
        button.shouldHave(cssClass(SELECTED_CLASS));
        return this;
    }

    @Override
    public EventHistoryPaginator goToNextPage() {
        int selectedPageNumber = parseInt(selectedPageButton.getText());
        return goToPage(selectedPageNumber + 1);
    }

    @Override
    public boolean hasNextPage() {
        return nextPageButton.isDisplayed();
    }
}
