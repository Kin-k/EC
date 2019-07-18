package ec.utils;

public interface Selfable<SELF> {

    @SuppressWarnings("unchecked")
    default SELF self() {
        return (SELF) this;
    }
}
