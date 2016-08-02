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
        PlayerBoard playerBoard = boardFactory.createEmptyPlayerBoard();

        opponentBoardArea.getChildren().add(opponentBoard);
        playerBoardArea.getChildren().add(playerBoard);
    }

}
