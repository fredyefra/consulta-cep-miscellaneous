package br.com.main.utils;

import br.com.main.bean.EnderecoWrapper;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.helpers.spies.MultiOnRequestSpy;
import io.smallrye.mutiny.helpers.spies.Spy;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import io.vertx.core.net.impl.pool.Executor;

import java.util.concurrent.ScheduledExecutorService;

//@ApplicationScoped
public class SpyEventsUtils {

    public MultiOnRequestSpy<EnderecoWrapper> requestSpy(Multi<EnderecoWrapper> ceps){

        MultiOnRequestSpy<EnderecoWrapper> requestSpy = Spy.onRequest(ceps);
        MultiOnRequestSpy<EnderecoWrapper> completionSpy = Spy.onRequest(requestSpy);

        completionSpy.subscribe().with(System.out::println);
        System.out.println("Numero de requisições: " + requestSpy.requestedCount());
        System.out.println("Completas ? " + completionSpy.invoked());

        return completionSpy;
    }


    public static void main(String[] args) {
        Executor<Integer> c = action -> {
            action.execute(123);
        };
    }

}
