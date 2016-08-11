package io.github.expansionteam.battleships.logic;

import java.util.concurrent.ExecutorService;

public class AsyncTask {

    private final ExecutorService executorService;

    public AsyncTask(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public void runLater(Runnable runnable) {
        executorService.execute(runnable);
    }

}
