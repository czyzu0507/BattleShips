package io.github.expansionteam.battleships;

import io.github.expansionteam.battleships.engine.Field;
import io.github.expansionteam.battleships.engine.Orientation;
import io.github.expansionteam.battleships.engine.Ship;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collection;

class JsonHandler {
    static String getJSONType(String json) {
        return new JSONObject(json).getString("type");
    }

    String resolveAction(String json, Game game, boolean player) {
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

                if (!game.shootOpponentField(x, y)) {
                    return getFieldAlreadyShotEvent(jsonResponse, player, position);
                }

                if (!game.isOpponentShipHit(x, y)) {
                    return getEmptyFieldHitEventJson(jsonResponse, player, position);
                }

                if (!game.isOpponentShipDestroyed(x, y)) {
                    return getShipHitEventJson(jsonResponse, player, position);
                }

                if (!game.isEnded()) {
                    return getShipDestroyedEventJson(jsonResponse, player, position, game.getAdjacentToOpponentShip(x, y));
                }

                return getGameWonEventJson(jsonResponse, player, position, game.getAdjacentToOpponentShip(x, y));
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

    private String getShipHitEventJson(JSONObject jsonResponse, boolean player, JSONObject position) {
        return getJsonEvent(jsonResponse,
                player ? "PlayerShipHitEvent" : "OpponentShipHitEvent",
                new JSONObject().put("position", position));
    }

    private String getEmptyFieldHitEventJson(JSONObject jsonResponse, boolean player, JSONObject position) {
        return getJsonEvent(jsonResponse,
                player ? "PlayerEmptyFieldHitEvent" : "OpponentEmptyFieldHitEvent",
                new JSONObject().put("position", position));
    }

    private String getFieldAlreadyShotEvent(JSONObject jsonResponse, boolean player, JSONObject position) {
        return getJsonEvent(jsonResponse,
                player ? "PlayerFieldAlreadyShot" : "OpponentFieldAlreadyShot",
                new JSONObject().put("position", position));
    }

    private String getShipDestroyedEventJson(JSONObject jsonResponse, boolean player, JSONObject position, Collection<Field> adjacentToShip) {
        return getJsonEvent(jsonResponse,
                player ? "PlayerShipDestroyedEvent" : "OpponentShipDestroyedEvent",
                new JSONObject()
                        .put("position", position)
                        .put("adjacent", createAdjacentPositionJsonArray(adjacentToShip)));
    }

    private String getGameWonEventJson(JSONObject jsonResponse, boolean player, JSONObject position, Collection<Field> adjacentToShip) {
        return getJsonEvent(jsonResponse,
                player ? "PlayerWonEvent" : "OpponentWonEvent",
                new JSONObject()
                        .put("position", position)
                        .put("adjacent", createAdjacentPositionJsonArray(adjacentToShip)));
    }

    private JSONArray createAdjacentPositionJsonArray(Collection<Field> adjacentToShip) {
        JSONArray adjacentPositions = new JSONArray();
        JSONObject adjacentPosition;

        for (Field field : adjacentToShip) {
            adjacentPosition = new JSONObject()
                    .put("x", field.getX())
                    .put("y", field.getY());

            adjacentPositions.put(adjacentPosition);
        }
        return adjacentPositions;
    }
}