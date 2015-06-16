package nc.ird.malariaplantdb.service.xls.exceptions;

/**
 * Exception to inform that an entity searched in an Excel's spreadsheet is not found
 */
public class XlsEntityNotFoundException extends ImportException {

    public XlsEntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public XlsEntityNotFoundException(String message) {
        super(message);
    }

}
