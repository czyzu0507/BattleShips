package io.github.expansionteam.battleships.logic;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import io.github.expansionteam.battleships.MainLauncher;
import io.github.expansionteam.battleships.logic.client.Client;
import io.github.expansionteam.battleships.logic.events.GenerateShipsEvent;
import io.github.expansionteam.battleships.logic.events.StartGameEvent;
import org.apache.log4j.Logger;

public class EventHandler {

    private final static Logger log = Logger.getLogger(EventHandler.class);

    @Inject
    private Client client;

    @Inject
    private EventConverter eventConverter;

    @Subscribe
    public void handleStartGameEvent(StartGameEvent event) {
        log.debug("Handle StartGameEvent.");
        client.sendMessage(eventConverter.convertToJson(event));
    }

    @Subscribe
    public void handleGenerateShipsEvent(GenerateShipsEvent event) {
        log.debug("Handle GenerateShipsEvent.");
        client.sendMessage(eventConverter.convertToJson(event));
    }

}
