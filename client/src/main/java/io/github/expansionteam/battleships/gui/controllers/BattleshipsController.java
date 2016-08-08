package io.github.expansionteam.battleships.gui.controllers;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import io.github.expansionteam.battleships.common.annotations.EventConsumer;
import io.github.expansionteam.battleships.common.annotations.EventProducer;
import io.github.expansionteam.battleships.common.events.*;
import io.github.expansionteam.battleships.gui.models.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

@EventProducer
@EventConsumer
public class BattleshipsController implements Initializable {

    private final static Logger log = Logger.getLogger(BattleshipsController.class);

    @Inject
    EventBus eventBus;

    @Inject
    BoardFactory boardFactory;

    @Inject
    EventDataConverter eventDataConverter;

    @FXML
    BorderPane boardArea;

    @FXML
    VBox opponentBoardArea;

    @FXML
    VBox playerBoardArea;

    OpponentBoard opponentBoard;
    PlayerBoard playerBoard;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        opponentBoard = boardFactory.createEmptyOpponentBoard();
        opponentBoardArea.getChildren().add(opponentBoard);

        playerBoard = boardFactory.createEmptyPlayerBoard();
        playerBoardArea.getChildren().add(playerBoard);

        boardArea.setVisible(false);

        log.trace("Post StartGameEvent.");
        eventBus.post(new StartGameEvent());
    }

    @Subscribe
    public void handleOpponentArrivedEvent(OpponentArrivedEvent event) {
        log.trace("Handle OpponentArrivedEvent.");

        boardArea.setVisible(true);

        log.trace("Post GenerateShipsEvent.");
        eventBus.post(new GenerateShipsEvent());
    }

    @Subscribe
    public void handleShipsGeneratedEvent(ShipsGeneratedEvent event) {
        log.trace("Handle ShipsGeneratedEvent.");

        event.getShips().stream().forEach(s -> {
            Ship ship = eventDataConverter.convertShipDataToShipGuiModel(s);
            playerBoard.placeShip(ship);
        });
    }

    @Subscribe
    public void handleEmptyFieldHitEvent(EmptyFieldHitEvent event) {
        log.trace("Handle EmptyFieldHitEvent.");
        opponentBoard.fieldWasShotAndMissed(Position.of(event.getPosition().getX(), event.getPosition().getY()));
    }

    @Subscribe
    public void handleShipHitEvent(ShipHitEvent event) {
        log.trace("Handle ShipHitEvent.");
        opponentBoard.fieldWasShotAndHit(Position.of(event.getPosition().getX(), event.getPosition().getY()));
    }

}
