package io.github.expansionteam.battleships.logic.message;

import io.github.expansionteam.battleships.common.events.GenerateShipsEvent;
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
    public void createFromJson() {
        // Given
        MessageFactory messageFactory = new MessageFactory();

        // When
        Message message = messageFactory.createFromJson("{}");

        // Then
        assertThat(message.getBody()).isEqualTo("{}");
    }

}