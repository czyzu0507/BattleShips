package io.github.expansionteam.battleships.logic;

import io.github.expansionteam.battleships.logic.events.StartGameEvent;
import org.json.JSONObject;

public class EventConverter {

    public String convertToJson(StartGameEvent event) {
        return new JSONObject().put("type", "StartGameEvent").toString();
    }

}
