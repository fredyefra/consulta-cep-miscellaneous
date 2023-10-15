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
    @GET
    @Path("/{cep}")
    public Uni<EnderecoWrapper> consultarPorCep(@PathParam("cep") String cep) {
        return service.consultarPorCep(cep);
    }

    @Blocking
    @GET
    @Path("/{uf}/{cidade}/{logradouro}")
    public Multi<List<EnderecoWrapper>> consultarPorUFCidadeLogradouro(@PathParam("uf") String uf,
                                                                       @PathParam("cidade") String cidade,
                                                                       @PathParam("logradouro") String logradouro) {

        return service.consultarPorUFCidadeLogradouro(uf, cidade, logradouro);
    }
}