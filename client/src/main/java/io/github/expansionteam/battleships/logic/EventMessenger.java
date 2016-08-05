package io.github.expansionteam.battleships.logic;

import com.google.inject.Inject;
import io.github.expansionteam.battleships.logic.client.SocketClient;
import org.apache.log4j.Logger;

public class EventMessenger {

    private final static Logger log = Logger.getLogger(EventMessenger.class);

    private final SocketClient socketClient;
    private final EventProcessor eventProcessor;

    @Inject
    public EventMessenger(SocketClient socketClient, EventProcessor eventProcessor) {
        this.socketClient = socketClient;
        this.eventProcessor = eventProcessor;
    }

    public void send(String message) {
        log.debug(message);

        String jsonAnswer = socketClient.talkWithServer(message);
        eventProcessor.processJson(jsonAnswer);
    }

}
