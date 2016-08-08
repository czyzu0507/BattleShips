package io.github.expansionteam.battleships.logic.message;

import io.github.expansionteam.battleships.common.events.GenerateShipsEvent;
import io.github.expansionteam.battleships.common.events.ShootPositionEvent;
import io.github.expansionteam.battleships.common.events.StartGameEvent;
import org.json.JSONException;
import org.json.JSONObject;

public class MessageFactory {

    public Message createFromEvent(StartGameEvent event) {
        return new Message("StartGameEvent", new JSONObject());
    }

    public Message createFromEvent(GenerateShipsEvent event) {
        return new Message("GenerateShipsEvent", new JSONObject());
    }

    public Message createFromEvent(ShootPositionEvent event) {
        return new Message("ShootPositionEvent", new JSONObject()
                .put("position", new JSONObject()
                        .put("x", event.getPosition().getX())
                        .put("y", event.getPosition().getY())));
    }

    public Message createFromJson(String jsonText) {
        try {
            JSONObject jsonObject = new JSONObject(jsonText);
            String type = jsonObject.getString("type");
            JSONObject data = jsonObject.getJSONObject("data");

            return new Message(type, data);
        } catch (JSONException ex) {
            throw new IllegalArgumentException("The jsonText argument must be a valid json text that represents a message.");
        }
    }

}
