package io.github.expansionteam.battleships.logic.message;

import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;
import io.github.expansionteam.battleships.logic.message.responsemessageprocessors.OpponentArrivedResponseMessageProcessor;
import io.github.expansionteam.battleships.logic.message.responsemessageprocessors.ShipsGeneratedResponseMessageProcessor;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class MessageProcessorProvider implements Provider<MessageProcessor> {

    private final static Logger log = Logger.getLogger(MessageProcessorProvider.class);

    private final EventBus eventBus;
    private final MessageSender messageSender;

    @Inject
    public MessageProcessorProvider(EventBus eventBus, MessageSender messageSender) {
        this.eventBus = eventBus;
        this.messageSender = messageSender;
    }

    @Override
    public MessageProcessor get() {
        Map<String, ResponseMessageProcessor> responseMessageProcessorsByType = new HashMap<>();
        responseMessageProcessorsByType.put("OpponentArrivedEvent", new OpponentArrivedResponseMessageProcessor(eventBus));
        responseMessageProcessorsByType.put("ShipsGeneratedEvent", new ShipsGeneratedResponseMessageProcessor(eventBus));

        return new MessageProcessor(eventBus, messageSender, responseMessageProcessorsByType);
    }

}
