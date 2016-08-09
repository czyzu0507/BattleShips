package io.github.expansionteam.battleships.logic.message.responsemessageprocessors;

import com.google.common.eventbus.EventBus;
import io.github.expansionteam.battleships.common.events.ShipDestroyedEvent;
import io.github.expansionteam.battleships.common.events.data.PositionData;
import io.github.expansionteam.battleships.logic.message.Message;
import io.github.expansionteam.battleships.logic.message.ResponseMessageProcessor;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShipDestroyedEventResponseMessageProcessor implements ResponseMessageProcessor {

    private final static Logger log = Logger.getLogger(ShipDestroyedEventResponseMessageProcessor.class);

    private final EventBus eventBus;

    public ShipDestroyedEventResponseMessageProcessor(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void processResponseMessage(Message responseMessage) {
        JSONObject positionJsonObject = responseMessage.getData().getJSONObject("position");
        JSONArray adjacentPositionJsonObjects = responseMessage.getData().getJSONArray("adjacent");

        PositionData position = createPositionDataFromJsonObject(positionJsonObject);

        List<PositionData> adjacentPositions = new ArrayList<>();
        for (int i = 0; i < adjacentPositionJsonObjects.length(); i++) {
            JSONObject currentPositionJsonObject = adjacentPositionJsonObjects.getJSONObject(i);
            adjacentPositions.add(createPositionDataFromJsonObject(currentPositionJsonObject));
        }

        log.debug("Post ShipDestroyedEvent.");
        eventBus.post(new ShipDestroyedEvent(position, adjacentPositions));
    }

    private PositionData createPositionDataFromJsonObject(JSONObject positionJsonObject) {
        int x = positionJsonObject.getInt("x");
        int y = positionJsonObject.getInt("y");
        return PositionData.of(x, y);
    }

}
