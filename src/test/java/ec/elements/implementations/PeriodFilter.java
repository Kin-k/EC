package ec.elements.implementations;

import com.codeborne.selenide.SelenideElement;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import ec.data.Period;
import ec.elements.ListBox;

import javax.annotation.Nonnull;

import static ec.utils.XpathUtils.hasString;

public class PeriodFilter extends WrappedElement
        implements ListBox<PeriodFilter> {

    public PeriodFilter(@Nonnull SelenideElement selenideElement) {
        super(selenideElement);
    }

    @CanIgnoreReturnValue
    public PeriodFilter select(@Nonnull Period value) {
        return select(value.toString());
    }

    @Override
    public SelenideElement getSelectedElement() {
        return $("label.checked");
    }

    @Override
    public SelenideElement getElementWithValue(@Nonnull String value) {
        return $x("./li/label" + hasString(value));
    }
}
