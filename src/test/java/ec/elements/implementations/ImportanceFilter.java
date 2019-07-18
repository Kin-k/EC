package ec.elements.implementations;

import com.codeborne.selenide.SelenideElement;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import ec.data.Importance;
import ec.elements.MultiValuedListBox;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.stream.Stream;

public class ImportanceFilter extends WrappedElement
        implements MultiValuedListBox<ImportanceFilter> {

    public ImportanceFilter(@Nonnull SelenideElement selenideElement) {
        super(selenideElement);
    }

    @CanIgnoreReturnValue
    public ImportanceFilter selectOnly(@Nonnull Importance... values) {
        return selectOnly(Stream.of(values)
                                .map(Objects::toString)
                                .toArray(String[]::new));
    }
}
