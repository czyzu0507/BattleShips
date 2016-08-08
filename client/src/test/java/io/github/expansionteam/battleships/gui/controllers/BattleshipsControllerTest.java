package io.github.expansionteam.battleships.gui.controllers;

import com.google.common.eventbus.EventBus;
import io.github.expansionteam.battleships.common.events.*;
import io.github.expansionteam.battleships.common.events.data.PositionData;
import io.github.expansionteam.battleships.common.events.data.ShipData;
import io.github.expansionteam.battleships.common.events.data.ShipOrientationData;
import io.github.expansionteam.battleships.common.events.data.ShipSizeData;
import io.github.expansionteam.battleships.gui.models.*;
import javafx.scene.layout.BorderPane;
import org.mockito.ArgumentCaptor;
import org.testng.annotations.Test;

import java.util.ArrayList;
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
        EventDataConverter eventDataConverterMock = mock(EventDataConverter.class);
        when(eventDataConverterMock.convertShipDataToShipGuiModel(isA(ShipData.class))).thenReturn(
                Ship.createHorizontal(Position.of(1, 1), ShipSize.FOUR),
                Ship.createVertical(Position.of(4, 6), ShipSize.TWO
                ));

        BorderPane boardAreaMock = mock(BorderPane.class);
        PlayerBoard playerBoardMock = mock(PlayerBoard.class);

        BattleshipsController battleshipsController = new BattleshipsController();
        battleshipsController.eventBus = eventBusMock;
        battleshipsController.eventDataConverter = eventDataConverterMock;
        battleshipsController.boardArea = boardAreaMock;
        battleshipsController.playerBoard = playerBoardMock;

        // When
        List<ShipData> shipsData = new ArrayList<>();
        shipsData.add(createShip1());
        shipsData.add(createShip2());

        battleshipsController.handleShipsGeneratedEvent(new ShipsGeneratedEvent(shipsData));

        // Then
        ArgumentCaptor<Ship> shipArgumentCaptor = ArgumentCaptor.forClass(Ship.class);

        verify(playerBoardMock, times(2)).placeShip(shipArgumentCaptor.capture());

        List<Ship> capturedShips = shipArgumentCaptor.getAllValues();

        assertThat(capturedShips.get(0)).isEqualTo(Ship.createHorizontal(Position.of(1, 1), ShipSize.FOUR));
        assertThat(capturedShips.get(1)).isEqualTo(Ship.createVertical(Position.of(4, 6), ShipSize.TWO));
    }

    @Test
    public void handleEmptyFieldHitEvent() {
        // Given
        OpponentBoard opponentBoardMock = mock(OpponentBoard.class);

        BattleshipsController battleshipsController = new BattleshipsController();
        battleshipsController.opponentBoard = opponentBoardMock;

        // When
        battleshipsController.handleEmptyFieldHitEvent(new EmptyFieldHitEvent(PositionData.of(3, 4)));

        // Then
        ArgumentCaptor<Position> positionArgumentCaptor = ArgumentCaptor.forClass(Position.class);
        verify(opponentBoardMock).fieldWasShotAndMissed(positionArgumentCaptor.capture());

        assertThat(positionArgumentCaptor.getValue()).isEqualTo(Position.of(3, 4));
    }

    @Test
    public void handleShipHitEvent() {
        // Given
        OpponentBoard opponentBoardMock = mock(OpponentBoard.class);

        BattleshipsController battleshipsController = new BattleshipsController();
        battleshipsController.opponentBoard = opponentBoardMock;

        // When
        battleshipsController.handleShipHitEvent(new ShipHitEvent(PositionData.of(2, 2)));

        // Then
        ArgumentCaptor<Position> positionArgumentCaptor = ArgumentCaptor.forClass(Position.class);
        verify(opponentBoardMock).fieldWasShotAndHit(positionArgumentCaptor.capture());
        assertThat(positionArgumentCaptor.getValue()).isEqualTo(Position.of(2, 2));
    }

    private ShipData createShip1() {
        return new ShipData(PositionData.of(1, 1), ShipSizeData.of(4), ShipOrientationData.HORIZONTAL);
    }

    private ShipData createShip2() {
        return new ShipData(PositionData.of(4, 6), ShipSizeData.of(2), ShipOrientationData.VERTICAL);
    }

}