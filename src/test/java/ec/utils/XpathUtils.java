package ec.utils;

import javax.annotation.Nonnull;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.util.stream.Collectors.joining;

public final class XpathUtils {

    private static final String HAS_CLASS_TEMPLATE = "contains(concat(' ', @class, ' '), ' %s ')";

    private XpathUtils() {
    }

    public static String hasClass(@Nonnull String className) {
        return "[" + format(HAS_CLASS_TEMPLATE, className) + "]";
    }


    public static String hasNoClasses(@Nonnull String... classesNames) {
        return Stream.of(classesNames)
                     .map(className -> format(HAS_CLASS_TEMPLATE, className))
                     .collect(joining(" or ", "[not(", ")]"));
    }

    public static String hasString(@Nonnull String text) {
        return "[string(.)='" + text + "']";
    }

    public static String hasStringDifferentFrom(@Nonnull String... texts) {
        return Stream.of(texts)
                     .collect(joining("' and '", "[string(.)!='", "']"));
    }
}
