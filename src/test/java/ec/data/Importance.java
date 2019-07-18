package ec.data;

import static org.apache.commons.lang3.StringUtils.capitalize;

public enum Importance {

    HIGH,
    HOLIDAYS,
    LOW,
    ;

    private final String value;

    Importance() {
        this.value = capitalize(name().toLowerCase());
    }

    Importance(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
