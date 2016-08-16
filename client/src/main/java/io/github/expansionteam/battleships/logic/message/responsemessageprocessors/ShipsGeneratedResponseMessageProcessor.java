package io.github.expansionteam.battleships.logic.message.responsemessageprocessors;

import com.google.common.eventbus.EventBus;
import io.github.expansionteam.battleships.common.events.ShipsGeneratedEvent;
import io.github.expansionteam.battleships.common.events.data.*;
import io.github.expansionteam.battleships.logic.message.Message;
import io.github.expansionteam.battleships.logic.message.ResponseMessageProcessor;
import jdk.nashorn.api.scripting.JSObject;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShipsGeneratedResponseMessageProcessor implements ResponseMessageProcessor {

    private final static Logger log = Logger.getLogger(ShipsGeneratedResponseMessageProcessor.class);

    private final EventBus eventBus;

    public ShipsGeneratedResponseMessageProcessor(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    private boolean isItEqual(JSONObject jsonObject, String string1, String string2){
        return jsonObject.getString(string1).equals(string2);
    }

    @Override
    public void processResponseMessage(Message responseMessage) {
        JSONArray ships = responseMessage.getData().getJSONArray("ships");

        List<ShipData> shipsData = new ArrayList<>();
        for (int i = 0; i < ships.length(); i++) {
            JSONObject jsonObject = ships.getJSONObject(i).getJSONObject("position");
            PositionData positionData = PositionData.of(jsonObject.getInt("x"), jsonObject.getInt("y"));

            ShipSizeData sizeData = ShipSizeData.of(ships.getJSONObject(i).getInt("size"));

            ShipOrientationData orientationData;
            if (isItEqual(ships.getJSONObject(i), "orientation", "HORIZONTAL")) {
                orientationData = ShipOrientationData.HORIZONTAL;
            } else {
                orientationData = ShipOrientationData.VERTICAL;
            }

            shipsData.add(new ShipData(positionData, sizeData, orientationData));
        }

        NextTurnData nextTurn;
        if (isItEqual(responseMessage.getData(), "nextTurn", "OPPONENT")) {
            nextTurn = NextTurnData.OPPONENT_TURN;
        } else {
            nextTurn = NextTurnData.PLAYER_TURN;
        }

        ShipsGeneratedEvent event = new ShipsGeneratedEvent(shipsData, nextTurn);
        eventBus.post(event);
        log.debug("Post: " + event.getClass().getSimpleName());
    }

}
