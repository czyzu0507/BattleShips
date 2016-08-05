package io.github.expansionteam.battleships.logic;

import com.google.common.eventbus.EventBus;
import io.github.expansionteam.battleships.logic.events.OpponentArrivedEvent;
import io.github.expansionteam.battleships.logic.events.ShipsGeneratedEvent;
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
        EventBus eventBusMock = mock(EventBus.class);
        EventProcessor eventProcessor = new EventProcessor(eventBusMock);

        // When
        String json = new JSONObject().put("type", "OpponentArrivedEvent").toString();
        eventProcessor.processJson(json);

        // Then
        verify(eventBusMock).post(isA(OpponentArrivedEvent.class));
    }

    @Test
    public void processShipsGeneratedEvent() {
        // Given
        EventBus eventBusMock = mock(EventBus.class);
        EventProcessor eventProcessor = new EventProcessor(eventBusMock);

        // When
        JSONObject jsonObject = new JSONObject()
                .put("type", "ShipsGeneratedEvent")
                .put("data", new JSONObject()
                        .put("ships", new JSONArray()
                                .put(new JSONObject()
                                        .put("position", new JSONObject().put("x", 6).put("y", 5))
                                        .put("size", 4)
                                        .put("orientation", "HORIZONTAL"))
                                .put(new JSONObject()
                                        .put("position", new JSONObject().put("x", 4).put("y", 4))
                                        .put("size", 3)
                                        .put("orientation", "VERTICAL"))));
        eventProcessor.processJson(jsonObject.toString());

        // Then
        ArgumentCaptor<ShipsGeneratedEvent> shipsGeneratedEventArgumentCaptor = ArgumentCaptor.forClass(ShipsGeneratedEvent.class);
        verify(eventBusMock).post(shipsGeneratedEventArgumentCaptor.capture());

        List<ShipsGeneratedEvent.Ship> ships = shipsGeneratedEventArgumentCaptor.getValue().ships;
        ShipsGeneratedEvent.Ship ship1 = ships.get(0);
        ShipsGeneratedEvent.Ship ship2 = ships.get(1);

        assertThat(ship1.position.x).isEqualTo(6);
        assertThat(ship1.position.y).isEqualTo(5);
        assertThat(ship1.size).isEqualTo(4);
        assertThat(ship1.orientation).isEqualTo(ShipsGeneratedEvent.Ship.Orientation.HORIZONTAL);

        assertThat(ship2.orientation).isEqualTo(ShipsGeneratedEvent.Ship.Orientation.VERTICAL);
        assertThat(ship2.size).isEqualTo(3);
        assertThat(ship2.position.x).isEqualTo(4);
        assertThat(ship2.position.y).isEqualTo(4);
    }

}
