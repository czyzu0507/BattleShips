package io.github.expansionteam.battleships.gui;

import io.github.expansionteam.battleships.common.events.data.PositionData;
import io.github.expansionteam.battleships.common.events.data.ShipData;
import io.github.expansionteam.battleships.common.events.data.ShipOrientationData;
import io.github.expansionteam.battleships.common.events.data.ShipSizeData;
import io.github.expansionteam.battleships.gui.EventDataConverter;
import io.github.expansionteam.battleships.gui.models.Ship;
import io.github.expansionteam.battleships.gui.models.ShipOrientation;
import io.github.expansionteam.battleships.gui.models.ShipSize;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EventDataConverterTest {

    @Test
    public void convertShipDataToShipGuiModel() {
        // Given
        EventDataConverter eventDataConverter = new EventDataConverter();

        // When
        ShipData shipData = new ShipData(PositionData.of(1, 3), ShipSizeData.of(4), ShipOrientationData.HORIZONTAL);
        Ship ship = eventDataConverter.convertShipDataToShipGuiModel(shipData);

        // Then
        assertThat(ship.getPosition().getX()).isEqualTo(1);
        assertThat(ship.getPosition().getY()).isEqualTo(3);
        assertThat(ship.getSize()).isEqualTo(ShipSize.FOUR);
        assertThat(ship.getOrientation()).isEqualTo(ShipOrientation.HORIZONTAL);
    }

}