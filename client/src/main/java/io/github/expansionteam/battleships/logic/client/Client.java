package io.github.expansionteam.battleships.logic.client;

import com.google.inject.Inject;
import org.apache.log4j.Logger;

import java.util.concurrent.ExecutorService;

public class Client {

    private final static Logger log = Logger.getLogger(Client.class.getSimpleName());

    private final ExecutorService executorService;
    private final EventMessenger eventMessenger;

    @Inject
    public Client(ExecutorService executorService, EventMessenger eventMessenger) {
        this.executorService = executorService;
        this.eventMessenger = eventMessenger;
    }

    public void sendMessage(String message) {
        executorService.execute(() -> eventMessenger.send(message));
    }

}
