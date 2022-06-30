package exceptions;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GenerationException implements ExceptionMapper<Exception> {

    @Override
    @Produces(MediaType.APPLICATION_JSON)
    public Response toResponse(Exception exception) {

        MessageError messageError;

        if (exception instanceof ExceptionGeneric) {

            messageError = ExceptionGeneric.messageError;
            return Response.status(Integer.parseInt(messageError.getStatus().toString()))
                    .entity(messageError).build();
        }

        if (exception instanceof ValidationException) {
            Set<ConstraintViolation<Object>> validate = (Set<ConstraintViolation<Object>>) ValidationException.obj;

            String violations = validate.stream().map(violation -> violation.getMessage())
                    .collect(Collectors.joining(", "));

            messageError = MessageError.getError(violations, "Validator fields", 400L);
            return Response.status(Status.BAD_REQUEST).entity(messageError).build();
        }

        if (exception instanceof ProcessingException) {
            messageError = MessageError.getError("Type Invalid", "Validator fields", 400L);
            return Response.status(Status.BAD_REQUEST).entity(messageError).build();
        }
        return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Please contact support").build();
    }
}
