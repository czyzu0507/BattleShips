package io.github.expansionteam.battleships.logic.message;

import com.google.common.eventbus.EventBus;
import io.github.expansionteam.battleships.common.events.EmptyFieldHitEvent;
import io.github.expansionteam.battleships.common.events.OpponentArrivedEvent;
import io.github.expansionteam.battleships.common.events.ShipHitEvent;
import io.github.expansionteam.battleships.common.events.ShipsGeneratedEvent;
import io.github.expansionteam.battleships.common.events.data.PositionData;
import io.github.expansionteam.battleships.common.events.data.ShipData;
import io.github.expansionteam.battleships.common.events.data.ShipOrientationData;
import io.github.expansionteam.battleships.gui.models.ShipOrientation;
import io.github.expansionteam.battleships.logic.message.responsemessageprocessors.EmptyFieldHitEventResponseMessageProcessor;
import io.github.expansionteam.battleships.logic.message.responsemessageprocessors.OpponentArrivedResponseMessageProcessor;
import io.github.expansionteam.battleships.logic.message.responsemessageprocessors.ShipHitEventResponseMessageProcessor;
import io.github.expansionteam.battleships.logic.message.responsemessageprocessors.ShipsGeneratedResponseMessageProcessor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mockito.ArgumentCaptor;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;

public class MessageProcessorTest {

    @Test
    public void processOpponentArrivedEventMessage() {
        // Given
        EventBus eventBusMock = mock(EventBus.class);

        MessageSender messageSenderMock = mock(MessageSender.class);
        Message responseMessage = new Message("OpponentArrivedEvent", new JSONObject());
        when(messageSenderMock.sendMessageAndWaitForResponse(isA(Message.class))).thenReturn(responseMessage);

        Map<String, ResponseMessageProcessor> responseMessageProcessorsByType = new HashMap<>();
        responseMessageProcessorsByType.put("OpponentArrivedEvent", new OpponentArrivedResponseMessageProcessor(eventBusMock));

        MessageProcessor messageProcessor = new MessageProcessor(eventBusMock, messageSenderMock, responseMessageProcessorsByType);

        // When
        messageProcessor.processMessage(new Message("", new JSONObject()));

        // Then
        verify(eventBusMock).post(isA(OpponentArrivedEvent.class));
    }

    @Test
    public void processShipsGeneratedEventMessage() {
        // Given
        EventBus eventBusMock = mock(EventBus.class);

        MessageSender messageSenderMock = mock(MessageSender.class);
        Message responseMessage = new Message("ShipsGeneratedEvent", new JSONObject()
                .put("ships", new JSONArray()
                        .put(new JSONObject()
                                .put("position", new JSONObject().put("x", 1).put("y", 2))
                                .put("orientation", "HORIZONTAL")
                                .put("size", 3))));
        when(messageSenderMock.sendMessageAndWaitForResponse(isA(Message.class))).thenReturn(responseMessage);

        Map<String, ResponseMessageProcessor> responseMessageProcessorsByType = new HashMap<>();
        responseMessageProcessorsByType.put("ShipsGeneratedEvent", new ShipsGeneratedResponseMessageProcessor(eventBusMock));

        MessageProcessor messageProcessor = new MessageProcessor(eventBusMock, messageSenderMock, responseMessageProcessorsByType);

        // When
        messageProcessor.processMessage(new Message("", new JSONObject()));

        // Then
        ArgumentCaptor<ShipsGeneratedEvent> shipsGeneratedEventArgumentCaptor = ArgumentCaptor.forClass(ShipsGeneratedEvent.class);
        verify(eventBusMock).post(shipsGeneratedEventArgumentCaptor.capture());

        ShipData shipData = shipsGeneratedEventArgumentCaptor.getValue().getShips().get(0);
        assertThat(shipData.getPosition().getX()).isEqualTo(1);
        assertThat(shipData.getPosition().getY()).isEqualTo(2);
        assertThat(shipData.getOrientation()).isEqualTo(ShipOrientationData.HORIZONTAL);
        assertThat(shipData.getSize().getValue()).isEqualTo(3);
    }

    @Test
    public void processEmptyFieldHitEventMessage() {
        // Given
        EventBus eventBusMock = mock(EventBus.class);

        MessageSender messageSenderMock = mock(MessageSender.class);
        Message responseMessage = new Message("EmptyFieldHitEvent", new JSONObject()
                .put("position", new JSONObject().put("x", 3).put("y", 6)));
        when(messageSenderMock.sendMessageAndWaitForResponse(isA(Message.class))).thenReturn(responseMessage);

        Map<String, ResponseMessageProcessor> responseMessageProcessorsByType = new HashMap<>();
        responseMessageProcessorsByType.put("EmptyFieldHitEvent", new EmptyFieldHitEventResponseMessageProcessor(eventBusMock));

        MessageProcessor messageProcessor = new MessageProcessor(eventBusMock, messageSenderMock, responseMessageProcessorsByType);

        // When
        messageProcessor.processMessage(new Message("", new JSONObject()));

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
        Message responseMessage = new Message("ShipHitEvent", new JSONObject()
                .put("position", new JSONObject().put("x", 2).put("y", 8)));
        when(messageSenderMock.sendMessageAndWaitForResponse(isA(Message.class))).thenReturn(responseMessage);

        Map<String, ResponseMessageProcessor> responseMessageProcessorsByType = new HashMap<>();
        responseMessageProcessorsByType.put("ShipHitEvent", new ShipHitEventResponseMessageProcessor(eventBusMock));

        MessageProcessor messageProcessor = new MessageProcessor(eventBusMock, messageSenderMock, responseMessageProcessorsByType);

        // When
        messageProcessor.processMessage(new Message("", new JSONObject()));

        // Then
        ArgumentCaptor<ShipHitEvent> shipHitEventArgumentCaptor = ArgumentCaptor.forClass(ShipHitEvent.class);
        verify(eventBusMock).post(shipHitEventArgumentCaptor.capture());

        PositionData positionData = shipHitEventArgumentCaptor.getValue().getPosition();
        assertThat(positionData.getX()).isEqualTo(2);
        assertThat(positionData.getY()).isEqualTo(8);
    }

}