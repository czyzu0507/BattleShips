package io.github.expansionteam.battleships.gui.controllers;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import io.github.expansionteam.battleships.gui.models.*;
import io.github.expansionteam.battleships.logic.events.GenerateShipsEvent;
import io.github.expansionteam.battleships.logic.events.OpponentArrivedEvent;
import io.github.expansionteam.battleships.logic.events.ShipsGeneratedEvent;
import io.github.expansionteam.battleships.logic.events.StartGameEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class BattleshipsController implements Initializable {

    private final static Logger log = Logger.getLogger(BattleshipsController.class);

    @Inject
    EventBus eventBus;

    @Inject
    BoardFactory boardFactory;

    @FXML
    BorderPane boardArea;

    @FXML
    VBox opponentBoardArea;

    @FXML
    VBox playerBoardArea;

    private OpponentBoard opponentBoard;
    private PlayerBoard playerBoard;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        opponentBoard = boardFactory.createEmptyOpponentBoard();
        opponentBoardArea.getChildren().add(opponentBoard);

        playerBoard = boardFactory.createEmptyPlayerBoard();
        playerBoardArea.getChildren().add(playerBoard);

        boardArea.setVisible(false);
        eventBus.post(new StartGameEvent());
    }

    @Subscribe
    public void handleOpponentArrivedEvent(OpponentArrivedEvent event) {
        log.debug("Handle OpponentArrivedEvent.");

        boardArea.setVisible(true);
        eventBus.post(new GenerateShipsEvent());
    }

    @Subscribe
    public void handleShipsGeneratedEvent(ShipsGeneratedEvent event) {
        log.debug("Handle ShipsGeneratedEvent.");
        event.ships.stream().forEach(s -> {
            Position position = Position.of(s.position.x, s.position.y);

            ShipSize size;
            switch (s.size) {
                case 1:
                    size = ShipSize.ONE;
                    break;
                case 2:
                    size = ShipSize.TWO;
                    break;
                case 3:
                    size = ShipSize.THREE;
                    break;
                case 4:
                    size = ShipSize.FOUR;
                    break;
                default:
                    throw new AssertionError();
            }

            Ship ship;
            if (s.orientation.equals(ShipsGeneratedEvent.Ship.Orientation.HORIZONTAL)) {
                ship = Ship.createHorizontal(position, size);
            } else {
                ship = Ship.createVertical(position, size);
            }
            playerBoard.placeShip(ship);
        });
    }

}
