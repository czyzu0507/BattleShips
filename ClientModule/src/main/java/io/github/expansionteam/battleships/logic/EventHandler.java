package io.github.expansionteam.battleships.logic;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import io.github.expansionteam.battleships.MainLauncher;
import io.github.expansionteam.battleships.logic.events.OpponentArrivedEvent;
import io.github.expansionteam.battleships.MessageThread;
import io.github.expansionteam.battleships.gui.Client;
import io.github.expansionteam.battleships.logic.events.StartGameEvent;
import org.apache.log4j.Logger;

import java.util.Random;

public class EventHandler {

    private final static Logger log = Logger.getLogger(MainLauncher.class.getSimpleName());
    
    @Inject
    private EventBus eventBus;

    @Inject
    private Client client;

    @Subscribe
    public void handle(StartGameEvent event) {
        log.debug("[LOGIC] Handle StartGameEvent.");
        eventBus.post(new OpponentArrivedEvent());
        log.debug("Game was started.");

        new MessageThread(client, eventBus, "sdasdsadsadasdsadasdasd" + new Random().nextInt(100)).returnThread().start();

        System.out.println("wyslano");
    }

}
