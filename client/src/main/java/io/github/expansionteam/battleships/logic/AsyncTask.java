package io.github.expansionteam.battleships.logic;

import javafx.application.Platform;
import javafx.concurrent.Task;

public class AsyncTask {

    public void runLater(Runnable runnable) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Platform.runLater(runnable);
                return null;
            }
        };

        new Thread(task).start();
    }
}
