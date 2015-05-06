package nc.ird.malariaplantdb.service.xls.exceptions;

/**
 * Checked exception which occurs in the xls importation process
 */
public class ImportException extends Exception {

    public ImportException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImportException(String message) {
        super(message);
    }
}
