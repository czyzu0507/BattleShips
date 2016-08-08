package io.github.expansionteam.battleships.logic.event;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import io.github.expansionteam.battleships.common.annotations.EventConsumer;
import io.github.expansionteam.battleships.common.events.GenerateShipsEvent;
import io.github.expansionteam.battleships.common.events.ShootPositionEvent;
import io.github.expansionteam.battleships.common.events.StartGameEvent;
import org.apache.log4j.Logger;

@EventConsumer
public class EventHandler {

    private final static Logger log = Logger.getLogger(EventHandler.class);

    private final EventProcessor eventProcessor;

    @Inject
    public EventHandler(EventProcessor eventProcessor) {
        this.eventProcessor = eventProcessor;
    }

    @Subscribe
    public void handleStartGameEvent(StartGameEvent event) {
        log.trace("Handle StartGameEvent.");
        eventProcessor.processEvent(event);
    }


    @Subscribe
    public void handleGenerateShipsEvent(GenerateShipsEvent event) {
        log.trace("Handle GenerateShipsEvent.");
        eventProcessor.processEvent(event);
    }

    @Subscribe
    public void handleShootPositionEvent(ShootPositionEvent event) {
        log.trace("Handle ShootPositionEvent.");
        eventProcessor.processEvent(event);
    }

}
