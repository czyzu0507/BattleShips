package io.github.expansionteam.battleships.logic;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import io.github.expansionteam.battleships.MainLauncher;
import io.github.expansionteam.battleships.gui.Client;
import io.github.expansionteam.battleships.logic.events.StartGameEvent;
import org.apache.log4j.Logger;

public class EventHandler {

    private final static Logger log = Logger.getLogger(MainLauncher.class.getSimpleName());

    @Inject
    private Client client;

    @Subscribe
    public void handle(StartGameEvent event) {
        log.debug("Game was started.");
        client.talkWithServer();
    }

}
