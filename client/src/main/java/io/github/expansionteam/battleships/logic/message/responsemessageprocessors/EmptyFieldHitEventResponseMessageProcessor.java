package io.github.expansionteam.battleships.logic.message.responsemessageprocessors;

import com.google.common.eventbus.EventBus;
import io.github.expansionteam.battleships.common.events.EmptyFieldHitEvent;
import io.github.expansionteam.battleships.common.events.data.PositionData;
import io.github.expansionteam.battleships.logic.message.Message;
import io.github.expansionteam.battleships.logic.message.ResponseMessageProcessor;
import org.apache.log4j.Logger;
import org.json.JSONObject;

public class EmptyFieldHitEventResponseMessageProcessor implements ResponseMessageProcessor {

    private final static Logger log = Logger.getLogger(EmptyFieldHitEventResponseMessageProcessor.class);

    private final EventBus eventBus;

    public EmptyFieldHitEventResponseMessageProcessor(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void processResponseMessage(Message responseMessage) {
        JSONObject positionJsonObject = responseMessage.getData().getJSONObject("position");
        int x = positionJsonObject.getInt("x");
        int y = positionJsonObject.getInt("y");

        log.trace("Post EmptyFieldHitEvent.");
        eventBus.post(new EmptyFieldHitEvent(PositionData.of(x, y)));
    }

}
