package io.github.expansionteam.battleships.logic;

import io.github.expansionteam.battleships.logic.events.GenerateShipsEvent;
import io.github.expansionteam.battleships.logic.events.StartGameEvent;
import org.json.JSONObject;

public class EventConverter {

    public String convertToJson(StartGameEvent event) {
        return new JSONObject().put("type", "StartGameEvent").toString();
    }

    public String convertToJson(GenerateShipsEvent event) {
        return new JSONObject().put("type", "GenerateShipsEvent").toString();
    }

}
