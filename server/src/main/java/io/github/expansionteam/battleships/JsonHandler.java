package io.github.expansionteam.battleships;

import io.github.expansionteam.battleships.engine.Field;
import io.github.expansionteam.battleships.engine.Orientation;
import io.github.expansionteam.battleships.engine.Ship;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collection;

public class JsonHandler {

    String resolveAction(String json, Game game) {
        JSONObject jsonObj = new JSONObject(json);
        String type = jsonObj.getString("type");
        JSONObject jsonResponse = new JSONObject();

        switch (type) {

            case "StartGameEvent": {
                return jsonResponse
                        .put("type", "OpponentArrivedEvent")
                        .toString();
            }

            case "GenerateShipsEvent": {
                game.generateRandomShips();
                Collection<Ship> ships = game.getPlayerShips();

                JSONArray shipsJson = new JSONArray();

                int i = 0;
                for (Ship ship : ships) {
                    Field startField = ship.getStartField();
                    Orientation orientation = ship.getOrientation();
                    int size = ship.getSize();

                    JSONObject position = new JSONObject()
                            .put("x", startField.getX())
                            .put("y", startField.getY());

                    JSONObject shipJson = new JSONObject()
                            .put("position", position)
                            .put("orientation", orientation.toString())
                            .put("size", size);

                    shipsJson.put(i++, shipJson);
                }

                JSONObject data = new JSONObject().put("ships", shipsJson);

                return jsonResponse
                        .put("type", "ShipsGeneratedEvent")
                        .put("data", data)
                        .toString();
            }
            default:
                return jsonResponse
                        .put("type", "NotRecognizeEvent")
                        .toString();
        }
    }
}
