package br.com.main.rest;

import br.com.main.bean.EnderecoWrapper;
import br.com.main.exception.NegocioExceptionMapper;
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
    @Path("/{uf}/{cidade}/{logradouro}/json/")
    List<EnderecoWrapper> consultarPorUFCidadeLogradouro(
            @PathParam("uf") String uf,
            @PathParam("cidade") String cidade,
            @PathParam("logradouro") String logradouro);
}