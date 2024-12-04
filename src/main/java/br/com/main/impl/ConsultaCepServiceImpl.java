package br.com.main.impl;


import br.com.main.bean.EnderecoWrapper;
import br.com.main.rest.ConsultaCepService;
import br.com.main.utils.DelayUtils;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@ApplicationScoped
public class ConsultaCepServiceImpl {

    @Inject
    @RestClient
    private ConsultaCepService service;

    @Inject
    private DelayUtils delayUtils;

    @Inject
    Logger logger;

    public Uni<EnderecoWrapper> consultarPorCep(final String cep) {

        return Uni.createFrom().completionStage(
                        //CompletableFuture.completedStage(service.consultarPorCep(cep))
                        CompletableFuture.supplyAsync(() -> service.<EnderecoWrapper>consultarPorCep(cep), new Executor() {
                            @Override
                            public void execute(Runnable command) {
                                new Thread(command).start();
                            }
                        })
                )
                //return service.consultarPorCep(cep)
                .onItem().ifNull().failWith(NotFoundException::new)
                .ifNoItem().after(Duration.ofSeconds(30))
                .failWith(BadRequestException::new)
                .onFailure().invoke(falha-> Log.log(Logger.Level.ERROR, "Requisição Falhou!" + falha))
                .onTermination().invoke(() -> Log.log(Logger.Level.INFO, "Requisição Entregue!"))
                .log();
    }

    public Uni<List<EnderecoWrapper>> consultarPorUFCidadeLogradouro(final String uf, final String bairro, final String logradouro) throws ExecutionException, InterruptedException {

        Uni<List<EnderecoWrapper>> enderecos = Uni
                .createFrom()
                .item(

                        CompletableFuture.supplyAsync(() -> service.consultarPorUFCidadeLogradouro(uf, bairro, logradouro))
                                .get()
                                .stream()
                                .filter(enderecoWrapper -> enderecoWrapper.uf().length() == 2)
                                .filter(enderecoWrapper -> enderecoWrapper.bairro().length() >= 3)
                                .filter(enderecoWrapper -> enderecoWrapper.logradouro().length() >= 3)
                                .collect(Collectors.toList())
                )
                .onItem().invoke(this::imprimir)
                .onFailure().invoke(() -> Log.log(Logger.Level.ERROR, "Requisição Falhou!"))
                .onTermination().invoke(() -> Log.log(Logger.Level.INFO, "Requisição Entregue!"));

        return enderecos;

    }

    private List imprimir(List<EnderecoWrapper> enderecos) {

        IntStream.range(0, enderecos.size())
                .mapToObj(endereco -> endereco + " ==> " + enderecos.get(endereco))
                .collect(Collectors.toList())
                .forEach(System.out::println);
        return enderecos;
    }
}