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

    String createMessage(String json, Game game, boolean player, RequestState requestState) {
        final JSONObject jsonRequest = new JSONObject(json);
        final JSONObject jsonResponse = new JSONObject();

        if (requestState == NOT_RECOGNIZED_EVENT) {
            return getNotRecognizedEventJson(jsonResponse, player);
        }

        if (requestState == GAME_STARTED) {
            return getOpponentArrivedEventJson(jsonResponse, player);
        }

        if (requestState == SHIPS_GENERATED) {
            game.generateRandomShips();
            Collection<Ship> ships = game.getPlayerShips();
            return getShipsGeneratedEventJson(jsonResponse, player, ships);
        }

        JSONObject position = jsonRequest.getJSONObject("data").getJSONObject("position");

        int x = position.getInt("x");
        int y = position.getInt("y");

        switch (requestState) {
            case FIELD_ALREADY_HIT: {
                return getFieldAlreadyShotEvent(jsonResponse, player, position);
            }

            case EMPTY_FIELD_HIT: {
                return getEmptyFieldHitEventJson(jsonResponse, player, position);
            }

            case SHIP_HIT: {
                return getShipHitEventJson(jsonResponse, player, position);
            }

            case SHIP_DESTROYED: {
                return getShipDestroyedEventJson(jsonResponse, player, position, game.getAdjacentToOpponentShip(x, y));
            }

            case GAME_END: {
                return getGameWonEventJson(jsonResponse, player, position, game.getAdjacentToOpponentShip(x, y));
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

    private String getJsonEvent(JSONObject jsonResponse, String eventType, boolean player, JSONObject data) {
        return jsonResponse
                .put("type", eventType)
                .put("boardOwner", player ? "OPPONENT" : "PLAYER")
                .put("data", data)
                .toString();
    }

    private String getNotRecognizedEventJson(JSONObject jsonResponse, boolean player) {
        return getJsonEvent(jsonResponse, "NotRecognizeEvent", player, new JSONObject()
                .put("nextTurn", player ? "PLAYER" : "OPPONENT"));
    }

    private String getOpponentArrivedEventJson(JSONObject jsonResponse, boolean player) {
        return getJsonEvent(jsonResponse, "OpponentArrivedEvent", player, new JSONObject()
                .put("nextTurn", "PLAYER"));
    }

    private String getShipsGeneratedEventJson(JSONObject jsonResponse, boolean player, Collection<Ship> ships) {
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

        return getJsonEvent(jsonResponse, "ShipsGeneratedEvent", player, new JSONObject()
                .put("nextTurn", player ? "PLAYER" : "OPPONENT")
                .put("ships", shipsJsonArray));
    }

    private String getShipHitEventJson(JSONObject jsonResponse, boolean player, JSONObject position) {
        return getJsonEvent(jsonResponse, "ShipHitEvent", player, new JSONObject()
                .put("nextTurn", player ? "PLAYER" : "OPPONENT")
                .put("position", position));
    }

    private String getEmptyFieldHitEventJson(JSONObject jsonResponse, boolean player, JSONObject position) {
        return getJsonEvent(jsonResponse, "EmptyFieldHitEvent", player, new JSONObject()
                .put("nextTurn", player ? "OPPONENT" : "PLAYER")
                .put("position", position));
    }

    private String getFieldAlreadyShotEvent(JSONObject jsonResponse, boolean player, JSONObject position) {
        return getJsonEvent(jsonResponse, "FieldAlreadyShot", player, new JSONObject()
                .put("nextTurn", player ? "PLAYER" : "OPPONENT")
                .put("position", position));
    }

    private String getShipDestroyedEventJson(JSONObject jsonResponse, boolean player, JSONObject position, Collection<Field> adjacentToShip) {
        return getJsonEvent(jsonResponse, "ShipDestroyedEvent", player, new JSONObject()
                .put("position", position)
                .put("adjacent", createAdjacentPositionJsonArray(adjacentToShip))
                .put("nextTurn", player ? "PLAYER" : "OPPONENT"));
    }

    private String getGameWonEventJson(JSONObject jsonResponse, boolean player, JSONObject position, Collection<Field> adjacentToShip) {
        return getJsonEvent(jsonResponse, "GameEndEvent", player, new JSONObject()
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