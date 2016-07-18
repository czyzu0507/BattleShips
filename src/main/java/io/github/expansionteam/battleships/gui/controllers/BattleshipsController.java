package io.github.expansionteam.battleships.gui.controllers;

import io.github.expansionteam.battleships.gui.models.Board;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class BattleshipsController implements Initializable {

    @FXML
    private VBox opponentBoard;

    @FXML
    private VBox playerBoard;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        opponentBoard.getChildren().add(new Board());
        playerBoard.getChildren().add(new Board());
    }

    @FXML
    private void exitGame() {
        Platform.exit();
        System.exit(0);
    }

}
