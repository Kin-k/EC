package ec.elements;

import javax.annotation.Nonnull;
import java.util.List;

public interface MultiPagedTable<R extends Row<R>, P extends Paginator<P>, SELF extends MultiPagedTable<R, P, SELF>>
        extends Table<R, SELF> {

    P getPaginator();

    default List<List<String>> parseAllPages(@Nonnull List<String> columns) {
        List<Integer> columnsIndexes = getColumnsIndexes(columns);

        P paginator = getPaginator();
        if (!paginator.isDisplayed()) {
            return parseColumns(columnsIndexes);
        }

        paginator.goToPage(1);
        List<List<String>> result = parseColumns(columnsIndexes);

        while (paginator.hasNextPage()) {
            paginator.goToNextPage();
            result.addAll(parseColumns(columnsIndexes));
        }

        return result;
    }
}
