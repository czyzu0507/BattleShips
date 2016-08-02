package io.github.expansionteam.battleships.gui.controllers;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import io.github.expansionteam.battleships.gui.models.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class BattleshipsController implements Initializable {

    @Inject
    private BoardFactory boardFactory;

    @FXML
    private VBox opponentBoardArea;

    @FXML
    private VBox playerBoardArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        OpponentBoard opponentBoard = boardFactory.createEmptyOpponentBoard();
        opponentBoard.fieldWasShotAndHit(Position.of(0, 0));
        opponentBoard.fieldWasShotAndHit(Position.of(0, 1));
        opponentBoard.fieldWasShotAndHit(Position.of(0, 2));
        opponentBoard.fieldWasShotAndMissed(Position.of(5, 5));

        PlayerBoard playerBoard = boardFactory.createEmptyPlayerBoard();
        playerBoard.placeShip(Ship.createHorizontal(Position.of(6, 5), ShipSize.FOUR));
        playerBoard.placeShip(Ship.createVertical(Position.of(4, 4), ShipSize.THREE));
        playerBoard.placeShip(Ship.createVertical(Position.of(9, 0), ShipSize.THREE));
        playerBoard.placeShip(Ship.createHorizontal(Position.of(0, 9), ShipSize.TWO));
        playerBoard.placeShip(Ship.createVertical(Position.of(4, 0), ShipSize.TWO));
        playerBoard.placeShip(Ship.createHorizontal(Position.of(1, 1), ShipSize.TWO));
        playerBoard.placeShip(Ship.createVertical(Position.of(2, 7), ShipSize.ONE));
        playerBoard.placeShip(Ship.createVertical(Position.of(9, 9), ShipSize.ONE));
        playerBoard.placeShip(Ship.createVertical(Position.of(8, 7), ShipSize.ONE));
        playerBoard.placeShip(Ship.createVertical(Position.of(1, 5), ShipSize.ONE));

        opponentBoardArea.getChildren().add(opponentBoard);
        playerBoardArea.getChildren().add(playerBoard);
    }

    @FXML
    private void exitGame() {
        Platform.exit();
        System.exit(0);
    }

}
