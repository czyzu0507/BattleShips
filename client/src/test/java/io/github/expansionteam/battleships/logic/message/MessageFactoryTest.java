package io.github.expansionteam.battleships.logic.message;

import io.github.expansionteam.battleships.common.events.GenerateShipsEvent;
import io.github.expansionteam.battleships.common.events.ShootPositionEvent;
import io.github.expansionteam.battleships.common.events.StartGameEvent;
import org.json.JSONObject;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageFactoryTest {

    @Test
    public void createMessageFromStartGameEvent() {
        // Given
        MessageFactory messageFactory = new MessageFactory();

        // When
        Message message = messageFactory.createFromEvent(new StartGameEvent());

        // Then
        JSONObject jsonObject = new JSONObject(message.getBody());
        assertThat(jsonObject.getString("type")).isEqualTo("StartGameEvent");
    }

    @Test
    public void createMessageFromGenerateShipsEvent() {
        // Given
        MessageFactory messageFactory = new MessageFactory();

        // When
        Message message = messageFactory.createFromEvent(new GenerateShipsEvent());

        // Then
        JSONObject jsonObject = new JSONObject(message.getBody());
        assertThat(jsonObject.getString("type")).isEqualTo("GenerateShipsEvent");
    }

    @Test
    public void createMessageFromShootPositionEvent() {
        // Given
        MessageFactory messageFactory = new MessageFactory();

        // When
        ShootPositionEvent shootPositionEvent = new ShootPositionEvent(new ShootPositionEvent.Position(1, 3));
        Message message = messageFactory.createFromEvent(shootPositionEvent);

        // Then
        JSONObject jsonObject = new JSONObject(message.getBody());
        assertThat(jsonObject.getString("type")).isEqualTo("ShootPositionEvent");
        assertThat(jsonObject.getJSONObject("data").getJSONObject("position").getInt("x")).isEqualTo(1);
        assertThat(jsonObject.getJSONObject("data").getJSONObject("position").getInt("y")).isEqualTo(3);
    }

    @Test
    public void createFromJson() {
        // Given
        MessageFactory messageFactory = new MessageFactory();

        // When
        Message message = messageFactory.createFromJson("{}");

        // Then
        assertThat(message.getBody()).isEqualTo("{}");
    }

}