package com.edneiaugusto.paymentsystem.system.thread;

import com.edneiaugusto.paymentsystem.util.Constants;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ThreadPool {

    private final ExecutorService executorServiceSingle;
    private final ExecutorService executorServiceCached;
    private final ExecutorService executorServiceFixed;

    @Value("${spring.profiles.active}")
    private String profile;

    public ThreadPool() {
        executorServiceSingle = Executors.newSingleThreadExecutor(new ThreadFactoryBuilder().setNameFormat("paymentsystem-single-thread-pool").build());
        executorServiceCached = Executors.newCachedThreadPool(new ThreadFactoryBuilder().setNameFormat("paymentsystem-cached-thread-pool").build());
        executorServiceFixed = Executors.newFixedThreadPool(20, new ThreadFactoryBuilder().setNameFormat("paymentsystem-fixed-thread-pool-20").build());
    }

    /**
     * Adiciona uma tarefa na fila de Execução. Nesse caso o sistema vai executar uma a uma por ordem de chegada em uma thread separada.
     *
     * @param tarefa um Runnable
     * @see ThreadPool
     */
    public void enfileirarTarefa(Runnable tarefa) {
        executar(executorServiceSingle, tarefa);
    }

    /**
     * Executa uma tarefa imediatamente em uma thread separada.
     *
     * @param tarefa um Runnable
     * @see ThreadPool
     */
    public void executarTarefa(Runnable tarefa) {
        executar(executorServiceCached, tarefa);
    }

    /**
     * Adiciona uma tarefa na fila de Execução. No entanto, nesse caso o sistema vai permitir a execução de até 10 thread simultâneas, somente ao exceder as 10
     * que o sistema faz a gestão em fila.
     *
     * @param tarefa um Runnable
     * @see ThreadPool
     */
    public void enfileirarTarefaFixed(Runnable tarefa) {
        executar(executorServiceFixed, tarefa);
    }

    private void executar(ExecutorService executorService, Runnable runnable) {
        if (Objects.equals(profile, Constants.SPRING_PROFILE_TESTE)) {
            runnable.run();
        } else {
            executorService.execute(runnable);
        }
    }

}
