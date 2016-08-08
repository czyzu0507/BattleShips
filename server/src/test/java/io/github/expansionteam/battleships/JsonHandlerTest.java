package io.github.expansionteam.battleships;

import io.github.expansionteam.battleships.engine.Field;
import io.github.expansionteam.battleships.engine.Orientation;
import io.github.expansionteam.battleships.engine.Ship;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class JsonHandlerTest {

    @Test
    public void responsesToStartGame() {
        // given
        Game gameMock = mock(Game.class);
        JsonHandler jsonHandler = new JsonHandler();
        String requestJson = new JSONObject().put("type", "StartGameEvent").toString();

        // when
        JSONObject actualResponse = new JSONObject(jsonHandler.resolveAction(requestJson, gameMock));

        // then
        assertEquals(actualResponse.get("type"), "OpponentArrivedEvent");
    }

    @Test
    public void responsesToNotRecognizeEvent() {
        // given
        Game gameMock = mock(Game.class);
        JsonHandler jsonHandler = new JsonHandler();
        String requestJson = new JSONObject().put("type", "NoSuchEvent").toString();

        // when
        JSONObject actualResponse = new JSONObject(jsonHandler.resolveAction(requestJson, gameMock));

        // then
        assertEquals(actualResponse.get("type"), "NotRecognizeEvent");
    }

    @Test(expectedExceptions = JSONException.class)
    public void throwsExceptionWhenNotJson() {
        // given
        Game gameMock = mock(Game.class);
        JsonHandler jsonHandler = new JsonHandler();
        String requestJson = "Not a Json";

        // when
        jsonHandler.resolveAction(requestJson, gameMock);
    }

    @Test
    public void responsesToGenerateShips() {
        // given
        JsonHandler jsonHandler = new JsonHandler();

        Collection<Ship> shipMocks = new LinkedList<>();

        Ship shipMock1 = mock(Ship.class);
        Field fieldMock1 = new Field(1, 2);

        when(shipMock1.getStartField()).thenReturn(fieldMock1);
        when(shipMock1.getOrientation()).thenReturn(Orientation.HORIZONTAL);
        when(shipMock1.getSize()).thenReturn(3);

        Ship shipMock2 = mock(Ship.class);
        Field fieldMock2 = new Field(5, 6);

        when(shipMock2.getStartField()).thenReturn(fieldMock2);
        when(shipMock2.getOrientation()).thenReturn(Orientation.VERTICAL);
        when(shipMock2.getSize()).thenReturn(2);

        shipMocks.add(shipMock1);
        shipMocks.add(shipMock2);

        Game gameMock = mock(Game.class);
        when(gameMock.getPlayerShips()).thenReturn(shipMocks);

        String requestJson = new JSONObject()
                .put("type", "GenerateShipsEvent")
                .toString();

        // when
        JSONObject actualResponse = new JSONObject(jsonHandler.resolveAction(requestJson, gameMock));
        JSONObject data = actualResponse.getJSONObject("data");
        JSONArray ships = data.getJSONArray("ships");

        JSONObject ship1 = ships.getJSONObject(0);
        JSONObject position1 = ship1.getJSONObject("position");

        int x1 = position1.getInt("x");
        int y1 = position1.getInt("y");
        String orientation1 = ship1.getString("orientation");
        int size1 = ship1.getInt("size");

        JSONObject ship2 = ships.getJSONObject(1);
        JSONObject position2 = ship2.getJSONObject("position");

        int x2 = position2.getInt("x");
        int y2 = position2.getInt("y");
        String orientation2 = ship2.getString("orientation");
        int size2 = ship2.getInt("size");

        // then
        assertEquals(actualResponse.getString("type"), "ShipsGeneratedEvent");
        assertEquals(x1, 1);
        assertEquals(y1, 2);
        assertEquals(orientation1, "HORIZONTAL");
        assertEquals(size1, 3);
        assertEquals(x2, 5);
        assertEquals(y2, 6);
        assertEquals(orientation2, "VERTICAL");
        assertEquals(size2, 2);
    }
    
    @DataProvider
    private Object[][] shootFieldSituations() {
        return new Object[][]{
                {true, false, false, "EmptyFieldHitEvent"},
                {true, true, false, "ShipHitEvent"},
                {false, false, false, "FieldAlreadyShot"}
        };
    }

    @Test(dataProvider = "shootFieldSituations")
    public void responsesToShotField(boolean valid, boolean ship, boolean destroyed, String event) {
        // given
        JsonHandler jsonHandler = new JsonHandler();

        Game gameMock = mock(Game.class);
        when(gameMock.shoot(2, 5)).thenReturn(valid);
        when(gameMock.isShipHit(2, 5)).thenReturn(ship);
        when(gameMock.isDestroyedShip(2, 5)).thenReturn(destroyed);

        Collection<Field> fieldMocks = createAdjacentFields(2, 5);
        when(gameMock.getAdjacentToShip(2, 5)).thenReturn(fieldMocks);

        String requestJson = createEvent("ShootPositionEvent", 2, 5);

        // when
        JSONObject actualResponse = new JSONObject(jsonHandler.resolveAction(requestJson, gameMock));

        // then
        assertEquals(actualResponse.getString("type"), event);

        JSONObject shootPosition = actualResponse.getJSONObject("data").getJSONObject("position");
        int x = shootPosition.getInt("x");
        assertEquals(x, 2);

        int y = shootPosition.getInt("y");
        assertEquals(y, 5);
    }

    @Test
    public void responsesToDestroyingShip() {
        // given
        JsonHandler jsonHandler = new JsonHandler();

        Game gameMock = mock(Game.class);
        when(gameMock.shoot(2, 5)).thenReturn(true);
        when(gameMock.isShipHit(2, 5)).thenReturn(true);
        when(gameMock.isDestroyedShip(2, 5)).thenReturn(true);

        Collection<Field> fieldMocks = createAdjacentFields(2, 5);
        when(gameMock.getAdjacentToShip(2, 5)).thenReturn(fieldMocks);

        String requestJson = createEvent("ShootPositionEvent", 2, 5);
        Collection<Integer> expectedAdjacentCoordinates = createAdjacentCoordinates(2, 5);

        // when
        JSONObject actualResponse = new JSONObject(jsonHandler.resolveAction(requestJson, gameMock));

        // then
        assertEquals(actualResponse.getString("type"), "ShipDestroyedEvent");

        JSONObject shootPosition = actualResponse.getJSONObject("data").getJSONObject("position");
        int x = shootPosition.getInt("x");
        assertEquals(x, 2);

        int y = shootPosition.getInt("y");
        assertEquals(y, 5);

        JSONArray adjacent = actualResponse.getJSONObject("data").getJSONArray("adjacent");
        Collection<Integer> actualAdjacentCoordinates = createAdjacentCoordinatesOfJSON(adjacent);
        assertEquals(actualAdjacentCoordinates, expectedAdjacentCoordinates);
    }

    private String createEvent(String event, int x, int y) {
        JSONObject position = new JSONObject()
                .put("position", new JSONObject().put("x", x).put("y", y));

        return new JSONObject()
                .put("type", event)
                .put("data", position)
                .toString();
    }

    private Collection<Integer> createAdjacentCoordinatesOfJSON(JSONArray adjacent) {
        Collection<Integer> actualAdjacentCoordinates = new ArrayList<>();
        for (int i = 0; i < adjacent.length(); i++) {
            JSONObject adjacentPosition = adjacent.getJSONObject(i);
            actualAdjacentCoordinates.add(adjacentPosition.getInt("x"));
            actualAdjacentCoordinates.add(adjacentPosition.getInt("y"));
        }
        return actualAdjacentCoordinates;
    }

    private Collection<Integer> createAdjacentCoordinates(int x, int y) {
        Collection<Integer> expectedAdjacentCoordinates = new ArrayList<>();
        for (int i = x - 1; i <= x + 1; i++) {
            if (x < 0 || x > 9) {
                continue;
            }
            for (int j = y - 1; j <= y + 1; j++) {
                if (y < 0 || y > 9 || (i == x && j == y)) {
                    continue;
                }
                expectedAdjacentCoordinates.add(i);
                expectedAdjacentCoordinates.add(j);
            }
        }
        return expectedAdjacentCoordinates;
    }

    private Collection<Field> createAdjacentFields(int x, int y) {
        Collection<Field> fieldMocks = new ArrayList<>();
        for (int i = x - 1; i <= x + 1; i++) {
            if (x < 0 || x > 9) {
                continue;
            }
            for (int j = y - 1; j <= y + 1; j++) {
                if (y < 0 || y > 9 || (i == x && j == y)) {
                    continue;
                }
                fieldMocks.add(new Field(i, j));
            }
        }
        return fieldMocks;
    }
}