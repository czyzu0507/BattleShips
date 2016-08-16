package io.github.expansionteam.battleships.gui;

import io.github.expansionteam.battleships.common.events.data.ShipData;
import io.github.expansionteam.battleships.gui.models.Position;
import io.github.expansionteam.battleships.gui.models.Ship;
import io.github.expansionteam.battleships.gui.models.ShipSize;

class EventDataConverter {

    Ship convertShipDataToShipGuiModel(ShipData shipData) {
        Position position = Position.of(shipData.getPosition().getX(), shipData.getPosition().getY());
        ShipSize shipSize = getShipSize(shipData);

        switch (shipData.getOrientation()) {
            case HORIZONTAL:
                return Ship.createHorizontal(position, shipSize);
            case VERTICAL:
                return Ship.createVertical(position, shipSize);
            default:
                throw new AssertionError();
        }
    }

    private ShipSize getShipSize(ShipData shipData) {
        switch (shipData.getSize().getValue()) {
            case 1:
                return ShipSize.ONE;
            case 2:
                return ShipSize.TWO;
            case 3:
                return ShipSize.THREE;
            case 4:
                return ShipSize.FOUR;
            default:
                throw new IllegalArgumentException("Ship size must be in range 1-4.");
        }
    }

}
