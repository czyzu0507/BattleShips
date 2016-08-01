package io.github.expansionteam.battleships.gui.models;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ShipSizeTest {

    @DataProvider(name="shipSize")
    private Object[][] getShipSizeData() {
        return new Object[][] {{ShipSize.ONE, 1}, {ShipSize.TWO, 2}, {ShipSize.THREE, 3}, {ShipSize.FOUR, 4}};
    }


    @Test(dataProvider = "shipSize")
    public void testGetValue(ShipSize shipSize, int value) throws Exception {
        //Assert
        assertEquals(shipSize.getValue(), value);
    }

}