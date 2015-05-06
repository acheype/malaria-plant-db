package nc.ird.malariaplantdb.service.xls;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import nc.ird.malariaplantdb.service.xls.exceptions.ImportException;
import net.sf.jxls.reader.XLSReadMessage;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Error which occurs on a cell content during the importation process
 *
 * Contains an error message, and the cell coordinate
 *
 * @author acheype
 */
@Slf4j
@Getter
@Setter
@ToString
public class CellError {

    public CellError(String message, Exception sourceException) {
        this.message = message;
        this.sourceException = sourceException;
    }

    public CellError(String message, String sheet, Integer line, String column) {
        this.message = message;
        this.sheet = sheet;
        this.line = line;
        this.column = column;
    }

    public CellError(String message, String sheet, Integer line, String column, Exception sourceException) {
        this(message, sourceException);
        this.sheet = sheet;
        this.line = line;
        this.column = column;
    }

    private String message;

    private String sheet;

    private Integer line;

    private String column;

    private Exception sourceException;

    public static CellError buildFromReadMessage(List<SheetInfo> sheetsInfo, XLSReadMessage readMessage){

        Pattern pattern = Pattern.compile("^Can\'t read cell (\\D+)(\\d+) on (.*) spreadsheet$");
        Matcher matcher = pattern.matcher(readMessage.getMessage());

        if (matcher.find()){
            try {
                String sheet = matcher.group(3);
                Integer line = Integer.parseInt(matcher.group(2));

                String columnLetter = matcher.group(1);

                ColumnInfo columnInfo = sheetsInfo.stream()
                        .filter(si -> si.getSheetLabel().equals(sheet))
                        .flatMap(si -> si.getColumnInfos().stream())
                        .filter(fi -> fi.getColumnLetterRef().equals(columnLetter))
                        .findFirst().orElse(null);

                if (columnInfo == null){
                    throw new ImportException(String.format("Impossible to find the columnLetter " +
                            "'%s' in the FieldInfo objects of the sheet '%s'", columnLetter, sheet));
                }

                return new CellError("Error by reading the cell value. Please verify the value has the expected " +
                        "format.",
                        sheet,
                        line,
                        columnInfo.getColumnName(),
                        readMessage.getException());
            } catch (IllegalStateException | IndexOutOfBoundsException | NumberFormatException | ImportException e){
                log.warn("Impossible to parse the cell coordinate in the error message", e);
                return new CellError(readMessage.getMessage(), readMessage.getException());
            }
        } else
            return new CellError(readMessage.getMessage(), readMessage.getException());
    }

}
