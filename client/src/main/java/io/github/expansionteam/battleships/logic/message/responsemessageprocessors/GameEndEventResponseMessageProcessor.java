package io.github.expansionteam.battleships.logic.message.responsemessageprocessors;

import com.google.common.eventbus.EventBus;
import io.github.expansionteam.battleships.common.events.GameEndEvent;
import io.github.expansionteam.battleships.common.events.data.NextTurnData;
import io.github.expansionteam.battleships.common.events.data.PositionData;
import io.github.expansionteam.battleships.common.events.opponentboard.OpponentGameEndEvent;
import io.github.expansionteam.battleships.common.events.opponentboard.OpponentShipDestroyedEvent;
import io.github.expansionteam.battleships.common.events.playerboard.PlayerGameEndEvent;
import io.github.expansionteam.battleships.common.events.playerboard.PlayerShipDestroyedEvent;
import io.github.expansionteam.battleships.logic.message.BoardOwner;
import io.github.expansionteam.battleships.logic.message.Message;
import io.github.expansionteam.battleships.logic.message.ResponseMessageProcessor;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GameEndEventResponseMessageProcessor implements ResponseMessageProcessor {

    private final static Logger log = Logger.getLogger(GameEndEventResponseMessageProcessor.class);

    private final EventBus eventBus;

    public GameEndEventResponseMessageProcessor(EventBus eventBus) {
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

        if (responseMessage.getBoardOwner().equals(BoardOwner.OPPONENT)) {
            OpponentGameEndEvent event = new OpponentGameEndEvent(position, adjacentPositions);

            eventBus.post(event);
            log.debug("Post: " + event.getClass().getSimpleName());
        } else {
            PlayerGameEndEvent event = new PlayerGameEndEvent(position, adjacentPositions);

            eventBus.post(event);
            log.debug("Post: " + event.getClass().getSimpleName());
        }
    }

    private PositionData createPositionDataFromJsonObject(JSONObject positionJsonObject) {
        int x = positionJsonObject.getInt("x");
        int y = positionJsonObject.getInt("y");
        return PositionData.of(x, y);
    }

}
