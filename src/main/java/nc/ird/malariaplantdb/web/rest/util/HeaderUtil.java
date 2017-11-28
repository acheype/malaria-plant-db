package nc.ird.malariaplantdb.web.rest.util;

import org.springframework.http.HttpHeaders;

/**
 * Utility class for http header creation.
 *
 */
public class HeaderUtil {

    public static HttpHeaders createAlert(String message, String param) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-malariaplantdbApp-alert", message);
        headers.add("X-malariaplantdbApp-params", param);
        return headers;
    }

    public static HttpHeaders createEntityCreationAlert(String entityName, String param) {
        return createAlert(String.format("A new " + entityName + " is created with the identifier '%s'", param), param);
    }

    public static HttpHeaders createEntityUpdateAlert(String entityName, String param) {
        return createAlert(String.format("A " + entityName + " is updated with identifier '%s'", param),  param);
    }

    public static HttpHeaders createEntityDeletionAlert(String entityName, String param) {
        return createAlert(String.format("A " + entityName + " is deleted with identifier '%s'", param), param);
    }
}
