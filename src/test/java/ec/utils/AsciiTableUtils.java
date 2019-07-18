package ec.utils;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciitable.CWC_LongestLine;
import io.qameta.allure.Attachment;

import java.util.List;

import static de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment.RIGHT;

public final class AsciiTableUtils {

    private AsciiTableUtils() {
    }

    public static String formatTable(List<String> columnsNames, List<List<String>> rows) {
        AsciiTable at = new AsciiTable();

        at.addRow(columnsNames);
        rows.forEach(at::addRow);

        at.getRenderer()
          .setCWC(new CWC_LongestLine());

        return at.setPaddingLeftRight(1)
                 .setTextAlignment(RIGHT)
                 .render();
    }
}
