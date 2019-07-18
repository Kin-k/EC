package ec.elements;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ec.utils.Selfable;

import javax.annotation.Nonnull;

// TODO implement SelenideElement and proxy it instead of using WrappedElement
interface BaseElement<SELF extends BaseElement<SELF>>
        extends Selfable<SELF> {

    SelenideElement $(@Nonnull String css);

    SelenideElement $(@Nonnull String css, int index);

    SelenideElement $x(@Nonnull String xpath);

    SelenideElement $x(@Nonnull String xpath, int index);

    ElementsCollection $$(@Nonnull String css);

    ElementsCollection $$x(@Nonnull String xpath);

    boolean isDisplayed();
}
