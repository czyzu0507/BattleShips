package io.github.expansionteam.battleships.gui.controllers;

import com.google.common.eventbus.EventBus;
import io.github.expansionteam.battleships.common.events.GenerateShipsEvent;
import io.github.expansionteam.battleships.common.events.OpponentArrivedEvent;
import io.github.expansionteam.battleships.common.events.ShipsGeneratedEvent;
import io.github.expansionteam.battleships.gui.models.PlayerBoard;
import io.github.expansionteam.battleships.gui.models.Position;
import io.github.expansionteam.battleships.gui.models.Ship;
import io.github.expansionteam.battleships.gui.models.ShipSize;
import javafx.scene.layout.BorderPane;
import org.mockito.ArgumentCaptor;
import org.testng.annotations.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;

public class BattleshipsControllerTest {

    @Test
    public void handleOpponentArrivedEvent() {
        // Given
        EventBus eventBusMock = mock(EventBus.class);
        BorderPane boardAreaMock = mock(BorderPane.class);

        BattleshipsController battleshipsController = new BattleshipsController();
        battleshipsController.eventBus = eventBusMock;
        battleshipsController.boardArea = boardAreaMock;

        // When
        battleshipsController.handleOpponentArrivedEvent(new OpponentArrivedEvent());

        // Then
        verify(eventBusMock).post(isA(GenerateShipsEvent.class));
    }

    @Test
    public void handleShipsGeneratedEvent() {
        // Given
        EventBus eventBusMock = mock(EventBus.class);
        BorderPane boardAreaMock = mock(BorderPane.class);
        PlayerBoard playerBoardMock = mock(PlayerBoard.class);

        BattleshipsController battleshipsController = new BattleshipsController();
        battleshipsController.eventBus = eventBusMock;
        battleshipsController.boardArea = boardAreaMock;
        battleshipsController.playerBoard = playerBoardMock;

        // When
        ShipsGeneratedEvent.Ship ship1 = createShip1();
        ShipsGeneratedEvent.Ship ship2 = createShip2();

        ShipsGeneratedEvent shipsGeneratedEvent = new ShipsGeneratedEvent();
        shipsGeneratedEvent.ships.add(ship1);
        shipsGeneratedEvent.ships.add(ship2);

        battleshipsController.handleShipsGeneratedEvent(shipsGeneratedEvent);

        // Then
        ArgumentCaptor<Ship> shipArgumentCaptor = ArgumentCaptor.forClass(Ship.class);

        verify(playerBoardMock, times(2)).placeShip(shipArgumentCaptor.capture());

        List<Ship> capturedShips = shipArgumentCaptor.getAllValues();

        assertThat(capturedShips.get(0)).isEqualTo(Ship.createHorizontal(Position.of(1, 1), ShipSize.FOUR));
        assertThat(capturedShips.get(1)).isEqualTo(Ship.createVertical(Position.of(4, 6), ShipSize.TWO));
    }

    private ShipsGeneratedEvent.Ship createShip1() {
        return new ShipsGeneratedEvent.Ship(
                new ShipsGeneratedEvent.Ship.Position(1, 1),
                4,
                ShipsGeneratedEvent.Ship.Orientation.HORIZONTAL);
    }

    private ShipsGeneratedEvent.Ship createShip2() {
        return new ShipsGeneratedEvent.Ship(
                new ShipsGeneratedEvent.Ship.Position(4, 6),
                2,
                ShipsGeneratedEvent.Ship.Orientation.VERTICAL);
    }

}