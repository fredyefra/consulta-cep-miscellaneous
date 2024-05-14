package br.com.main.rest;

import br.com.main.bean.EnderecoWrapper;
import br.com.main.exception.NegocioExceptionMapper;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

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

}
