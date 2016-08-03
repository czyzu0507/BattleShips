package io.github.expansionteam.battleships;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class JsonHandlerTest {

    @Test
    public void responsesToStartGame() {

        // given
        JsonHandler jsonHandler = new JsonHandler();
        String requestJson = "{\"type\":\"StartGameEvent\"}";
        String expectedResponseJson = "{\"type\":\"OpponentArrivedEvent\"}";

        // when
        String actualResponse = jsonHandler.resolveAction(requestJson);
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
        String actualResponse = jsonHandler.resolveAction(requestJson);

        // then
        assertEquals(actualResponse, expectedResponseJson);
    }

    @Test(expectedExceptions = JSONException.class)
    public void throwsExceptionWhenNotJson() {
        // given
        JsonHandler jsonHandler = new JsonHandler();
        String requestJson = "Not a Json";

        // when
        jsonHandler.resolveAction(requestJson);
    }
}