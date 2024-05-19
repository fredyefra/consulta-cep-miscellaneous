package br.com.main.utils;

import br.com.main.bean.EnderecoWrapper;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@ApplicationScoped
public final class DelayUtils {

    private static final Long TEMPO_EM_MILISEGUNDOS = 4000L;

    public void atraso(List<EnderecoWrapper> enderecos) {
        try {
            Thread.sleep(TEMPO_EM_MILISEGUNDOS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void atraso2(List<EnderecoWrapper> enderecos) {
        try {
            Thread.sleep(TEMPO_EM_MILISEGUNDOS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void atraso3(EnderecoWrapper endereco) {

        CompletableFuture<String> f = CompletableFuture.supplyAsync(() -> {

            try {
                Thread.sleep(TEMPO_EM_MILISEGUNDOS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return Thread.currentThread().getName();
        });
    }
}
