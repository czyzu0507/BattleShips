package io.github.expansionteam.battleships.logic.message;

import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import io.github.expansionteam.battleships.common.annotations.EventProducer;
import org.apache.log4j.Logger;

import java.util.Map;

@EventProducer
public class MessageProcessor {

    private final static Logger log = Logger.getLogger(MessageProcessor.class);

    private final EventBus eventBus;
    private final MessageSender messageSender;
    private final Map<String, ResponseMessageProcessor> responseMessageProcessorsByType;

    @Inject
    public MessageProcessor(
            EventBus eventBus,
            MessageSender messageSender,
            Map<String, ResponseMessageProcessor> responseMessageProcessorsByType) {
        this.eventBus = eventBus;
        this.messageSender = messageSender;
        this.responseMessageProcessorsByType = responseMessageProcessorsByType;
    }

    public void processMessage(Message message) {
        Message responseMessage = messageSender.sendMessageAndWaitForResponse(message);

        if (!responseMessageProcessorsByType.containsKey(responseMessage.getType())) {
            log.error("Unable to process this message: " + responseMessage);
            throw new IllegalArgumentException("Unable to process this message.");
        }

        responseMessageProcessorsByType.get(responseMessage.getType()).processResponseMessage(responseMessage);
    }

}
