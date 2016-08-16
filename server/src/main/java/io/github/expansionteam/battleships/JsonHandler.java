package io.github.expansionteam.battleships;

import io.github.expansionteam.battleships.engine.Field;
import io.github.expansionteam.battleships.engine.Orientation;
import io.github.expansionteam.battleships.engine.Ship;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collection;

import static io.github.expansionteam.battleships.RequestState.*;

class JsonHandler {
    static String getJSONType(String json) {
        return new JSONObject(json).getString("type");
    }

    String createMessage(String json, Game game, boolean isOpponentPlayer, RequestState requestState) {
        final JSONObject jsonRequest = new JSONObject(json);
        final JSONObject jsonResponse = new JSONObject();

        if (requestState == NOT_RECOGNIZED_EVENT) {
            return getNotRecognizedEventJson(jsonResponse, isOpponentPlayer);
        }

        if (requestState == GAME_STARTED) {
            return getOpponentArrivedEventJson(jsonResponse, isOpponentPlayer);
        }

        if (requestState == SHIPS_GENERATED) {
            game.generateRandomShips();
            Collection<Ship> ships = game.getPlayerShips();
            return getShipsGeneratedEventJson(jsonResponse, isOpponentPlayer, ships);
        }

        JSONObject position = jsonRequest.getJSONObject("data").getJSONObject("position");

        int x = position.getInt("x");
        int y = position.getInt("y");

        switch (requestState) {
            case FIELD_ALREADY_HIT: {
                return getFieldAlreadyShotEvent(jsonResponse, isOpponentPlayer, position);
            }

            case EMPTY_FIELD_HIT: {
                return getEmptyFieldHitEventJson(jsonResponse, isOpponentPlayer, position);
            }

            case SHIP_HIT: {
                return getShipHitEventJson(jsonResponse, isOpponentPlayer, position);
            }

            case SHIP_DESTROYED: {
                return getShipDestroyedEventJson(jsonResponse, isOpponentPlayer, position, game.getAdjacentToOpponentShip(x, y));
            }

            case GAME_END: {
                return getGameWonEventJson(jsonResponse, isOpponentPlayer, position, game.getAdjacentToOpponentShip(x, y));
            }
            default:
                throw new IllegalStateException("Unexpected request state");
        }
    }

    RequestState apply(String json, Game game) {
        final JSONObject jsonRequest = new JSONObject(json);
        final String type = new JSONObject(json).getString("type");

        switch (type) {
            case "StartGameEvent": {
                return GAME_STARTED;
            }
            case "GenerateShipsEvent": {
                game.generateRandomShips();
                return RequestState.SHIPS_GENERATED;
            }

            case "ShootPositionEvent": {
                JSONObject position = jsonRequest.getJSONObject("data").getJSONObject("position");

                int x = position.getInt("x");
                int y = position.getInt("y");

                if (!game.shootOpponentField(x, y)) {
                    return RequestState.FIELD_ALREADY_HIT;
                }

                if (!game.isOpponentShipHit(x, y)) {
                    return RequestState.EMPTY_FIELD_HIT;
                }

                if (!game.isOpponentShipDestroyed(x, y)) {
                    return RequestState.SHIP_HIT;
                }

                if (!game.isEnded()) {
                    return RequestState.SHIP_DESTROYED;
                }

                return RequestState.GAME_END;
            }
            default:
                return RequestState.NOT_RECOGNIZED_EVENT;
        }
    }

    private String getJsonEvent(JSONObject jsonResponse, String eventType, boolean isOpponentPlayer, JSONObject data) {
        return jsonResponse
                .put("type", eventType)
                .put("boardOwner", isOpponentPlayer ? "OPPONENT" : "PLAYER")
                .put("data", data)
                .toString();
    }

    private String getNotRecognizedEventJson(JSONObject jsonResponse, boolean isOpponentPlayer) {
        return getJsonEvent(jsonResponse, "NotRecognizeEvent", isOpponentPlayer, new JSONObject()
                .put("nextTurn", isOpponentPlayer ? "PLAYER" : "OPPONENT"));
    }

    private String getOpponentArrivedEventJson(JSONObject jsonResponse, boolean isOpponentPlayer) {
        return getJsonEvent(jsonResponse, "OpponentArrivedEvent", isOpponentPlayer, new JSONObject()
                .put("nextTurn", "PLAYER"));
    }

    private String getShipsGeneratedEventJson(JSONObject jsonResponse, boolean isOpponentPlayer, Collection<Ship> ships) {
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

        return getJsonEvent(jsonResponse, "ShipsGeneratedEvent", isOpponentPlayer, new JSONObject()
                .put("nextTurn", isOpponentPlayer ? "PLAYER" : "OPPONENT")
                .put("ships", shipsJsonArray));
    }

    private String getShipHitEventJson(JSONObject jsonResponse, boolean isOpponentPlayer, JSONObject position) {
        return getJsonEvent(jsonResponse, "ShipHitEvent", isOpponentPlayer, new JSONObject()
                .put("nextTurn", isOpponentPlayer ? "PLAYER" : "OPPONENT")
                .put("position", position));
    }

    private String getEmptyFieldHitEventJson(JSONObject jsonResponse, boolean isOpponentPlayer, JSONObject position) {
        return getJsonEvent(jsonResponse, "EmptyFieldHitEvent", isOpponentPlayer, new JSONObject()
                .put("nextTurn", isOpponentPlayer ? "OPPONENT" : "PLAYER")
                .put("position", position));
    }

    private String getFieldAlreadyShotEvent(JSONObject jsonResponse, boolean isOpponentPlayer, JSONObject position) {
        return getJsonEvent(jsonResponse, "FieldAlreadyShot", isOpponentPlayer, new JSONObject()
                .put("nextTurn", isOpponentPlayer ? "PLAYER" : "OPPONENT")
                .put("position", position));
    }

    private String getShipDestroyedEventJson(JSONObject jsonResponse, boolean isOpponentPlayer, JSONObject position, Collection<Field> adjacentToShip) {
        return getJsonEvent(jsonResponse, "ShipDestroyedEvent", isOpponentPlayer, new JSONObject()
                .put("position", position)
                .put("adjacent", createAdjacentPositionJsonArray(adjacentToShip))
                .put("nextTurn", isOpponentPlayer ? "PLAYER" : "OPPONENT"));
    }

    private String getGameWonEventJson(JSONObject jsonResponse, boolean isOpponentPlayer, JSONObject position, Collection<Field> adjacentToShip) {
        return getJsonEvent(jsonResponse, "GameEndEvent", isOpponentPlayer, new JSONObject()
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