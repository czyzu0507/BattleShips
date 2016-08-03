package io.github.expansionteam.battleships;

import io.github.expansionteam.battleships.engine.Field;
import io.github.expansionteam.battleships.engine.Orientation;
import io.github.expansionteam.battleships.engine.Ship;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.LinkedList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class JsonHandlerTest {
    Game gameMock = mock(Game.class);

    @Test
    public void responsesToStartGame() {
        // given
        JsonHandler jsonHandler = new JsonHandler();
        String requestJson = "{\"type\":\"StartGameEvent\"}";
        String expectedResponseJson = "{\"type\":\"OpponentArrivedEvent\"}";

        // when
        String actualResponse = jsonHandler.resolveAction(requestJson, gameMock);
        JSONObject jsonObject = new JSONObject(actualResponse);

        // then
        assertEquals(actualResponse, expectedResponseJson);
    }

    @Test
    public void responsesToNotRecognizeEvent() {
        // given
        JsonHandler jsonHandler = new JsonHandler();
        String requestJson = "{\"type\":\"NoSuchEvent\"}";
        String expectedResponseJson = "{\"type\":\"NotRecognizeEvent\"}";

        // when
        String actualResponse = jsonHandler.resolveAction(requestJson, gameMock);

        // then
        assertEquals(actualResponse, expectedResponseJson);
    }

    @Test(expectedExceptions = JSONException.class)
    public void throwsExceptionWhenNotJson() {
        // given
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
}