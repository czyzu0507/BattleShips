package io.github.expansionteam.battleships.logic.client;

import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;

public class Client {

    @Inject
    private EventBus eventBus;

    @Inject
    private SocketClient socketClient;

    public void sendMessage(String message) {
        new Thread(new MessageThread(socketClient, eventBus, message)).start();
    }

}
