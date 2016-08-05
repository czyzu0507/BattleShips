package io.github.expansionteam.battleships.logic;

import javafx.application.Platform;

public class AsyncTask {

    public void runLater(Runnable runnable) {
        Platform.runLater(runnable);
    }

}
