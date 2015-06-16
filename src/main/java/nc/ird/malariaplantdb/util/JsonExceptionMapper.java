package nc.ird.malariaplantdb.util;

import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;

/**
 * Exception mapper that returns the exception as JSON
 *
 * @author acheype
 */
@Slf4j
@Provider
public class JsonExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(final Exception e) {
        String type = e.getClass().getSimpleName();
        String message = e.getMessage();

        Map<String, String> entity = new HashMap<>();
        entity.put("type", type);
        entity.put("message", message);

        log.error(type, e);

        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(entity)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }
}
