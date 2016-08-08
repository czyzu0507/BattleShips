package io.github.expansionteam.battleships.logic.message.responsemessageprocessors;

import com.google.common.eventbus.EventBus;
import io.github.expansionteam.battleships.common.events.ShipsGeneratedEvent;
import io.github.expansionteam.battleships.logic.message.Message;
import io.github.expansionteam.battleships.logic.message.ResponseMessageProcessor;
import org.apache.log4j.Logger;
import org.json.JSONArray;

public class ShipsGeneratedResponseMessageProcessor implements ResponseMessageProcessor {

    private final static Logger log = Logger.getLogger(ShipsGeneratedResponseMessageProcessor.class);

    private final EventBus eventBus;

    public ShipsGeneratedResponseMessageProcessor(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void processResponseMessage(Message responseMessage) {
        JSONArray ships = responseMessage.getData().getJSONArray("ships");

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

        log.trace("Post ShipsGeneratedEvent.");
        eventBus.post(shipsGeneratedEvent);
    }

}
