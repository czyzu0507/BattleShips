package io.github.expansionteam.battleships.logic.message.responsemessageprocessors;

import com.google.common.eventbus.EventBus;
import io.github.expansionteam.battleships.common.events.ShipsGeneratedEvent;
import io.github.expansionteam.battleships.common.events.data.*;
import io.github.expansionteam.battleships.logic.message.Message;
import io.github.expansionteam.battleships.logic.message.ResponseMessageProcessor;
import org.apache.log4j.Logger;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class ShipsGeneratedResponseMessageProcessor implements ResponseMessageProcessor {

    private final static Logger log = Logger.getLogger(ShipsGeneratedResponseMessageProcessor.class);

    private final EventBus eventBus;

    public ShipsGeneratedResponseMessageProcessor(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void processResponseMessage(Message responseMessage) {
        JSONArray ships = responseMessage.getData().getJSONArray("ships");

        List<ShipData> shipsData = new ArrayList<>();
        for (int i = 0; i < ships.length(); i++) {
            PositionData positionData = PositionData.of(
                    ships.getJSONObject(i).getJSONObject("position").getInt("x"),
                    ships.getJSONObject(i).getJSONObject("position").getInt("y"));

            ShipSizeData sizeData = ShipSizeData.of(ships.getJSONObject(i).getInt("size"));

            ShipOrientationData orientationData;
            if (ships.getJSONObject(i).getString("orientation").equals("HORIZONTAL")) {
                orientationData = ShipOrientationData.HORIZONTAL;
            } else {
                orientationData = ShipOrientationData.VERTICAL;
            }

            shipsData.add(new ShipData(positionData, sizeData, orientationData));
        }

        NextTurnData nextTurn;
        if (responseMessage.getData().getString("nextTurn").equals("OPPONENT")) {
            nextTurn = NextTurnData.OPPONENT_TURN;
        } else {
            nextTurn = NextTurnData.PLAYER_TURN;
        }

        ShipsGeneratedEvent event = new ShipsGeneratedEvent(shipsData, nextTurn);
        eventBus.post(event);
        log.debug("Post: " + event.getClass().getSimpleName());
    }

}
