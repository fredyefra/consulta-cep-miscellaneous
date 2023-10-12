package br.com.main.impl;


import br.com.main.bean.EnderecoWrapper;
import br.com.main.rest.ConsultaCepService;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@ApplicationScoped
public class ConsultaCepServiceImpl {

    @Inject
    @RestClient
    private ConsultaCepService service;

    private void atraso(List<EnderecoWrapper> enderecoWrapperList) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //posicao na lista de ceps
    private void imprimir(List<EnderecoWrapper> enderecoWrapperList){

         IntStream.range(0, enderecoWrapperList.size())
                .mapToObj(endereco -> endereco + " --> " + enderecoWrapperList.get(endereco))
                .collect(Collectors.toList())
                .forEach(System.out::println);

        //return collect;
    }
    public Uni<EnderecoWrapper> consultarPorCep(final String cep) {

        return Uni
                .createFrom()
                .item(service.consultarPorCep(cep))
                .onItem().ifNull().failWith(NotFoundException::new)
                //.onFailure().invoke(() -> Log.log(Logger.Level.FATAL,"Falha na  Entrega!"))
                .ifNoItem().after(Duration.ofSeconds(30))
                .failWith(BadRequestException::new)
                .onTermination().invoke(() -> Log.log(Logger.Level.INFO,"Requisição Entregue!"));
    }

    public Multi<List<EnderecoWrapper>> consultarPorUFCidadeLogradouro(final String uf,
                                                                       final String bairro,
                                                                       final String logradouro) {

        return   Multi
                .createFrom()
                .items(service.consultarPorUFCidadeLogradouro(uf, bairro, logradouro)
                        .stream()
                        .filter(enderecoWrapper -> enderecoWrapper.getUf().length() == 2)
                        .filter(enderecoWrapper -> enderecoWrapper.getBairro().length() >= 3)
                        .filter(enderecoWrapper -> enderecoWrapper.getLogradouro().length() >= 3)
                        .collect(Collectors.toUnmodifiableList()))
                .onItem().invoke(this::atraso)
                .onItem().invoke(this::imprimir)
                .onTermination().invoke(() -> Log.log(Logger.Level.INFO,"Requisição Entregue!"));

    }
}

