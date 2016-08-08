package io.github.expansionteam.battleships.logic.message.responsemessageprocessors;

import com.google.common.eventbus.EventBus;
import io.github.expansionteam.battleships.common.events.ShipHitEvent;
import io.github.expansionteam.battleships.common.events.data.PositionData;
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

        log.debug("Post ShipHitEvent.");
        eventBus.post(new ShipHitEvent(PositionData.of(x, y)));
    }

}
