package io.github.expansionteam.battleships.logic.message;

import io.github.expansionteam.battleships.common.events.GenerateShipsEvent;
import io.github.expansionteam.battleships.common.events.ShootPositionEvent;
import io.github.expansionteam.battleships.common.events.StartGameEvent;
import org.json.JSONObject;

public class MessageFactory {

    public Message createFromEvent(StartGameEvent event) {
        JSONObject jsonObject = new JSONObject().put("type", "StartGameEvent");
        return new Message(jsonObject.toString());
    }

    public Message createFromEvent(GenerateShipsEvent event) {
        JSONObject jsonObject = new JSONObject().put("type", "GenerateShipsEvent");
        return new Message(jsonObject.toString());
    }

    public Message createFromEvent(ShootPositionEvent event) {
        JSONObject jsonObject = new JSONObject()
                .put("type", "ShootPositionEvent")
                .put("data", new JSONObject()
                        .put("position", new JSONObject()
                                .put("x", event.position.x)
                                .put("y", event.position.y)));

        return new Message(jsonObject.toString());
    }

    public Message createFromJson(String json) {
        return new Message(json);
    }

}
