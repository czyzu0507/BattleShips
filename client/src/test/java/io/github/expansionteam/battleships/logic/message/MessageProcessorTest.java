package io.github.expansionteam.battleships.logic.message;

import com.google.common.eventbus.EventBus;
import io.github.expansionteam.battleships.common.events.EmptyFieldHitEvent;
import io.github.expansionteam.battleships.common.events.OpponentArrivedEvent;
import io.github.expansionteam.battleships.common.events.ShipHitEvent;
import io.github.expansionteam.battleships.common.events.ShipsGeneratedEvent;
import io.github.expansionteam.battleships.common.events.data.PositionData;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mockito.ArgumentCaptor;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;

public class MessageProcessorTest {

    @Test
    public void processOpponentArrivedEventMessage() {
        // Given
        EventBus eventBusMock = mock(EventBus.class);

        MessageSender messageSenderMock = mock(MessageSender.class);
        Message responseMessage = new Message(new JSONObject().put("type", "OpponentArrivedEvent").toString());
        when(messageSenderMock.sendMessageAndWaitForResponse(isA(Message.class))).thenReturn(responseMessage);

        MessageProcessor messageProcessor = new MessageProcessor(eventBusMock, messageSenderMock);

        // When
        messageProcessor.processMessage(new Message(""));

        // Then
        verify(eventBusMock).post(isA(OpponentArrivedEvent.class));
    }

    @Test
    public void processShipsGeneratedEventMessage() {
        // Given
        EventBus eventBusMock = mock(EventBus.class);

        MessageSender messageSenderMock = mock(MessageSender.class);
        Message responseMessage = new Message(new JSONObject()
                .put("type", "ShipsGeneratedEvent")
                .put("data", new JSONObject()
                        .put("ships", new JSONArray()
                                .put(new JSONObject()
                                        .put("position", new JSONObject().put("x", 1).put("y", 2))
                                        .put("orientation", "HORIZONTAL")
                                        .put("size", 3))))
                .toString());
        when(messageSenderMock.sendMessageAndWaitForResponse(isA(Message.class))).thenReturn(responseMessage);

        MessageProcessor messageProcessor = new MessageProcessor(eventBusMock, messageSenderMock);

        // When
        messageProcessor.processMessage(new Message(""));

        // Then
        ArgumentCaptor<ShipsGeneratedEvent> shipsGeneratedEventArgumentCaptor = ArgumentCaptor.forClass(ShipsGeneratedEvent.class);
        verify(eventBusMock).post(shipsGeneratedEventArgumentCaptor.capture());

        ShipsGeneratedEvent.Ship ship = shipsGeneratedEventArgumentCaptor.getValue().ships.get(0);
        assertThat(ship.position.x).isEqualTo(1);
        assertThat(ship.position.y).isEqualTo(2);
        assertThat(ship.orientation).isEqualTo(ShipsGeneratedEvent.Ship.Orientation.HORIZONTAL);
        assertThat(ship.size).isEqualTo(3);
    }

    @Test
    public void processEmptyFieldHitEventMessage() {
        // Given
        EventBus eventBusMock = mock(EventBus.class);

        MessageSender messageSenderMock = mock(MessageSender.class);
        Message responseMessage = new Message(new JSONObject()
                .put("type", "EmptyFieldHitEvent")
                .put("data", new JSONObject()
                        .put("position", new JSONObject().put("x", 3).put("y", 6)))
                .toString());
        when(messageSenderMock.sendMessageAndWaitForResponse(isA(Message.class))).thenReturn(responseMessage);

        MessageProcessor messageProcessor = new MessageProcessor(eventBusMock, messageSenderMock);

        // When
        messageProcessor.processMessage(new Message(""));

        // Then
        ArgumentCaptor<EmptyFieldHitEvent> emptyFieldHitEventArgumentCaptor = ArgumentCaptor.forClass(EmptyFieldHitEvent.class);
        verify(eventBusMock).post(emptyFieldHitEventArgumentCaptor.capture());

        PositionData positionData = emptyFieldHitEventArgumentCaptor.getValue().getPosition();
        assertThat(positionData.getX()).isEqualTo(3);
        assertThat(positionData.getY()).isEqualTo(6);
    }

    @Test
    public void processShipHitEventMessage() {
        // Given
        EventBus eventBusMock = mock(EventBus.class);

        MessageSender messageSenderMock = mock(MessageSender.class);
        Message responseMessage = new Message(new JSONObject()
                .put("type", "ShipHitEvent")
                .put("data", new JSONObject()
                        .put("position", new JSONObject().put("x", 2).put("y", 8)))
                .toString());
        when(messageSenderMock.sendMessageAndWaitForResponse(isA(Message.class))).thenReturn(responseMessage);

        MessageProcessor messageProcessor = new MessageProcessor(eventBusMock, messageSenderMock);

        // When
        messageProcessor.processMessage(new Message(""));

        // Then
        ArgumentCaptor<ShipHitEvent> shipHitEventArgumentCaptor = ArgumentCaptor.forClass(ShipHitEvent.class);
        verify(eventBusMock).post(shipHitEventArgumentCaptor.capture());

        PositionData positionData = shipHitEventArgumentCaptor.getValue().getPosition();
        assertThat(positionData.getX()).isEqualTo(2);
        assertThat(positionData.getY()).isEqualTo(8);
    }

}