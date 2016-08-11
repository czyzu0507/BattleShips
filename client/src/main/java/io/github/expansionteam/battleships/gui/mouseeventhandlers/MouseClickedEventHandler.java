package io.github.expansionteam.battleships.gui.mouseeventhandlers;

import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import io.github.expansionteam.battleships.common.annotations.EventProducer;
import io.github.expansionteam.battleships.common.events.ShootPositionEvent;
import io.github.expansionteam.battleships.common.events.data.PositionData;
import io.github.expansionteam.battleships.gui.models.OpponentField;
import javafx.scene.input.MouseEvent;
import org.apache.log4j.Logger;

@EventProducer
public class MouseClickedEventHandler {

    private final EventBus eventBus;

    @Inject
    public MouseClickedEventHandler(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    private final static Logger log = Logger.getLogger(MouseClickedEventHandler.class);

    public void handlePlayerBoard(MouseEvent event) {
        log.debug("Handle MouseEvent: mouse click on player board.");
    }

    public void handleOpponentBoard(MouseEvent event) {
        log.debug("Handle MouseEvent: mouse click on opponent board.");

        OpponentField field = (OpponentField) event.getSource();

        if (!field.wasShot()) {
            eventBus.post(new ShootPositionEvent(PositionData.of(field.getPosition().getX(), field.getPosition().getY())));
        }
    }

}
