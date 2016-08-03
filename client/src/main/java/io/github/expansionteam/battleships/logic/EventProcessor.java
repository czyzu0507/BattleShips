package io.github.expansionteam.battleships.logic;

import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import io.github.expansionteam.battleships.MainLauncher;
import io.github.expansionteam.battleships.logic.events.OpponentArrivedEvent;
import org.apache.log4j.Logger;
import org.json.JSONObject;

public class EventProcessor {

    private final static Logger log = Logger.getLogger(EventProcessor.class.getSimpleName());

    private final EventBus eventBus;

    @Inject
    public EventProcessor(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void processJson(String json) {
        log.debug(json);

        JSONObject jsonObject = new JSONObject(json);
        switch (jsonObject.getString("type")) {
            case "OpponentArrivedEvent":
                eventBus.post(new OpponentArrivedEvent());
                break;
            default:
                throw new AssertionError();
        }
    }

}
