package io.github.expansionteam.battleships.logic.event;

import io.github.expansionteam.battleships.common.events.GenerateShipsEvent;
import io.github.expansionteam.battleships.common.events.StartGameEvent;
import org.testng.annotations.Test;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class EventHandlerTest {

    @Test
    public void handleStartGameEvent() {
        // Given
        EventProcessor eventProcessorMock = mock(EventProcessor.class);
        EventHandler eventHandler = new EventHandler(eventProcessorMock);

        // When
        eventHandler.handleStartGameEvent(new StartGameEvent());

        // Then
        verify(eventProcessorMock).processEvent(isA(StartGameEvent.class));
    }

    @Test
    public void handleGenerateShipsEvent() {
        // Given
        EventProcessor eventProcessorMock = mock(EventProcessor.class);
        EventHandler eventHandler = new EventHandler(eventProcessorMock);

        // When
        eventHandler.handleGenerateShipsEvent(new GenerateShipsEvent());

        // Then
        verify(eventProcessorMock).processEvent(isA(GenerateShipsEvent.class));
    }

}
