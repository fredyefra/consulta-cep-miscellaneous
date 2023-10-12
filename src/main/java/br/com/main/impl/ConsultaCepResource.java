package br.com.main.impl;

import br.com.main.bean.EnderecoWrapper;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.common.annotation.NonBlocking;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/cep")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ConsultaCepResource {

    @Inject
    ConsultaCepServiceImpl service;

    @Blocking
    @Path("/{cep}")
    @GET
    public Uni<EnderecoWrapper> consultarPorCep (@PathParam("cep") String cep){
        return  service.consultarPorCep(cep);
    }

    //@NonBlocking
    @Blocking
    @Path("/{uf}/{cidade}/{logradouro}")
    @GET
    public Multi<List<EnderecoWrapper>> consultarPorUFCidadeLogradouro(@PathParam("uf")String uf,
                                                                       @PathParam("cidade")String cidade,
                                                                       @PathParam("logradouro")String logradouro){

        return service.consultarPorUFCidadeLogradouro(uf, cidade, logradouro);
    }



//    @Path("/{cep}")
//    @Blocking
//    @GET
//    public Uni<Response> consultarPorCep (@PathParam("cep") String cep){
//
//        return  service.consultarPorCep(cep).map(enderecoWrapper -> {
//
//            if (enderecoWrapper != null){
//                return Response.status(Response.Status.OK).entity(enderecoWrapper).build();
//            }
//            return null;
//        }).onItem()
//          .ifNull()
//          .continueWith(status(Response.Status.NOT_FOUND).build());
//    }
}
