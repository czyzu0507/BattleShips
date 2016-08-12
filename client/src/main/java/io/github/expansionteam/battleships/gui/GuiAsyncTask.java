package io.github.expansionteam.battleships.gui;

import javafx.application.Platform;

public class GuiAsyncTask {

    public void runLater(Runnable runnable) {
        Platform.runLater(runnable);
    }
}
