package io.github.expansionteam.battleships.gui.mouseeventhandlers;

import javafx.scene.input.MouseEvent;
import org.apache.log4j.Logger;

public class MouseClickedEventHandler {

    private final static Logger log = Logger.getLogger(MouseClickedEventHandler.class);

    public void handlePlayerBoard(MouseEvent event) {
        log.debug("Handle MouseEvent: mouse click on player board.");
    }

    public void handleOpponentBoard(MouseEvent event) {
        log.debug("Handle MouseEvent: mouse click on opponent board.");
    }

}
