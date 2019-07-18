package ec.elements;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public interface Table<R extends Row<R>, SELF extends Table<R, SELF>>
        extends BaseElement<SELF> {

    ElementsCollection getHeaders();

    int getRowsCount();

    R getRow(int i);

    default List<List<String>> parse(@Nonnull List<String> columns) {
        return parseColumns(getColumnsIndexes(columns));
    }

    // TODO: make private after upgrade Java to 1.9
    // If speed is critical here then should rewrite this using .getAttribute("innerHTML") and parse HTML
    // instead of multiply requests to the browser
    default List<List<String>> parseColumns(@Nonnull List<Integer> columnsIndexes) {
        List<List<String>> result = new ArrayList<>();
        for (int i = 1; i <= getRowsCount(); i++) {
            result.add(getRow(i).parse(columnsIndexes));
        }
        return result;
    }

    // TODO: make private after upgrade Java to 1.9
    default List<Integer> getColumnsIndexes(@Nonnull List<String> columns) {
        Map<String, Integer> headerIndexes = new HashMap<>();
        int i = 0;
        for (SelenideElement h : getHeaders()) {
            String text = h.getText();
            headerIndexes.put(text, i);
            i++;
        }

        return columns.stream()
                      .peek(c -> assertThat(headerIndexes).overridingErrorMessage("Column '%s' is absent", c)
                                                          .containsKey(c))
                      .map(headerIndexes::get)
                      .collect(toList());
    }
}
