package br.com.main.impl;

import br.com.main.bean.EnderecoWrapper;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.helpers.test.UniAssertSubscriber;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Duration;

@QuarkusTest
public class ConsultaCepServiceImplTest {

    //https://javadoc.io/doc/io.smallrye.reactive/mutiny/1.1.2/io/smallrye/mutiny/helpers/test/UniAssertSubscriber.html

    @InjectMock
    ConsultaCepServiceImpl service;

    @BeforeEach
    public void init(){
        //ConsultaCepServiceImpl mockCep = Mockito.mock(ConsultaCepServiceImpl.class);
        Mockito.when(service.consultarPorCep("71720585")).thenReturn(Uni.createFrom().item(this::endereco));
    }
    
    @Test
    public void testDevolveEnderecoCompletoPeloCep() {

        EnderecoWrapper atual = service.consultarPorCep("71720585")
                .subscribe()
                .withSubscriber(UniAssertSubscriber.create())
                .awaitItem(Duration.ofSeconds(5))
                .getItem();

        EnderecoWrapper expectativa = endereco();

        Assertions.assertEquals(expectativa, atual, ()-> "Os endereços não são iguais!");

    }


    //TODO // java.lang.AssertionError:
    //        //Expected a completion event, but didn't received it.
    //        //https://github.com/smallrye/smallrye-mutiny/discussions/1057
    @Test
    public void testDevolveEnderecoCompletoPeloCepAssinado() {

        EnderecoWrapper expectativa = endereco();

        UniAssertSubscriber<EnderecoWrapper> enderecoSubscriber = service.consultarPorCep("71720585")
                .invoke(atual -> Assertions.assertEquals(expectativa, atual, ()-> "Os endereços não são iguais!"))
                .subscribe()
                .withSubscriber(UniAssertSubscriber.create());

        enderecoSubscriber
                .awaitItem(Duration.ofSeconds(5))
                .assertCompleted();
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

    //@Test
//    public void testDevolveEnderecoCompletoPeloCepAssinado() {
//
//        EnderecoWrapper expectativa = endereco();
//
//        UniAssertSubscriber<EnderecoWrapper> enderecoSubscriber = consultarPorCep("71720585")
//                .invoke(atual -> Assertions.assertEquals(expectativa, atual, ()-> "Os endereços não são iguais!"))
//                .subscribe()
//                .withSubscriber(UniAssertSubscriber.create());
//
//        enderecoSubscriber
//            .awaitItem(Duration.ofSeconds(10))
//            .assertCompleted();
//    }

}
