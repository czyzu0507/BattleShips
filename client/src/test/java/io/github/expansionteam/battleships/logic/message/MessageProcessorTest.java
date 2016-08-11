package io.github.expansionteam.battleships.logic.message;

import com.google.common.eventbus.EventBus;
import io.github.expansionteam.battleships.common.events.EmptyFieldHitEvent;
import io.github.expansionteam.battleships.common.events.OpponentArrivedEvent;
import io.github.expansionteam.battleships.common.events.ShipHitEvent;
import io.github.expansionteam.battleships.common.events.ShipsGeneratedEvent;
import io.github.expansionteam.battleships.common.events.data.PositionData;
import io.github.expansionteam.battleships.common.events.data.ShipData;
import io.github.expansionteam.battleships.common.events.data.ShipOrientationData;
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
        // given
        EventBus eventBusMock = mock(EventBus.class);

        MessageSender messageSenderMock = mock(MessageSender.class);
        Message responseMessage = new Message("OpponentArrivedEvent", null, new JSONObject());
        when(messageSenderMock.sendMessageAndWaitForResponse(isA(Message.class))).thenReturn(responseMessage);

        Map<String, ResponseMessageProcessor> responseMessageProcessorsByType = new HashMap<>();
        responseMessageProcessorsByType.put("OpponentArrivedEvent", new OpponentArrivedResponseMessageProcessor(eventBusMock));

        MessageProcessor messageProcessor = new MessageProcessor(messageSenderMock, responseMessageProcessorsByType);

        // when
        messageProcessor.processMessage(new Message("", null, new JSONObject()));

        // then
        verify(eventBusMock).post(isA(OpponentArrivedEvent.class));
    }

    @Test
    public void processShipsGeneratedEventMessage() {
        // given
        EventBus eventBusMock = mock(EventBus.class);

        MessageSender messageSenderMock = mock(MessageSender.class);
        Message responseMessage = new Message("ShipsGeneratedEvent", null, new JSONObject()
                .put("ships", new JSONArray()
                        .put(new JSONObject()
                                .put("position", new JSONObject().put("x", 1).put("y", 2))
                                .put("orientation", "HORIZONTAL")
                                .put("size", 3)))
                .put("nextTurn", "PLAYER"));
        when(messageSenderMock.sendMessageAndWaitForResponse(isA(Message.class))).thenReturn(responseMessage);

        Map<String, ResponseMessageProcessor> responseMessageProcessorsByType = new HashMap<>();
        responseMessageProcessorsByType.put("ShipsGeneratedEvent", new ShipsGeneratedResponseMessageProcessor(eventBusMock));

        MessageProcessor messageProcessor = new MessageProcessor(messageSenderMock, responseMessageProcessorsByType);

        // when
        messageProcessor.processMessage(new Message("", null, new JSONObject()));

        // then
        ArgumentCaptor<ShipsGeneratedEvent> shipsGeneratedEventArgumentCaptor = ArgumentCaptor.forClass(ShipsGeneratedEvent.class);
        verify(eventBusMock).post(shipsGeneratedEventArgumentCaptor.capture());

        ShipData shipData = shipsGeneratedEventArgumentCaptor.getValue().getShips().get(0);
        assertThat(shipData.getPosition().getX()).isEqualTo(1);
        assertThat(shipData.getPosition().getY()).isEqualTo(2);
        assertThat(shipData.getOrientation()).isEqualTo(ShipOrientationData.HORIZONTAL);
        assertThat(shipData.getSize().getValue()).isEqualTo(3);
    }

    @Test
    public void processOpponentEmptyFieldHitEventMessage() {
        // given
        EventBus eventBusMock = mock(EventBus.class);

        MessageSender messageSenderMock = mock(MessageSender.class);
        Message responseMessage = new Message("OpponentEmptyFieldHitEvent", BoardOwner.OPPONENT, new JSONObject()
                .put("position", new JSONObject().put("x", 3).put("y", 6))
                .put("nextTurn", "Opponent"));
        when(messageSenderMock.sendMessageAndWaitForResponse(isA(Message.class))).thenReturn(responseMessage);

        Map<String, ResponseMessageProcessor> responseMessageProcessorsByType = new HashMap<>();
        responseMessageProcessorsByType.put("OpponentEmptyFieldHitEvent", new EmptyFieldHitEventResponseMessageProcessor(eventBusMock));

        MessageProcessor messageProcessor = new MessageProcessor(messageSenderMock, responseMessageProcessorsByType);

        // when
        messageProcessor.processMessage(new Message("", null, new JSONObject()));

        // then
        ArgumentCaptor<EmptyFieldHitEvent> emptyFieldHitEventArgumentCaptor = ArgumentCaptor.forClass(EmptyFieldHitEvent.class);
        verify(eventBusMock).post(emptyFieldHitEventArgumentCaptor.capture());

        PositionData positionData = emptyFieldHitEventArgumentCaptor.getValue().getPosition();
        assertThat(positionData.getX()).isEqualTo(3);
        assertThat(positionData.getY()).isEqualTo(6);
    }

    @Test
    public void processPlayerEmptyFieldHitEventMessage() {
        // given
        EventBus eventBusMock = mock(EventBus.class);

        MessageSender messageSenderMock = mock(MessageSender.class);
        Message responseMessage = new Message("PlayerEmptyFieldHitEvent", BoardOwner.OPPONENT, new JSONObject()
                .put("position", new JSONObject().put("x", 3).put("y", 6))
                .put("nextTurn", "OPPONENT"));
        when(messageSenderMock.sendMessageAndWaitForResponse(isA(Message.class))).thenReturn(responseMessage);

        Map<String, ResponseMessageProcessor> responseMessageProcessorsByType = new HashMap<>();
        responseMessageProcessorsByType.put("PlayerEmptyFieldHitEvent", new EmptyFieldHitEventResponseMessageProcessor(eventBusMock));

        MessageProcessor messageProcessor = new MessageProcessor(messageSenderMock, responseMessageProcessorsByType);

        // when
        messageProcessor.processMessage(new Message("", null, new JSONObject()));

        // then
        ArgumentCaptor<EmptyFieldHitEvent> emptyFieldHitEventArgumentCaptor = ArgumentCaptor.forClass(EmptyFieldHitEvent.class);
        verify(eventBusMock).post(emptyFieldHitEventArgumentCaptor.capture());

        PositionData positionData = emptyFieldHitEventArgumentCaptor.getValue().getPosition();
        assertThat(positionData.getX()).isEqualTo(3);
        assertThat(positionData.getY()).isEqualTo(6);
    }

    @Test
    public void processOpponentShipHitEventMessage() {
        // given
        EventBus eventBusMock = mock(EventBus.class);

        MessageSender messageSenderMock = mock(MessageSender.class);
        Message responseMessage = new Message("OpponentShipHitEvent", BoardOwner.OPPONENT, new JSONObject()
                .put("position", new JSONObject().put("x", 2).put("y", 8))
                .put("nextTurn", "OPPONENT"));
        when(messageSenderMock.sendMessageAndWaitForResponse(isA(Message.class))).thenReturn(responseMessage);

        Map<String, ResponseMessageProcessor> responseMessageProcessorsByType = new HashMap<>();
        responseMessageProcessorsByType.put("OpponentShipHitEvent", new ShipHitEventResponseMessageProcessor(eventBusMock));

        MessageProcessor messageProcessor = new MessageProcessor(messageSenderMock, responseMessageProcessorsByType);

        // when
        messageProcessor.processMessage(new Message("", null, new JSONObject()));

        // then
        ArgumentCaptor<ShipHitEvent> shipHitEventArgumentCaptor = ArgumentCaptor.forClass(ShipHitEvent.class);
        verify(eventBusMock).post(shipHitEventArgumentCaptor.capture());

        PositionData positionData = shipHitEventArgumentCaptor.getValue().getPosition();
        assertThat(positionData.getX()).isEqualTo(2);
        assertThat(positionData.getY()).isEqualTo(8);
    }

    @Test
    public void processPlayerShipHitEventMessage() {
        // given
        EventBus eventBusMock = mock(EventBus.class);

        MessageSender messageSenderMock = mock(MessageSender.class);
        Message responseMessage = new Message("PlayerShipHitEvent", BoardOwner.OPPONENT, new JSONObject()
                .put("position", new JSONObject().put("x", 2).put("y", 8))
                .put("nextTurn", "OPPONENT"));
        when(messageSenderMock.sendMessageAndWaitForResponse(isA(Message.class))).thenReturn(responseMessage);

        Map<String, ResponseMessageProcessor> responseMessageProcessorsByType = new HashMap<>();
        responseMessageProcessorsByType.put("PlayerShipHitEvent", new ShipHitEventResponseMessageProcessor(eventBusMock));

        MessageProcessor messageProcessor = new MessageProcessor(messageSenderMock, responseMessageProcessorsByType);

        // when
        messageProcessor.processMessage(new Message("", null, new JSONObject()));

        // then
        ArgumentCaptor<ShipHitEvent> shipHitEventArgumentCaptor = ArgumentCaptor.forClass(ShipHitEvent.class);
        verify(eventBusMock).post(shipHitEventArgumentCaptor.capture());

        PositionData positionData = shipHitEventArgumentCaptor.getValue().getPosition();
        assertThat(positionData.getX()).isEqualTo(2);
        assertThat(positionData.getY()).isEqualTo(8);
    }

    @Test
    public void processOpponentShipDestroyedEventMessage() {
        // given
        EventBus eventBusMock = mock(EventBus.class);

        MessageSender messageSenderMock = mock(MessageSender.class);
        Message responseMessage = new Message("OpponentShipDestroyedEvent", BoardOwner.OPPONENT, new JSONObject()
                .put("position", new JSONObject().put("x", 2).put("y", 8))
                .put("nextTurn", "OPPONENT"));
        when(messageSenderMock.sendMessageAndWaitForResponse(isA(Message.class))).thenReturn(responseMessage);

        Map<String, ResponseMessageProcessor> responseMessageProcessorsByType = new HashMap<>();
        responseMessageProcessorsByType.put("OpponentShipDestroyedEvent", new ShipHitEventResponseMessageProcessor(eventBusMock));

        MessageProcessor messageProcessor = new MessageProcessor(messageSenderMock, responseMessageProcessorsByType);

        // when
        messageProcessor.processMessage(new Message("", null, new JSONObject()));

        // then
        ArgumentCaptor<ShipHitEvent> shipHitEventArgumentCaptor = ArgumentCaptor.forClass(ShipHitEvent.class);
        verify(eventBusMock).post(shipHitEventArgumentCaptor.capture());

        PositionData positionData = shipHitEventArgumentCaptor.getValue().getPosition();
        assertThat(positionData.getX()).isEqualTo(2);
        assertThat(positionData.getY()).isEqualTo(8);
    }

    @Test
    public void processPlayerShipDestroyedEventMessage() {
        // given
        EventBus eventBusMock = mock(EventBus.class);

        MessageSender messageSenderMock = mock(MessageSender.class);
        Message responseMessage = new Message("PlayerShipDestroyedEvent", BoardOwner.OPPONENT, new JSONObject()
                .put("position", new JSONObject().put("x", 2).put("y", 8))
                .put("nextTurn", "OPPONENT"));
        when(messageSenderMock.sendMessageAndWaitForResponse(isA(Message.class))).thenReturn(responseMessage);

        Map<String, ResponseMessageProcessor> responseMessageProcessorsByType = new HashMap<>();
        responseMessageProcessorsByType.put("PlayerShipDestroyedEvent", new ShipHitEventResponseMessageProcessor(eventBusMock));

        MessageProcessor messageProcessor = new MessageProcessor(messageSenderMock, responseMessageProcessorsByType);

        // when
        messageProcessor.processMessage(new Message("", null, new JSONObject()));

        // then
        ArgumentCaptor<ShipHitEvent> shipHitEventArgumentCaptor = ArgumentCaptor.forClass(ShipHitEvent.class);
        verify(eventBusMock).post(shipHitEventArgumentCaptor.capture());

        PositionData positionData = shipHitEventArgumentCaptor.getValue().getPosition();
        assertThat(positionData.getX()).isEqualTo(2);
        assertThat(positionData.getY()).isEqualTo(8);
    }

}