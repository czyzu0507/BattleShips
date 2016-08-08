package io.github.expansionteam.battleships.logic.message.responsemessageprocessors;

import com.google.common.eventbus.EventBus;
import io.github.expansionteam.battleships.common.events.OpponentArrivedEvent;
import io.github.expansionteam.battleships.logic.message.Message;
import io.github.expansionteam.battleships.logic.message.ResponseMessageProcessor;
import org.apache.log4j.Logger;

public class OpponentArrivedResponseMessageProcessor implements ResponseMessageProcessor {

    private final static Logger log = Logger.getLogger(OpponentArrivedResponseMessageProcessor.class);

    private final EventBus eventBus;

    public OpponentArrivedResponseMessageProcessor(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void processResponseMessage(Message responseMessage) {
        log.trace("Post OpponentArrivedEvent.");
        eventBus.post(new OpponentArrivedEvent());
    }

}
