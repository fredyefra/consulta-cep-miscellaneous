package br.com.main.rest;

import br.com.main.bean.EnderecoWrapper;
import br.com.main.exception.NegocioExceptionMapper;
import io.smallrye.mutiny.Multi;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RegisterProvider(NegocioExceptionMapper.class)
@Path(value = "/ws")
@RegisterRestClient(configKey = "consulta-cep-api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface ConsultaCepService {

    @GET
    @Path("/{cep}/json/")
    EnderecoWrapper consultarPorCep(@PathParam("cep") final String cep);

    @GET
    @Path("/{uf}/{bairro}/{logradouro}/json/")
    List<EnderecoWrapper> consultarPorUFCidadeLogradouro(
            @PathParam("uf") final String uf,
            @PathParam("bairro") final String bairro,
            @PathParam("logradouro") final String logradouro);

    @GET
    @Path("/{cep}/json/")
    CompletableFuture<EnderecoWrapper> consultarPorCepv2(@PathParam("cep") final String cep);

    @GET
    @Path("/{cep}/json/")
    CompletableFuture<EnderecoWrapper> consultarPorUFCidadeLogradouro2(@PathParam("cep") final String cep);

}
