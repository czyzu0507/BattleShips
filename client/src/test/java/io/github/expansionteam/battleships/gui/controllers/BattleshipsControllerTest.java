package io.github.expansionteam.battleships.gui.controllers;

import com.google.common.eventbus.EventBus;
import io.github.expansionteam.battleships.logic.events.GenerateShipsEvent;
import io.github.expansionteam.battleships.logic.events.OpponentArrivedEvent;
import javafx.scene.layout.BorderPane;
import org.testng.annotations.Test;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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

}