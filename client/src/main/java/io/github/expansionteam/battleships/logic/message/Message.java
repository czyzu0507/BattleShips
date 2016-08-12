package io.github.expansionteam.battleships.logic.message;

import org.json.JSONObject;

public class Message {

    private final String type;
    private final BoardOwner boardOwner;
    private final JSONObject data;

    Message(String type, BoardOwner boardOwner, JSONObject data) {
        this.type = type;
        this.boardOwner = boardOwner;
        this.data = data;
    }

    String getType() {
        return type;
    }

    public BoardOwner getBoardOwner() {
        return boardOwner;
    }

    public JSONObject getData() {
        return data;
    }

    @Override
    public String toString() {
        return new JSONObject().put("type", type).put("data", data).toString();
    }

}
