package io.github.expansionteam.battleships.logic;

import io.github.expansionteam.battleships.logic.events.StartGameEvent;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EventConverterTest {

    @Test
    public void convertStartGameEventToJson() {
        // Given
        EventConverter eventConverter = new EventConverter();

        // When
        String json = eventConverter.convertToJson(new StartGameEvent());

        // Then
        assertThat(json).isEqualTo("{\"type\":\"StartGameEvent\"}");
    }


}