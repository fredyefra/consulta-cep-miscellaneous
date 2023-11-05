package br.com.main.exception;

import br.com.main.message.MessageError;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.HashMap;
import java.util.Map;

@Provider
public class NegocioExceptionMapper implements ExceptionMapper<WebApplicationException> {
//public class NegocioExceptionMapper implements ExceptionMapper<ClientWebApplicationException> {
    @Override
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response toResponse(WebApplicationException exception) {

        MessageError message = new MessageError();
        message.setMessage(exception.getMessage());
        message.setCode(exception.getResponse().getStatus());

      switch (exception.getResponse().getStatus()){
            case 400: return Response.status(Response.Status.BAD_REQUEST).entity("    DEU ZICA AQUI ---> :  1   " + (message.getMessage()) + message.getCode()).build();
            case 401: return Response.status(Response.Status.UNAUTHORIZED).entity("   IMPLEMENTAR ---> :  2   " + (message.getMessage()) + message.getCode()).build();
            case 403: return Response.status(Response.Status.FORBIDDEN).entity("      IMPLEMENTAR ---> :  3   " + (message.getMessage()) + message.getCode()).build();
            case 404: return Response.status(Response.Status.NOT_FOUND).entity("      DEU ZICA AQUI ---> :  4   " + (message.getMessage()) + message.getCode()).build();
            default:  return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("PROCURE O SUPORTE !!!!!" ).build();
        }
    }
}