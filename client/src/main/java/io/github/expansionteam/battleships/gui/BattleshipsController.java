package io.github.expansionteam.battleships.gui;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import io.github.expansionteam.battleships.common.annotations.EventConsumer;
import io.github.expansionteam.battleships.common.annotations.EventProducer;
import io.github.expansionteam.battleships.common.events.*;
import io.github.expansionteam.battleships.common.events.data.NextTurnData;
import io.github.expansionteam.battleships.common.events.opponentboard.OpponentEmptyFieldHitEvent;
import io.github.expansionteam.battleships.common.events.opponentboard.OpponentGameEndEvent;
import io.github.expansionteam.battleships.common.events.opponentboard.OpponentShipDestroyedEvent;
import io.github.expansionteam.battleships.common.events.opponentboard.OpponentShipHitEvent;
import io.github.expansionteam.battleships.common.events.playerboard.PlayerEmptyFieldHitEvent;
import io.github.expansionteam.battleships.common.events.playerboard.PlayerGameEndEvent;
import io.github.expansionteam.battleships.common.events.playerboard.PlayerShipDestroyedEvent;
import io.github.expansionteam.battleships.common.events.playerboard.PlayerShipHitEvent;
import io.github.expansionteam.battleships.gui.models.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
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

    @Inject
    GameState gameState;

    @Inject
    GuiAsyncTask guiAsyncTask;

    @FXML
    HBox boardArea;

    @FXML
    VBox opponentBoardArea;

    @FXML
    VBox playerBoardArea;

    @FXML
    VBox messageArea;

    @FXML
    Label message;

    OpponentBoard opponentBoard;
    PlayerBoard playerBoard;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setMessage("Waiting for opponent...");

        opponentBoard = boardFactory.createEmptyOpponentBoard();
        opponentBoardArea.getChildren().add(opponentBoard);

        playerBoard = boardFactory.createEmptyPlayerBoard();
        playerBoardArea.getChildren().add(playerBoard);
        boardArea.setDisable(true);

        StartGameEvent startGameEvent = new StartGameEvent();
        eventBus.post(startGameEvent);

        log.debug("Post: " + startGameEvent.getClass().getSimpleName());
    }

    @Subscribe
    public void handleOpponentArrivedEvent(OpponentArrivedEvent event) {
        log.debug("Handle: " + event.getClass().getSimpleName());

        boardArea.setDisable(false);
        setMessage("");

        GenerateShipsEvent generateShipsEvent = new GenerateShipsEvent();
        eventBus.post(generateShipsEvent);

        log.debug("Post: " + generateShipsEvent.getClass().getSimpleName());
    }

    @Subscribe
    public void handleShipsGeneratedEvent(ShipsGeneratedEvent event) {
        log.debug("Handle: " + event.getClass().getSimpleName());

        event.getShips().stream().forEach(s -> {
            Ship ship = eventDataConverter.convertShipDataToShipGuiModel(s);
            playerBoard.placeShip(ship);
        });

        updateGameState(event.getNextTurn());
    }

    @Subscribe
    public void handleOpponentEmptyFieldHitEvent(OpponentEmptyFieldHitEvent event) {
        log.debug("Handle: " + event.getClass().getSimpleName());
        opponentBoard.positionWasShotAndMissed(Position.of(event.getPosition().getX(), event.getPosition().getY()));

        updateGameState(event.getNextTurn());
    }

    @Subscribe
    public void handlePlayerEmptyFieldHitEvent(PlayerEmptyFieldHitEvent event) {
        log.debug("Handle: " + event.getClass().getSimpleName());
        playerBoard.positionWasShot(Position.of(event.getPosition().getX(), event.getPosition().getY()));

        updateGameState(event.getNextTurn());
    }

    @Subscribe
    public void handleOpponentShipHitEvent(OpponentShipHitEvent event) {
        log.debug("Handle: " + event.getClass().getSimpleName());
        opponentBoard.positionWasShotAndHit(Position.of(event.getPosition().getX(), event.getPosition().getY()));

        updateGameState(event.getNextTurn());
    }

    @Subscribe
    public void handlePlayerShipHitEvent(PlayerShipHitEvent event) {
        log.debug("Handle: " + event.getClass().getSimpleName());
        playerBoard.positionWasShot(Position.of(event.getPosition().getX(), event.getPosition().getY()));

        updateGameState(event.getNextTurn());
    }

    @Subscribe
    public void handleOpponentShipDestroyedEvent(OpponentShipDestroyedEvent event) {
        log.debug("Handle: " + event.getClass().getSimpleName());

        opponentBoard.positionWasShotAndHit(Position.of(event.getPosition().getX(), event.getPosition().getY()));
        event.getAdjacentPositions().stream().forEach(p -> opponentBoard.positionWasShotAndMissed(Position.of(p.getX(), p.getY())));

        updateGameState(event.getNextTurn());
    }

    @Subscribe
    public void handlePlayerShipDestroyedEvent(PlayerShipDestroyedEvent event) {
        log.debug("Handle: " + event.getClass().getSimpleName());

        playerBoard.positionWasShot(Position.of(event.getPosition().getX(), event.getPosition().getY()));
        event.getAdjacentPositions().stream().forEach(p -> playerBoard.positionWasShot(Position.of(p.getX(), p.getY())));

        updateGameState(event.getNextTurn());
    }

    @Subscribe
    public void handleOpponentGameEndEvent(OpponentGameEndEvent event) {
        log.debug("Handle: " + event.getClass().getSimpleName());

        opponentBoard.positionWasShotAndHit(Position.of(event.getPosition().getX(), event.getPosition().getY()));
        event.getAdjacentPositions().stream().forEach(p -> opponentBoard.positionWasShotAndMissed(Position.of(p.getX(), p.getY())));

        setMessage("You won!");
        boardArea.setDisable(true);
    }


    @Subscribe
    public void handlePlayerGameEndEvent(PlayerGameEndEvent event) {
        log.debug("Handle: " + event.getClass().getSimpleName());

        playerBoard.positionWasShot(Position.of(event.getPosition().getX(), event.getPosition().getY()));
        event.getAdjacentPositions().stream().forEach(p -> playerBoard.positionWasShot(Position.of(p.getX(), p.getY())));

        setMessage("You lose!");
        boardArea.setDisable(true);
    }

    private void updateGameState(NextTurnData nextTurn) {
        if (nextTurn.equals(NextTurnData.OPPONENT_TURN)) {
            gameState.setCurrentTurn(GameState.Turn.OPPONENT_TURN);
            setMessage("Opponent's turn.");
            boardArea.setDisable(true);

            eventBus.post(new WaitForOpponentEvent());
        } else {
            setMessage("Your turn.");
            boardArea.setDisable(false);
            gameState.setCurrentTurn(GameState.Turn.PLAYER_TURN);
        }
    }

    private void setMessage(String text) {
        guiAsyncTask.runLater(() -> message.setText(text));
    }

}

