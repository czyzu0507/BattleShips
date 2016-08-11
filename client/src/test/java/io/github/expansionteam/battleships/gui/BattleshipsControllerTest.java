package io.github.expansionteam.battleships.gui;

import com.google.common.eventbus.EventBus;
import io.github.expansionteam.battleships.common.events.GenerateShipsEvent;
import io.github.expansionteam.battleships.common.events.OpponentArrivedEvent;
import io.github.expansionteam.battleships.common.events.ShipsGeneratedEvent;
import io.github.expansionteam.battleships.common.events.data.*;
import io.github.expansionteam.battleships.common.events.opponentboard.OpponentEmptyFieldHitEvent;
import io.github.expansionteam.battleships.common.events.opponentboard.OpponentShipDestroyedEvent;
import io.github.expansionteam.battleships.common.events.opponentboard.OpponentShipHitEvent;
import io.github.expansionteam.battleships.common.events.playerboard.PlayerEmptyFieldHitEvent;
import io.github.expansionteam.battleships.common.events.playerboard.PlayerShipDestroyedEvent;
import io.github.expansionteam.battleships.common.events.playerboard.PlayerShipHitEvent;
import io.github.expansionteam.battleships.gui.models.*;
import javafx.scene.layout.BorderPane;
import org.mockito.ArgumentCaptor;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;

public class BattleshipsControllerTest {

    @Test
    public void handleOpponentArrivedEvent() {
        // given
        EventBus eventBusMock = mock(EventBus.class);
        GameState gameStateMock = mock(GameState.class);
        BorderPane boardAreaMock = mock(BorderPane.class);

        BattleshipsController battleshipsController = new BattleshipsController();
        battleshipsController.eventBus = eventBusMock;
        battleshipsController.boardArea = boardAreaMock;
        battleshipsController.gameState = gameStateMock;

        // when
        battleshipsController.handleOpponentArrivedEvent(new OpponentArrivedEvent());

        // then
        verify(eventBusMock).post(isA(GenerateShipsEvent.class));
    }

    @Test
    public void handleShipsGeneratedEvent() {
        // given
        EventBus eventBusMock = mock(EventBus.class);
        EventDataConverter eventDataConverterMock = mock(EventDataConverter.class);
        when(eventDataConverterMock.convertShipDataToShipGuiModel(isA(ShipData.class))).thenReturn(
                Ship.createHorizontal(Position.of(1, 1), ShipSize.FOUR),
                Ship.createVertical(Position.of(4, 6), ShipSize.TWO
                ));
        GameState gameStateMock = mock(GameState.class);

        BorderPane boardAreaMock = mock(BorderPane.class);
        PlayerBoard playerBoardMock = mock(PlayerBoard.class);

        BattleshipsController battleshipsController = new BattleshipsController();
        battleshipsController.eventBus = eventBusMock;
        battleshipsController.eventDataConverter = eventDataConverterMock;
        battleshipsController.gameState = gameStateMock;
        battleshipsController.boardArea = boardAreaMock;
        battleshipsController.playerBoard = playerBoardMock;

        // when
        List<ShipData> shipsData = new ArrayList<>();
        shipsData.add(createShip1());
        shipsData.add(createShip2());

        NextTurnData nextTurn = NextTurnData.PLAYER_TURN;

        battleshipsController.handleShipsGeneratedEvent(new ShipsGeneratedEvent(shipsData, nextTurn));

        // then
        ArgumentCaptor<Ship> shipArgumentCaptor = ArgumentCaptor.forClass(Ship.class);

        verify(playerBoardMock, times(2)).placeShip(shipArgumentCaptor.capture());

        List<Ship> capturedShips = shipArgumentCaptor.getAllValues();

        assertThat(capturedShips.get(0)).isEqualTo(Ship.createHorizontal(Position.of(1, 1), ShipSize.FOUR));
        assertThat(capturedShips.get(1)).isEqualTo(Ship.createVertical(Position.of(4, 6), ShipSize.TWO));
    }

