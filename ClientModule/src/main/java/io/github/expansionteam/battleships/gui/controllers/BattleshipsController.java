package io.github.expansionteam.battleships.gui.controllers;

import io.github.expansionteam.battleships.gui.models.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class BattleshipsController implements Initializable {

    @FXML
    private VBox opponentBoardArea;

    @FXML
    private VBox playerBoardArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        BoardFactory boardFactory = new BoardFactory();

        Board opponentBoard = boardFactory.createEmptyOpponentBoard();

        Board playerBoard = boardFactory.createEmptyPlayerBoard();
        playerBoard.placeShip(Ship.create(Position.of(6, 5), ShipOrientation.HORIZONTAL, ShipSize.FOUR));
        playerBoard.placeShip(Ship.create(Position.of(4, 4), ShipOrientation.VERTICAL, ShipSize.THREE));
        playerBoard.placeShip(Ship.create(Position.of(9, 0), ShipOrientation.VERTICAL, ShipSize.THREE));
        playerBoard.placeShip(Ship.create(Position.of(0, 9), ShipOrientation.HORIZONTAL, ShipSize.TWO));
        playerBoard.placeShip(Ship.create(Position.of(4, 0), ShipOrientation.VERTICAL, ShipSize.TWO));
        playerBoard.placeShip(Ship.create(Position.of(1, 1), ShipOrientation.HORIZONTAL, ShipSize.TWO));
        playerBoard.placeShip(Ship.create(Position.of(2, 7), ShipOrientation.VERTICAL, ShipSize.ONE));
        playerBoard.placeShip(Ship.create(Position.of(9, 9), ShipOrientation.VERTICAL, ShipSize.ONE));
        playerBoard.placeShip(Ship.create(Position.of(8, 7), ShipOrientation.VERTICAL, ShipSize.ONE));
        playerBoard.placeShip(Ship.create(Position.of(1, 5), ShipOrientation.VERTICAL, ShipSize.ONE));

        opponentBoardArea.getChildren().add(opponentBoard);
        playerBoardArea.getChildren().add(playerBoard);
    }

    @FXML
    private void exitGame() {
        Platform.exit();
        System.exit(0);
    }

}
