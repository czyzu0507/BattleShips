package io.github.expansionteam.battleships.logic.message;

import org.json.JSONObject;

public class Message {

    private final String type;
    private final JSONObject data;

    Message(String type, JSONObject data) {
        this.type = type;
        this.data = data;
    }

    String getType() {
        return type;
    }

    public JSONObject getData() {
        return data;
    }

    @Override
    public String toString() {
        return new JSONObject().put("type", type).put("data", data).toString();
    }

}
