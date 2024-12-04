package br.com.main.impl;

import br.com.main.bean.EnderecoWrapper;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.helpers.test.UniAssertSubscriber;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;

@QuarkusTest
public class ConsultaCepServiceImplIntegrationTest {

    // java.lang.AssertionError:
    //Expected a completion event, but didn't received it.

    //https://github.com/smallrye/smallrye-mutiny/discussions/1057

    @Inject
    ConsultaCepServiceImpl service;

    @Test
    public void testDevolveEnderecoCompletoPeloCep() {

        EnderecoWrapper expectativa = endereco();

        EnderecoWrapper atual = consultarPorCep("71720585")
                //.invoke(item -> Assertions.assertEquals(item, endereco))
                .subscribe()
                .withSubscriber(UniAssertSubscriber.create())
                .awaitItem(Duration.ofSeconds(5))
                .getItem();

        Assertions.assertEquals(expectativa, atual, ()-> "Os endereços não são iguais!");

    }

    @Test
    public void testDevolveEnderecoCompletoPeloCepAssinado() {

        EnderecoWrapper expectativa = endereco();

        UniAssertSubscriber<EnderecoWrapper> enderecoSubscriber = consultarPorCep("71720585")
                .invoke(atual -> Assertions.assertEquals(expectativa, atual, ()-> "Os endereços não são iguais!"))
                .subscribe()
                .withSubscriber(UniAssertSubscriber.create());

        enderecoSubscriber
            .awaitItem(Duration.ofSeconds(10))
            .assertCompleted();
    }

    private Uni<EnderecoWrapper> consultarPorCep(final String cep) {
        return service.consultarPorCep(cep);
    }

    private EnderecoWrapper endereco() {
        return new EnderecoWrapper("71720-585",
                "Avenida Terceira Avenida Área Especial 2",
                "",
                "Núcleo Bandeirante",
                "Brasília",
                "DF",
                "61");
    }
}
