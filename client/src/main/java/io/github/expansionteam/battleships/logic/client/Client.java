package io.github.expansionteam.battleships.logic.client;

import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import io.github.expansionteam.battleships.logic.events.OpponentArrivedEvent;

public class Client {

    private EventBus eventBus;
    private SocketClient socketClient;

    @Inject
    public Client(EventBus eventBus, SocketClient socketClient) {
        this.eventBus = eventBus;
        this.socketClient = socketClient;
    }

    public void sendMessage(String message) {
        new Thread(() -> {
            String answer = socketClient.talkWithServer(message);
            eventBus.post(new OpponentArrivedEvent());
        }).start();
    }

}
