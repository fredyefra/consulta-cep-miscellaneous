
//import jakarta.ws.rs.NotFoundException;
//import jakarta.ws.rs.Produces;
//import jakarta.ws.rs.core.MediaType;
//import jakarta.ws.rs.core.Response;
//import jakarta.ws.rs.ext.ExceptionMapper;
//import jakarta.ws.rs.ext.Provider;
//
////import com.fasterxml.jackson.databind.ObjectMapper;
////import com.fasterxml.jackson.databind.node.ObjectNode;
////import jakarta.inject.Inject;
////import jakarta.ws.rs.NotFoundException;
////import jakarta.ws.rs.Produces;
////import jakarta.ws.rs.core.MediaType;
////import jakarta.ws.rs.core.Response;
////import jakarta.ws.rs.ext.ExceptionMapper;
////import jakarta.ws.rs.ext.Provider;
////
//@Provider
//public class NegocioExceptionMapper2 implements ExceptionMapper<NotFoundException> {
//
//    //@Inject
//    //ObjectMapper objectMapper;
//
//    @Override
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response toResponse(NotFoundException exception) {
//
//       //Response errorResponse = mapExceptionToResponse(exception);
//        MessageError message = new MessageError();
//        message.setMessage(exception.getMessage());
//        message.setCode(exception.getResponse().getStatus());
//
//        if (exception instanceof NotFoundException){
//            return Response.status(Response.Status.NOT_FOUND).entity(message.getMessage()).build();
//        }
//        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Deu Zica, procure o suporte !!!!!" ).build();
//    }
//
//    /*private String respostaJson(int errorStatus, String errorMessage) {
//        ObjectNode errorObject = objectMapper.createObjectNode();
//        errorObject.put("Status: ", errorStatus);
//        errorObject.put("Ttitulo: ", errorMessage);
//
//        return objectMapper.createArrayNode().add(errorObject).toString();
//    }*/
//}
//
