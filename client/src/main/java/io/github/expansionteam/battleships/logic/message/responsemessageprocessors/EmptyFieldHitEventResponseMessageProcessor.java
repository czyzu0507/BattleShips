package io.github.expansionteam.battleships.logic.message.responsemessageprocessors;

import com.google.common.eventbus.EventBus;
import io.github.expansionteam.battleships.common.events.data.NextTurnData;
import io.github.expansionteam.battleships.common.events.data.PositionData;
import io.github.expansionteam.battleships.common.events.opponentboard.OpponentEmptyFieldHitEvent;
import io.github.expansionteam.battleships.common.events.playerboard.PlayerEmptyFieldHitEvent;
import io.github.expansionteam.battleships.logic.message.BoardOwner;
import io.github.expansionteam.battleships.logic.message.Message;
import io.github.expansionteam.battleships.logic.message.ResponseMessageProcessor;
import org.apache.log4j.Logger;

public class EmptyFieldHitEventResponseMessageProcessor implements ResponseMessageProcessor {

    private final static Logger log = Logger.getLogger(EmptyFieldHitEventResponseMessageProcessor.class);

    private final EventBus eventBus;

    public EmptyFieldHitEventResponseMessageProcessor(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void processResponseMessage(Message responseMessage) {
        int x = responseMessage.getData().getJSONObject("position").getInt("x");
        int y = responseMessage.getData().getJSONObject("position").getInt("y");

        NextTurnData nextTurn;
        if (responseMessage.getData().getString("nextTurn").equals("OPPONENT")) {
            nextTurn = NextTurnData.OPPONENT_TURN;
        } else {
            nextTurn = NextTurnData.PLAYER_TURN;
        }

        if (responseMessage.getBoardOwner().equals(BoardOwner.OPPONENT)) {
            OpponentEmptyFieldHitEvent event = new OpponentEmptyFieldHitEvent(PositionData.of(x, y), nextTurn);
            eventBus.post(event);

            log.debug("Post: " + event.getClass().getSimpleName());
        } else {
            PlayerEmptyFieldHitEvent event = new PlayerEmptyFieldHitEvent(PositionData.of(x, y), nextTurn);
            eventBus.post(event);

            log.debug("Post: " + event.getClass().getSimpleName());
        }
    }

}
