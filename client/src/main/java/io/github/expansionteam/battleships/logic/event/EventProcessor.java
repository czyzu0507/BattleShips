package io.github.expansionteam.battleships.logic.event;

import com.google.inject.Inject;
import io.github.expansionteam.battleships.common.events.GenerateShipsEvent;
import io.github.expansionteam.battleships.common.events.StartGameEvent;
import io.github.expansionteam.battleships.logic.AsyncTask;
import io.github.expansionteam.battleships.logic.message.Message;
import io.github.expansionteam.battleships.logic.message.MessageFactory;
import io.github.expansionteam.battleships.logic.message.MessageProcessor;
import io.github.expansionteam.battleships.logic.message.ProcessMessageTask;
import org.apache.log4j.Logger;

public class EventProcessor {

    private final static Logger log = Logger.getLogger(EventProcessor.class);

    private final AsyncTask asyncTask;
    private final MessageFactory messageFactory;
    private final MessageProcessor messageProcessor;

    @Inject
    public EventProcessor(AsyncTask asyncTask, MessageFactory messageFactory, MessageProcessor messageProcessor) {
        this.asyncTask = asyncTask;
        this.messageFactory = messageFactory;
        this.messageProcessor = messageProcessor;
    }

    public void processEvent(StartGameEvent event) {
        log.debug("Process StartGameEvent.");

        Message message = messageFactory.createFromEvent(event);
        asyncTask.runLater(new ProcessMessageTask(messageProcessor, message));
    }

    public void processEvent(GenerateShipsEvent event) {
        log.debug("Process GenerateShipsEvent.");

        Message message = messageFactory.createFromEvent(event);
        asyncTask.runLater(new ProcessMessageTask(messageProcessor, message));
    }

}
