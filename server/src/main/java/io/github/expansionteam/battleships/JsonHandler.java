package io.github.expansionteam.battleships;

import io.github.expansionteam.battleships.engine.Field;
import io.github.expansionteam.battleships.engine.Orientation;
import io.github.expansionteam.battleships.engine.Ship;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collection;

public class JsonHandler {

    String resolveAction(String json, Game game) {
        JSONObject jsonRequest = new JSONObject(json);
        String type = jsonRequest.getString("type");
        JSONObject jsonResponse = new JSONObject();

        switch (type) {
            case "StartGameEvent": {
                return getOpponentArrivedEventJson(jsonResponse);
            }

            case "GenerateShipsEvent": {
                game.generateRandomShips();
                Collection<Ship> ships = game.getPlayerShips();

                return getShipsGeneratedEventJson(jsonResponse, ships);
            }
            case "ShootPositionEvent": {

                JSONObject position = jsonRequest.getJSONObject("position");

                int x = position.getInt("x");
                int y = position.getInt("y");

                if (game.shoot(x, y)) {
                    return getShipHitEventJson(jsonResponse, position);
                }

                return getEmptyFieldHitEventJson(jsonResponse, position);
            }
            default:
                return getNotRecognizedEventJson(jsonResponse);
        }
    }

    private String getNotRecognizedEventJson(JSONObject jsonResponse) {
        return jsonResponse
                .put("type", "NotRecognizeEvent")
                .toString();
    }

    private String getOpponentArrivedEventJson(JSONObject jsonResponse) {
        return jsonResponse
                .put("type", "OpponentArrivedEvent")
                .toString();
    }

    private String getShipsGeneratedEventJson(JSONObject jsonResponse, Collection<Ship> ships) {
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

    private String getShipHitEventJson(JSONObject jsonResponse, JSONObject position) {
        return jsonResponse
                .put("type", "ShipHitEvent")
                .put("position", position)
                .toString();
    }

    private String getEmptyFieldHitEventJson(JSONObject jsonResponse, JSONObject position) {
        return jsonResponse
                .put("type", "EmptyFieldHitEvent")
                .put("position", position)
                .toString();
    }
}