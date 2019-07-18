package ec.elements;

import com.codeborne.selenide.SelenideElement;

import javax.annotation.Nonnull;
import java.util.List;

import static java.util.stream.Collectors.toList;

public interface Row<SELF extends Row<SELF>>
        extends BaseElement<SELF> {

    SelenideElement getCell(int i);

    default List<String> parse(@Nonnull List<Integer> columnsIndexes) {
        return columnsIndexes.stream()
                             .map(i -> getCell(i).getText())
                             .collect(toList());
    }
}
