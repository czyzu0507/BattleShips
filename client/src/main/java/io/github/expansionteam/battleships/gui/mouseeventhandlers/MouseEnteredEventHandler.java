package io.github.expansionteam.battleships.gui.mouseeventhandlers;

import io.github.expansionteam.battleships.gui.models.OpponentField;
import javafx.scene.input.MouseEvent;
import org.apache.log4j.Logger;

public class MouseEnteredEventHandler {

    private final static Logger log = Logger.getLogger(MouseEnteredEventHandler.class);

    public void handlePlayerBoard(MouseEvent event) {
    }

    public void handleOpponentBoard(MouseEvent event) {
        OpponentField field = (OpponentField) event.getSource();
        field.startTargeting();
    }

}
