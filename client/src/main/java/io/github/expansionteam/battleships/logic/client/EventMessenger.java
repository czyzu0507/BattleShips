package io.github.expansionteam.battleships.logic.client;

import com.google.inject.Inject;
import io.github.expansionteam.battleships.logic.EventProcessor;
import org.apache.log4j.Logger;

public class EventMessenger {

    private final static Logger log = Logger.getLogger(EventMessenger.class.getSimpleName());

    private final SocketClient socketClient;
    private final EventProcessor eventProcessor;

    @Inject
    public EventMessenger(SocketClient socketClient, EventProcessor eventProcessor) {
        this.socketClient = socketClient;
        this.eventProcessor = eventProcessor;
    }

    public void send(String message) {
        String jsonAnswer = socketClient.talkWithServer(message);
        eventProcessor.processJson(jsonAnswer);
    }

}
