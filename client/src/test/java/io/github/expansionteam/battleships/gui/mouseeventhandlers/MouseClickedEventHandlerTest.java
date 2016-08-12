package io.github.expansionteam.battleships.gui.mouseeventhandlers;

import com.google.common.eventbus.EventBus;
import io.github.expansionteam.battleships.common.events.ShootPositionEvent;
import io.github.expansionteam.battleships.gui.GameState;
import io.github.expansionteam.battleships.gui.models.Field;
import io.github.expansionteam.battleships.gui.models.OpponentField;
import io.github.expansionteam.battleships.gui.models.Position;
import javafx.scene.input.MouseEvent;
import org.testng.annotations.Test;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;

public class MouseClickedEventHandlerTest {

    @Test
    public void sendEventIfFieldWasNotAlreadyShot() {
        // given
        EventBus eventBusMock = mock(EventBus.class);

        GameState gameStateMock = mock(GameState.class);
        when(gameStateMock.getCurrentTurn()).thenReturn(GameState.Turn.PLAYER_TURN);

        MouseClickedEventHandler mouseClickedEventHandler = new MouseClickedEventHandler(eventBusMock, gameStateMock);

        // when
        MouseEvent mouseEventMock = mock(MouseEvent.class);

        OpponentField fieldMock = mock(OpponentField.class);
        when(fieldMock.getPosition()).thenReturn(Position.of(1, 2));
        when(fieldMock.wasShot()).thenReturn(false);

        when(mouseEventMock.getSource()).thenReturn(fieldMock);

        mouseClickedEventHandler.handleOpponentBoard(mouseEventMock);

        // then
        verify(eventBusMock).post(isA(ShootPositionEvent.class));
    }

    @Test
    public void doNotSendEventIfFieldWasAlreadyShot() {
        EventBus eventBusMock = mock(EventBus.class);

        GameState gameStateMock = mock(GameState.class);
        when(gameStateMock.getCurrentTurn()).thenReturn(GameState.Turn.PLAYER_TURN);

        MouseClickedEventHandler mouseClickedEventHandler = new MouseClickedEventHandler(eventBusMock, gameStateMock);

        // when
        MouseEvent mouseEventMock = mock(MouseEvent.class);

        OpponentField fieldMock = mock(OpponentField.class);
        when(fieldMock.getPosition()).thenReturn(Position.of(1, 2));
        when(fieldMock.wasShot()).thenReturn(true);

        when(mouseEventMock.getSource()).thenReturn(fieldMock);

        mouseClickedEventHandler.handleOpponentBoard(mouseEventMock);

        // then
        verify(eventBusMock, times(0)).post(isA(ShootPositionEvent.class));
    }

    @Test
    public void doNotSendEventIfItIsOpponentTurn() {
        // given
        EventBus eventBusMock = mock(EventBus.class);

        GameState gameStateMock = mock(GameState.class);
        when(gameStateMock.getCurrentTurn()).thenReturn(GameState.Turn.OPPONENT_TURN);

        MouseClickedEventHandler mouseClickedEventHandler = new MouseClickedEventHandler(eventBusMock, gameStateMock);

        // when
        MouseEvent mouseEventMock = mock(MouseEvent.class);

        OpponentField fieldMock = mock(OpponentField.class);
        when(fieldMock.getPosition()).thenReturn(Position.of(1, 2));
        when(fieldMock.wasShot()).thenReturn(false);

        when(mouseEventMock.getSource()).thenReturn(fieldMock);

        mouseClickedEventHandler.handleOpponentBoard(mouseEventMock);

        // then
        verify(eventBusMock, times(0)).post(isA(ShootPositionEvent.class));
    }

}