package io.github.expansionteam.battleships.gui.mouseeventhandlers;

import com.google.common.eventbus.EventBus;
import io.github.expansionteam.battleships.common.events.ShootPositionEvent;
import io.github.expansionteam.battleships.gui.models.Field;
import io.github.expansionteam.battleships.gui.models.Position;
import javafx.scene.input.MouseEvent;
import org.testng.annotations.Test;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MouseClickedEventHandlerTest {

    @Test
    public void handleClickingOnOpponentBoard() {
        // Given
        EventBus eventBusMock = mock(EventBus.class);
        MouseClickedEventHandler mouseClickedEventHandler = new MouseClickedEventHandler(eventBusMock);

        // When
        MouseEvent mouseEventMock = mock(MouseEvent.class);

        Field fieldMock = mock(Field.class);
        when(fieldMock.getPosition()).thenReturn(Position.of(1, 2));

        when(mouseEventMock.getSource()).thenReturn(fieldMock);

        mouseClickedEventHandler.handleOpponentBoard(mouseEventMock);

        // Then
        verify(eventBusMock).post(isA(ShootPositionEvent.class));
    }

}