    @Test
    public void handleOpponentEmptyFieldHitEvent() {
        // given
        EventBus eventBusMock = mock(EventBus.class);
        GameState gameStateMock = mock(GameState.class);
        OpponentBoard opponentBoardMock = mock(OpponentBoard.class);

        BattleshipsController battleshipsController = new BattleshipsController();
        battleshipsController.eventBus = eventBusMock;
        battleshipsController.gameState = gameStateMock;
        battleshipsController.opponentBoard = opponentBoardMock;

        // when
        battleshipsController.handleOpponentEmptyFieldHitEvent(
                new OpponentEmptyFieldHitEvent(PositionData.of(3, 4), NextTurnData.OPPONENT_TURN));

        // then
        ArgumentCaptor<Position> positionArgumentCaptor = ArgumentCaptor.forClass(Position.class);
        verify(opponentBoardMock).positionWasShotAndMissed(positionArgumentCaptor.capture());

        assertThat(positionArgumentCaptor.getValue()).isEqualTo(Position.of(3, 4));
    }

    @Test
    public void handlePlayerEmptyFieldHitEvent() {
        // given
        PlayerBoard playerBoardMock = mock(PlayerBoard.class);
        GameState gameStateMock = mock(GameState.class);

        BattleshipsController battleshipsController = new BattleshipsController();
        battleshipsController.playerBoard = playerBoardMock;
        battleshipsController.gameState = gameStateMock;

        // when
        battleshipsController.handlePlayerEmptyFieldHitEvent(
                new PlayerEmptyFieldHitEvent(PositionData.of(3, 4), NextTurnData.PLAYER_TURN));

        // then
        ArgumentCaptor<Position> positionArgumentCaptor = ArgumentCaptor.forClass(Position.class);
        verify(playerBoardMock).positionWasShot(positionArgumentCaptor.capture());

        assertThat(positionArgumentCaptor.getValue()).isEqualTo(Position.of(3, 4));
    }


    @Test
    public void handleOpponentShipHitEvent() {
        // given
        OpponentBoard opponentBoardMock = mock(OpponentBoard.class);
        GameState gameStateMock = mock(GameState.class);

        BattleshipsController battleshipsController = new BattleshipsController();
        battleshipsController.opponentBoard = opponentBoardMock;
        battleshipsController.gameState = gameStateMock;

        // when
        battleshipsController.handleOpponentShipHitEvent(new OpponentShipHitEvent(PositionData.of(2, 2), NextTurnData.PLAYER_TURN));

        // then
        ArgumentCaptor<Position> positionArgumentCaptor = ArgumentCaptor.forClass(Position.class);
        verify(opponentBoardMock).positionWasShotAndHit(positionArgumentCaptor.capture());
        assertThat(positionArgumentCaptor.getValue()).isEqualTo(Position.of(2, 2));
    }

    @Test
    public void handlePlayerShipHitEvent() {
        // given
        EventBus eventBusMock = mock(EventBus.class);
        GameState gameStateMock = mock(GameState.class);
        PlayerBoard playerBoardMock = mock(PlayerBoard.class);

        BattleshipsController battleshipsController = new BattleshipsController();
        battleshipsController.eventBus = eventBusMock;
        battleshipsController.playerBoard = playerBoardMock;
        battleshipsController.gameState = gameStateMock;

        // when
        battleshipsController.handlePlayerShipHitEvent(new PlayerShipHitEvent(PositionData.of(2, 2), NextTurnData.OPPONENT_TURN));

        // then
        ArgumentCaptor<Position> positionArgumentCaptor = ArgumentCaptor.forClass(Position.class);
        verify(playerBoardMock).positionWasShot(positionArgumentCaptor.capture());
        assertThat(positionArgumentCaptor.getValue()).isEqualTo(Position.of(2, 2));
    }

