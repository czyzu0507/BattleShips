package io.github.expansionteam.battleships.logic;

import com.google.common.eventbus.EventBus;
import io.github.expansionteam.battleships.logic.events.OpponentArrivedEvent;
import org.json.JSONObject;
import org.testng.annotations.Test;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class EventProcessorTest {

    @Test
    public void processOpponentArrivedEvent() {
        // Given
        EventBus eventBusMock = mock(EventBus.class);
        EventProcessor eventProcessor = new EventProcessor(eventBusMock);

        // When
        String json = new JSONObject().put("type", "OpponentArrivedEvent").toString();
        eventProcessor.processJson(json);

        // Then
        verify(eventBusMock).post(isA(OpponentArrivedEvent.class));
    }


}