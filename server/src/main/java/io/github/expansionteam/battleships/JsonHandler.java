package io.github.expansionteam.battleships;

import io.github.expansionteam.battleships.engine.Field;
import io.github.expansionteam.battleships.engine.Orientation;
import io.github.expansionteam.battleships.engine.Ship;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collection;

class JsonHandler {

    String resolveAction(String json, Game game) {
        final JSONObject jsonRequest = new JSONObject(json);
        final String type = jsonRequest.getString("type");
        final JSONObject jsonResponse = new JSONObject();

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

                JSONObject position = jsonRequest.getJSONObject("data").getJSONObject("position");

                int x = position.getInt("x");
                int y = position.getInt("y");

                if (!game.shoot(x, y)) {
                    return getFieldAlreadyShotEvent(jsonResponse, position);
                }

                if (!game.isShipHit(x, y)) {
                    return getEmptyFieldHitEventJson(jsonResponse, position);
                }

                if (!game.isDestroyedShip(x, y)) {
                    return getShipHitEventJson(jsonResponse, position);
                }

                return getShipDestroyedEventJson(jsonResponse, position, game.getAdjacentToShip(x, y));
            }
            default:
                return getNotRecognizedEventJson(jsonResponse);
        }
    }

    private String getJsonEvent(JSONObject jsonResponse, String eventType, JSONObject data) {
        return jsonResponse
                .put("type", eventType)
                .put("data", data)
                .toString();
    }

    private String getNotRecognizedEventJson(JSONObject jsonResponse) {
        return getJsonEvent(jsonResponse, "NotRecognizeEvent", new JSONObject());
    }

    private String getOpponentArrivedEventJson(JSONObject jsonResponse) {
        return getJsonEvent(jsonResponse, "OpponentArrivedEvent", new JSONObject());
    }

    private String getShipsGeneratedEventJson(JSONObject jsonResponse, Collection<Ship> ships) {
        JSONArray shipsJsonArray = new JSONArray();
        JSONObject positionJsonObject;
        JSONObject shipJsonObject;

        for (Ship ship : ships) {
            Field startField = ship.getStartField();
            Orientation orientation = ship.getOrientation();
            int size = ship.getSize();

            positionJsonObject = new JSONObject()
                    .put("x", startField.getX())
                    .put("y", startField.getY());

            shipJsonObject = new JSONObject()
                    .put("position", positionJsonObject)
                    .put("orientation", orientation.toString())
                    .put("size", size);

            shipsJsonArray.put(shipJsonObject);
        }

        return getJsonEvent(jsonResponse, "ShipsGeneratedEvent", new JSONObject().put("ships", shipsJsonArray));
    }

    private String getShipHitEventJson(JSONObject jsonResponse, JSONObject position) {
        return getJsonEvent(jsonResponse, "ShipHitEvent", new JSONObject().put("position", position));
    }

    private String getEmptyFieldHitEventJson(JSONObject jsonResponse, JSONObject position) {
        return getJsonEvent(jsonResponse, "EmptyFieldHitEvent", new JSONObject().put("position", position));
    }

    private String getShipDestroyedEventJson(JSONObject jsonResponse, JSONObject position, Collection<Field> adjacentToShip) {
        JSONArray adjacentPositions = new JSONArray();
        JSONObject adjacentPosition;

        for (Field field : adjacentToShip) {
            adjacentPosition = new JSONObject()
                    .put("x", field.getX())
                    .put("y", field.getY());

            adjacentPositions.put(adjacentPosition);
        }

        return jsonResponse
                .put("type", "ShipDestroyedEvent")
                .put("data", new JSONObject()
                        .put("position", position)
                        .put("adjacent", adjacentPositions))
                .toString();
    }

    private String getFieldAlreadyShotEvent(JSONObject jsonResponse, JSONObject position) {
        return jsonResponse
                .put("type", "FieldAlreadyShot")
                .put("data", new JSONObject().put("position", position))
                .toString();
    }
}