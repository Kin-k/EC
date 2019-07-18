package ec.data;

import static org.apache.commons.lang3.StringUtils.capitalize;

public enum Country {
    CANADA,
    SWITZERLAND,
    ;

    private final String value;

    Country() {
        this.value = capitalize(name().toLowerCase());
    }

    Country(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
