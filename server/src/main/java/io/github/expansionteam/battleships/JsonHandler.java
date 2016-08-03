package io.github.expansionteam.battleships;

import org.json.JSONObject;

public class JsonHandler {

    String resolveAction(String str) {
        JSONObject jsonObj = new JSONObject(str);
        String type = jsonObj.getString("type");
        JSONObject jsonResponse = new JSONObject();

        switch (type) {
            case "StartGameEvent": {
                return jsonResponse
                        .put("type", "OpponentArrivedEvent")
                        .toString();
            }
            default:
                return jsonResponse
                        .put("type", "NotRecognizeEvent")
                        .toString();
        }
    }
}
