package ec.elements;

import com.google.errorprone.annotations.CanIgnoreReturnValue;

public interface Paginator<SELF extends Paginator<SELF>>
        extends BaseElement<Paginator<SELF>> {

    @CanIgnoreReturnValue
    SELF goToPage(int i);

    @CanIgnoreReturnValue
    SELF goToNextPage();

    boolean hasNextPage();
}
