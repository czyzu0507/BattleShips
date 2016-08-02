package io.github.expansionteam.battleships;

import com.google.common.eventbus.EventBus;
import io.github.expansionteam.battleships.gui.Client;
import io.github.expansionteam.battleships.logic.events.AnswerEvent;

public final class MessageThread implements Runnable {
    private final Client client;
    private final EventBus eventBus;
    private final String msg;

    public MessageThread(Client client, EventBus eventBus, String msg) {
        this.client = client;
        this.eventBus = eventBus;
        this.msg = msg;
    }

    private String sendMessage(String str) {
        return client.talkWithServer(str);
    }

    @Override
    public void run() {
        String answer = sendMessage(msg);
        System.out.println("Answer " + answer);
        eventBus.post( new AnswerEvent(answer) );
    }

    public Thread returnThread() {
        return new Thread(this);
    }
}
