package ec.data;

import static ec.data.Country.CANADA;
import static ec.data.Country.SWITZERLAND;

public enum Currency {
    CAD("Canadian dollar", CANADA),
    CHF("Swiss frank", SWITZERLAND),
    ;

    private final String fullName;
    private final Country country;

    Currency(String description, Country country) {
        fullName = toString() + " - " + description;
        this.country = country;
    }

    public String getFullName() {
        return fullName;
    }

    public Country getCountry() {
        return country;
    }
}
