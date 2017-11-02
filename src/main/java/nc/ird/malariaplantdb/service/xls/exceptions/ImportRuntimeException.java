package nc.ird.malariaplantdb.service.xls.exceptions;

/**
 * Unchecked exception which occurs in the xls importation process
 */
public class ImportRuntimeException extends RuntimeException {

    public ImportRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImportRuntimeException(String message) {
        super(message);
    }
}
