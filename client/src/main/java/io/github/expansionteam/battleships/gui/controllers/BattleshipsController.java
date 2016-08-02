package io.github.expansionteam.battleships.gui.controllers;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import io.github.expansionteam.battleships.MainLauncher;
import io.github.expansionteam.battleships.gui.models.BoardFactory;
import io.github.expansionteam.battleships.gui.models.OpponentBoard;
import io.github.expansionteam.battleships.gui.models.PlayerBoard;
import io.github.expansionteam.battleships.logic.events.OpponentArrivedEvent;
import io.github.expansionteam.battleships.logic.events.StartGameEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class BattleshipsController implements Initializable {

    private final static Logger log = Logger.getLogger(MainLauncher.class.getSimpleName());

    @Inject
    private EventBus eventBus;

    @Inject
    private BoardFactory boardFactory;

    @FXML
    private BorderPane boardArea;

    @FXML
    private VBox opponentBoardArea;

    @FXML
    private VBox playerBoardArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        OpponentBoard opponentBoard = boardFactory.createEmptyOpponentBoard();
        PlayerBoard playerBoard = boardFactory.createEmptyPlayerBoard();

        boardArea.setVisible(false);
        opponentBoardArea.getChildren().add(opponentBoard);
        playerBoardArea.getChildren().add(playerBoard);

        eventBus.post(new StartGameEvent());
    }

    @Subscribe
    public void handleOpponentArrived(OpponentArrivedEvent event) {
        log.debug("[GUI] Handle OpponentArrivedEvent.");
        boardArea.setVisible(true);
    }

}
