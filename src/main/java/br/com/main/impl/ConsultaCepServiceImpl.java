package br.com.main.impl;


import br.com.main.bean.EnderecoWrapper;
import br.com.main.rest.ConsultaCepService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.time.Duration;
import java.util.List;

@ApplicationScoped
public class ConsultaCepServiceImpl {

    @Inject
    @RestClient
    private ConsultaCepService service;

    public Uni<EnderecoWrapper> consultarPorCep(final String cep) {

        //UniOnCancel<EnderecoWrapper> cancel = Uni.createFrom().item(service.consultarPorCep(cep)).onCancellation();
        return Uni
                .createFrom()
                .item(service.consultarPorCep(cep))
                .ifNoItem().after(Duration.ofSeconds(60))
                .failWith(BadRequestException::new)
                .onItem()
                .ifNull().failWith(NotFoundException::new)
                .log();
    }

    public Multi<List<EnderecoWrapper>> consultarPorUFCidadeLogradouro(final String uf,
                                                                       final String bairro,
                                                                       final String logradouro) {

        return Multi
                .createFrom()
                .items(service.consultarPorUFCidadeLogradouro(uf, bairro, logradouro));

    }
}

