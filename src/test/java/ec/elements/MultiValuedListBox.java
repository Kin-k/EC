package ec.elements;

import com.codeborne.selenide.SelenideElement;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

import javax.annotation.Nonnull;
import java.util.Set;

import static com.codeborne.selenide.Condition.exist;
import static ec.utils.XpathUtils.hasString;
import static org.apache.commons.compress.utils.Sets.newHashSet;

public interface MultiValuedListBox<SELF extends MultiValuedListBox<SELF>>
        extends BaseElement<SELF> {

    @CanIgnoreReturnValue
    default SELF selectOnly(@Nonnull String... values) {
        if (values.length == 0) {
            throw new IllegalArgumentException("MultiValuedListBox shouldn't have at least one selected value");
        }

        Set<String> valuesSet = newHashSet(values);

        valuesSet.forEach(value -> {
            SelenideElement option = $x("./li[label" + hasString(value) + "]");
            SelenideElement checked = option.$("input:checked");
            if (!checked.exists()) {
                option.$("label")
                      .click();
                checked.shouldBe(exist);
            }
        });

        $$("li > input:checked + label").forEach(label -> {
            if (!valuesSet.contains(label.getText())) {
                label.click();
                label.parent()
                     .$("input:checked")
                     .shouldNotBe(exist);
            }
        });

        return self();
    }
}
