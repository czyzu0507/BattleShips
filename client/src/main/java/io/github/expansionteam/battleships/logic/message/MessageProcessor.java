package io.github.expansionteam.battleships.logic.message;

import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import io.github.expansionteam.battleships.common.annotations.EventProducer;
import io.github.expansionteam.battleships.common.events.OpponentArrivedEvent;
import io.github.expansionteam.battleships.common.events.ShipsGeneratedEvent;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

@EventProducer
public class MessageProcessor {

    private final static Logger log = Logger.getLogger(MessageProcessor.class);

    private final EventBus eventBus;
    private final MessageSender messageSender;

    @Inject
    public MessageProcessor(EventBus eventBus, MessageSender messageSender) {
        this.eventBus = eventBus;
        this.messageSender = messageSender;
    }

    public void processMessage(Message message) {
        Message response = messageSender.sendMessageAndWaitForResponse(message);

        JSONObject jsonObject = new JSONObject(response.getBody());
        switch (jsonObject.getString("type")) {
            case "OpponentArrivedEvent":
                log.debug("Post OpponentArrivedEvent.");
                eventBus.post(new OpponentArrivedEvent());
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

                log.debug("Post ShipsGeneratedEvent.");
                eventBus.post(shipsGeneratedEvent);
                break;
            default:
                log.debug("Unable to process the messages.");
                throw new AssertionError();
        }
    }

}
