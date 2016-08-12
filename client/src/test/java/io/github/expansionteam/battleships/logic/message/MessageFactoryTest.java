package io.github.expansionteam.battleships.logic.message;

import io.github.expansionteam.battleships.common.events.GenerateShipsEvent;
import io.github.expansionteam.battleships.common.events.ShootPositionEvent;
import io.github.expansionteam.battleships.common.events.StartGameEvent;
import io.github.expansionteam.battleships.common.events.data.PositionData;
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
        assertThat(message.getType()).isEqualTo("StartGameEvent");
        assertThat(message.getData()).isNotNull();
    }

    @Test
    public void createMessageFromGenerateShipsEvent() {
        // Given
        MessageFactory messageFactory = new MessageFactory();

        // When
        Message message = messageFactory.createFromEvent(new GenerateShipsEvent());

        // Then
        assertThat(message.getType()).isEqualTo("GenerateShipsEvent");
        assertThat(message.getData()).isNotNull();
    }

    @Test
    public void createMessageFromShootPositionEvent() {
        // Given
        MessageFactory messageFactory = new MessageFactory();

        // When
        ShootPositionEvent shootPositionEvent = new ShootPositionEvent(PositionData.of(1, 3));
        Message message = messageFactory.createFromEvent(shootPositionEvent);

        // Then
        assertThat(message.getType()).isEqualTo("ShootPositionEvent");
        assertThat(message.getData().getJSONObject("position").getInt("x")).isEqualTo(1);
        assertThat(message.getData().getJSONObject("position").getInt("y")).isEqualTo(3);
    }

    @Test
    public void createFromJson() {
        // Given
        MessageFactory messageFactory = new MessageFactory();

        // When
        Message message = messageFactory.createFromJson(new JSONObject()
                .put("type", "Type")
                .put("boardOwner", "OWNER")
                .put("data", new JSONObject())
                .toString());

        // Then
        assertThat(message.getType()).isEqualTo("Type");
        assertThat(message.getData()).isNotNull();
    }

}