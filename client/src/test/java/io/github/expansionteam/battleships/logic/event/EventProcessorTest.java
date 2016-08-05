package io.github.expansionteam.battleships.logic.event;

import com.google.common.eventbus.EventBus;
import io.github.expansionteam.battleships.common.events.GenerateShipsEvent;
import io.github.expansionteam.battleships.common.events.ShipsGeneratedEvent;
import io.github.expansionteam.battleships.common.events.StartGameEvent;
import io.github.expansionteam.battleships.logic.AsyncTask;
import io.github.expansionteam.battleships.logic.message.MessageFactory;
import io.github.expansionteam.battleships.logic.message.MessageProcessor;
import io.github.expansionteam.battleships.logic.message.ProcessMessageTask;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mockito.ArgumentCaptor;
import org.testng.annotations.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class EventProcessorTest {

    @Test
    public void processOpponentArrivedEvent() {
        // Given
        AsyncTask asyncTaskMock = mock(AsyncTask.class);
        MessageFactory messageFactoryMock = mock(MessageFactory.class);
        MessageProcessor messageProcessorMock = mock(MessageProcessor.class);

        EventProcessor eventProcessor = new EventProcessor(asyncTaskMock, messageFactoryMock, messageProcessorMock);

        // When
        eventProcessor.processEvent(new StartGameEvent());

        // Then
        verify(messageFactoryMock).createFromEvent(isA(StartGameEvent.class));
        verify(asyncTaskMock).runLater(isA(ProcessMessageTask.class));
    }

    @Test
    public void processShipsGeneratedEvent() {
        // Given
        AsyncTask asyncTaskMock = mock(AsyncTask.class);
        MessageFactory messageFactoryMock = mock(MessageFactory.class);
        MessageProcessor messageProcessorMock = mock(MessageProcessor.class);

        EventProcessor eventProcessor = new EventProcessor(asyncTaskMock, messageFactoryMock, messageProcessorMock);

        // When
        eventProcessor.processEvent(new GenerateShipsEvent());

        // Then
        verify(messageFactoryMock).createFromEvent(isA(GenerateShipsEvent.class));
        verify(asyncTaskMock).runLater(isA(ProcessMessageTask.class));
    }

}