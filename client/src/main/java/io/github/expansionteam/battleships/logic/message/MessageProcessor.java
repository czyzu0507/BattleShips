package io.github.expansionteam.battleships.logic.message;

import com.google.inject.Inject;
import io.github.expansionteam.battleships.common.annotations.EventProducer;
import org.apache.log4j.Logger;

import java.util.Map;

@EventProducer
public class MessageProcessor {

    private final static Logger log = Logger.getLogger(MessageProcessor.class);

    private final MessageSender messageSender;
    private final Map<String, ResponseMessageProcessor> responseMessageProcessorsByType;

    @Inject
    public MessageProcessor(
            MessageSender messageSender,
            Map<String, ResponseMessageProcessor> responseMessageProcessorsByType) {
        this.messageSender = messageSender;
        this.responseMessageProcessorsByType = responseMessageProcessorsByType;
    }

    void processMessage(Message message) {
        Message responseMessage = messageSender.sendMessageAndWaitForResponse(message);
        log.debug("Processing message: " + responseMessage);

        if (!responseMessageProcessorsByType.containsKey(responseMessage.getType())) {
            log.error("Unable to process this message: " + responseMessage);
            throw new IllegalArgumentException("Unable to process this message.");
        }

        responseMessageProcessorsByType.get(responseMessage.getType()).processResponseMessage(responseMessage);
    }

}
