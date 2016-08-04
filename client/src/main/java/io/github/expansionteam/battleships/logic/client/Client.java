package io.github.expansionteam.battleships.logic.client;

import com.google.inject.Inject;
import io.github.expansionteam.battleships.logic.EventMessenger;
import javafx.application.Platform;
import org.apache.log4j.Logger;

public class Client {

    private final static Logger log = Logger.getLogger(Client.class.getSimpleName());

    private final EventMessenger eventMessenger;

    @Inject
    public Client(EventMessenger eventMessenger) {
        this.eventMessenger = eventMessenger;
    }

    public void sendMessage(String message) {
        Platform.runLater(() -> eventMessenger.send(message));
    }

}
