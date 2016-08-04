package io.github.expansionteam.battleships.logic;

import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import io.github.expansionteam.battleships.logic.events.OpponentArrivedEvent;
import io.github.expansionteam.battleships.logic.events.ShipsGeneratedEvent;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class EventProcessor {

    private final static Logger log = Logger.getLogger(EventProcessor.class);

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
                OpponentArrivedEvent opponentArrivedEvent = new OpponentArrivedEvent();
                eventBus.post(opponentArrivedEvent);
                break;
            case "ShipsGeneratedEvent":
                JSONArray ships = jsonObject.getJSONObject("data").getJSONArray("ships");

                ShipsGeneratedEvent shipsGeneratedEvent = new ShipsGeneratedEvent();
                for (int i = 0; i < ships.length(); i++) {
                    ShipsGeneratedEvent.Ship.Position position = new ShipsGeneratedEvent.Ship.Position(
                            ships.getJSONObject(i).getJSONObject("position").getInt("x"),
                            ships.getJSONObject(i).getJSONObject("position").getInt("y"));
                    int size = ships.getJSONObject(i).getInt("size");

                    ShipsGeneratedEvent.Ship.Orientation orientation;
                    if (ships.getJSONObject(i).getString("orientation").equals("HORIZONTAL")) {
                        orientation = ShipsGeneratedEvent.Ship.Orientation.HORIZONTAL;
                    } else {
                        orientation = ShipsGeneratedEvent.Ship.Orientation.VERTICAL;
                    }

                    shipsGeneratedEvent.ships.add(new ShipsGeneratedEvent.Ship(position, size, orientation));
                }
                eventBus.post(shipsGeneratedEvent);
                break;
            default:
                throw new AssertionError();
        }
    }

}
