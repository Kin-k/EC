package ec.data;

import java.time.LocalDate;
import java.time.Month;

import static java.lang.String.format;
import static java.time.LocalDate.now;
import static java.time.format.TextStyle.SHORT;
import static java.util.Locale.ENGLISH;
import static org.apache.commons.lang3.StringUtils.capitalize;

public enum Period {
    CURRENT_MONTH{
        @Override
        public String toIntervalDescription() {
            LocalDate now = now();
            Month month = now.getMonth();
            int length = month.length(now.isLeapYear());
            return format("1  - %s %s, %s",
                          length, month.getDisplayName(SHORT, ENGLISH), now.getYear());
        }
    },
    ;

    private final String value;

    Period() {
        this.value = capitalize(name().toLowerCase()
                                      .replace("_", " "));
    }

    Period(String value) {
        this.value = value;
    }

    public abstract String toIntervalDescription();

    @Override
    public String toString() {
        return value;
    }
}