    @Test
    public void handleOpponentShipDestroyedEvent() {
        // given
        OpponentBoard opponentBoardMock = mock(OpponentBoard.class);
        GameState gameStateMock = mock(GameState.class);

        BattleshipsController battleshipsController = new BattleshipsController();
        battleshipsController.opponentBoard = opponentBoardMock;
        battleshipsController.gameState = gameStateMock;

        // when
        PositionData position = PositionData.of(1, 4);
        List<PositionData> adjacentPositions = new ArrayList<>(Arrays.asList(PositionData.of(3, 2), PositionData.of(5, 7)));
        NextTurnData nextTurn = NextTurnData.PLAYER_TURN;
        battleshipsController.handleOpponentShipDestroyedEvent(new OpponentShipDestroyedEvent(
                position, adjacentPositions, nextTurn));

        // then
        ArgumentCaptor<Position> hitPositionArgumentCaptor = ArgumentCaptor.forClass(Position.class);
        verify(opponentBoardMock).positionWasShotAndHit(hitPositionArgumentCaptor.capture());

        assertThat(hitPositionArgumentCaptor.getAllValues().get(0).getX()).isEqualTo(1);
        assertThat(hitPositionArgumentCaptor.getAllValues().get(0).getY()).isEqualTo(4);

        ArgumentCaptor<Position> missedPositionArgumentCaptor = ArgumentCaptor.forClass(Position.class);
        verify(opponentBoardMock, times(2)).positionWasShotAndMissed(missedPositionArgumentCaptor.capture());

        assertThat(missedPositionArgumentCaptor.getAllValues().get(0).getX()).isEqualTo(3);
        assertThat(missedPositionArgumentCaptor.getAllValues().get(0).getY()).isEqualTo(2);

        assertThat(missedPositionArgumentCaptor.getAllValues().get(1).getX()).isEqualTo(5);
        assertThat(missedPositionArgumentCaptor.getAllValues().get(1).getY()).isEqualTo(7);
    }

    @Test
    public void handlePlayerShipDestroyedEvent() {
        // given
        PlayerBoard playerBoardMock = mock(PlayerBoard.class);
        GameState gameStateMock = mock(GameState.class);

        BattleshipsController battleshipsController = new BattleshipsController();
        battleshipsController.playerBoard = playerBoardMock;
        battleshipsController.gameState = gameStateMock;

        // when
        PositionData position = PositionData.of(1, 4);
        List<PositionData> adjacentPositions = new ArrayList<>(Arrays.asList(PositionData.of(3, 2), PositionData.of(5, 7)));
        NextTurnData nextTurn = NextTurnData.PLAYER_TURN;
        battleshipsController.handlePlayerShipDestroyedEvent(new PlayerShipDestroyedEvent(
                position, adjacentPositions, nextTurn));

        // then
        ArgumentCaptor<Position> positionArgumentCaptor = ArgumentCaptor.forClass(Position.class);
        verify(playerBoardMock, times(3)).positionWasShot(positionArgumentCaptor.capture());

        assertThat(positionArgumentCaptor.getAllValues().get(0).getX()).isEqualTo(1);
        assertThat(positionArgumentCaptor.getAllValues().get(0).getY()).isEqualTo(4);

        assertThat(positionArgumentCaptor.getAllValues().get(1).getX()).isEqualTo(3);
        assertThat(positionArgumentCaptor.getAllValues().get(1).getY()).isEqualTo(2);

        assertThat(positionArgumentCaptor.getAllValues().get(2).getX()).isEqualTo(5);
        assertThat(positionArgumentCaptor.getAllValues().get(2).getY()).isEqualTo(7);
    }

    private ShipData createShip1() {
        return new ShipData(PositionData.of(1, 1), ShipSizeData.of(4), ShipOrientationData.HORIZONTAL);
    }

    private ShipData createShip2() {
        return new ShipData(PositionData.of(4, 6), ShipSizeData.of(2), ShipOrientationData.VERTICAL);
    }

}