package io.github.expansionteam.battleships.logic;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import io.github.expansionteam.battleships.MainLauncher;
import io.github.expansionteam.battleships.logic.events.OpponentArrivedEvent;
import io.github.expansionteam.battleships.logic.events.StartGameEvent;
import org.apache.log4j.Logger;

public class EventHandler {

    private final static Logger log = Logger.getLogger(MainLauncher.class.getSimpleName());
    
    @Inject
    private EventBus eventBus;

    @Subscribe
    public void handle(StartGameEvent event) {
        log.debug("Game was started.");
        eventBus.post(new OpponentArrivedEvent());
    }

}
