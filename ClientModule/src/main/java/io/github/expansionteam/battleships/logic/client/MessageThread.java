package io.github.expansionteam.battleships.logic.client;

import com.google.common.eventbus.EventBus;
import io.github.expansionteam.battleships.logic.events.OpponentArrivedEvent;

public final class MessageThread implements Runnable {

    private final SocketClient socketClient;
    private final EventBus eventBus;
    private final String msg;

    public MessageThread(SocketClient socketClient, EventBus eventBus, String msg) {
        this.socketClient = socketClient;
        this.eventBus = eventBus;
        this.msg = msg;
    }

    private String sendMessage(String str) {
        return socketClient.talkWithServer(str);
    }

    @Override
    public void run() {
        String answer = sendMessage(msg);
        eventBus.post(new OpponentArrivedEvent());
    }

}
