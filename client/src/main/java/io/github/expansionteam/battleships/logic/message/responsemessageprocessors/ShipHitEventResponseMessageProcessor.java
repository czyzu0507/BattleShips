package io.github.expansionteam.battleships.logic.message.responsemessageprocessors;

import com.google.common.eventbus.EventBus;
import io.github.expansionteam.battleships.common.events.ShipHitEvent;
import io.github.expansionteam.battleships.common.events.data.NextTurnData;
import io.github.expansionteam.battleships.common.events.data.PositionData;
import io.github.expansionteam.battleships.common.events.opponentboard.OpponentShipHitEvent;
import io.github.expansionteam.battleships.common.events.playerboard.PlayerShipHitEvent;
import io.github.expansionteam.battleships.logic.message.BoardOwner;
import io.github.expansionteam.battleships.logic.message.Message;
import io.github.expansionteam.battleships.logic.message.ResponseMessageProcessor;
import org.apache.log4j.Logger;
import org.json.JSONObject;

public class ShipHitEventResponseMessageProcessor implements ResponseMessageProcessor {

    private final static Logger log = Logger.getLogger(ShipHitEventResponseMessageProcessor.class);

    private final EventBus eventBus;

    public ShipHitEventResponseMessageProcessor(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void processResponseMessage(Message responseMessage) {
        JSONObject positionJsonObject = responseMessage.getData().getJSONObject("position");
        int x = positionJsonObject.getInt("x");
        int y = positionJsonObject.getInt("y");

        NextTurnData nextTurn;
        if (responseMessage.getData().getString("nextTurn").equals("OPPONENT")) {
            nextTurn = NextTurnData.OPPONENT_TURN;
        } else {
            nextTurn = NextTurnData.PLAYER_TURN;
        }

        if (responseMessage.getBoardOwner().equals(BoardOwner.OPPONENT)) {
            log.debug("Post OpponentShipHitEvent.");
            eventBus.post(new OpponentShipHitEvent(PositionData.of(x, y), nextTurn));
        } else {
            log.debug("Post PlayerShipHitEvent.");
            eventBus.post(new PlayerShipHitEvent(PositionData.of(x, y), nextTurn));
        }
    }

}
