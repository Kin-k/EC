package ec.elements.implementations;

import com.codeborne.selenide.SelenideElement;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import ec.data.Currency;
import ec.data.Importance;
import ec.elements.MultiValuedListBox;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.stream.Stream;

public class CurrencyFilter extends WrappedElement
        implements MultiValuedListBox<CurrencyFilter> {

    public CurrencyFilter(@Nonnull SelenideElement selenideElement) {
        super(selenideElement);
    }

    @CanIgnoreReturnValue
    public CurrencyFilter selectOnly(@Nonnull Currency... values) {
        return selectOnly(Stream.of(values)
                                .map(Currency::getFullName)
                                .toArray(String[]::new));
    }
}
