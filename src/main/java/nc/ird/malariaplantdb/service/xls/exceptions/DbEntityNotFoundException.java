package nc.ird.malariaplantdb.service.xls.exceptions;

/**
 * Exception to inform that an entity searched in the database is not found
 */
public class DbEntityNotFoundException extends ImportException {

    public DbEntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DbEntityNotFoundException(String message) {
        super(message);
    }

}
