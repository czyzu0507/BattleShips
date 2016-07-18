package io.github.expansionteam.battleships.engine;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import static io.github.expansionteam.battleships.engine.Orientation.*;
import static org.testng.Assert.*;

@Test()
public class ShipTest {
    private final Board board = new Board();
    private final Field startingField = new Field(1,1);
    private final Set<Field> fieldsOfShip1 = board.generateSetOfFieldsForShip(startingField, HORIZONTAL, 4);
    private final Set<Field> fieldsOfShip2 = board.generateSetOfFieldsForShip(startingField, VERTICAL, 4);
    private Ship ship1 = new Ship.ShipBuilder(fieldsOfShip1, 4).build();
    private Ship ship2 = new Ship.ShipBuilder(fieldsOfShip2, 4).build();

    @DataProvider(name = "horizontal")
    private Object[][] provideSetOfPositionsAndResultsHorizontal() {
        return new Object[][] {
                { new TreeSet<>(Arrays.asList( new Field(1,1), new Field(2,1), new Field(3,1), new Field(4,1) ) ), true},
                { new TreeSet<>(Arrays.asList( new Field(1,1), new Field(2,1), new Field(3,1), new Field(4,1), new Field(5,1) ) ), false},
                { new TreeSet<>(Arrays.asList( new Field(0,1), new Field(1,1), new Field(2,1), new Field(3,1), new Field(4,1) ) ), false}
        };
    }

    @DataProvider(name = "vertical")
    private Object[][] provideSetOfPositionsAndResultsVertical() {
        return new Object[][] {
                { new TreeSet<>(Arrays.asList( new Field(1,1), new Field(1,2), new Field(1,3), new Field(1,4) ) ), true},
                { new TreeSet<>(Arrays.asList( new Field(1,1), new Field(1,2), new Field(1,3), new Field(1,4), new Field(1,5) ) ), false},
                { new TreeSet<>(Arrays.asList( new Field(1,0), new Field(1,1), new Field(1,2), new Field(1,3), new Field(1,4) ) ), false}
        };
    }

    @DataProvider(name = "equality")
    private Object[][] provideSomeObjectsForEqualityTest() {
        return new Object[][] {
                {ship1, ship2, false},
                {ship1, new Ship.ShipBuilder(fieldsOfShip1, 4).build(), true},
                {ship2, new Ship.ShipBuilder(fieldsOfShip2, 4).build(), true},
                {ship1, new Ship.ShipBuilder(fieldsOfShip2, 4).build(), false},
                {ship2, new Ship.ShipBuilder(fieldsOfShip1, 4).build(), false}
        };
    }

    @DataProvider(name = "names")
    private Object[][] provideObjectsToCheckNames() {
        return new Object[][] {
                {new Ship.ShipBuilder(board.generateSetOfFieldsForShip(startingField, HORIZONTAL, 1), 1).build(), "Submarine"},
                {new Ship.ShipBuilder(board.generateSetOfFieldsForShip(startingField, HORIZONTAL, 2), 2).build(), "Destroyer"},
                {new Ship.ShipBuilder(board.generateSetOfFieldsForShip(startingField, HORIZONTAL, 3), 3).build(), "Cruiser"},
                {new Ship.ShipBuilder(board.generateSetOfFieldsForShip(startingField, HORIZONTAL, 4), 4).build(), "Battleship"},
               // {new Ship.ShipBuilder(board.generateSetOfFieldsForShip(startingField, HORIZONTAL, 5), 5).build(), "Aircraft carrier"},
        };
    }

    @Test(dataProvider = "horizontal")
    public void testHorizontalFieldsOccupiedByShip(Set<Field> set, boolean expected) {
        assertEquals( ship1.occupiedFields.containsAll( set ), expected );
    }

    @Test(dataProvider = "vertical")
    public void testVerticalFieldsOccupiedByShip(Set<Field> set, boolean expected) {
        assertEquals( ship2.occupiedFields.containsAll( set ), expected);
    }

    @Test(dataProvider = "equality")
    public void testHashCode(Ship ship1, Ship ship2, boolean expected) {
        assertEquals( ship1.hashCode() == ship2.hashCode(), expected );
    }

    @Test(dataProvider = "equality")
    public void testEquals(Ship ship1, Ship ship2, boolean expected) {
        assertEquals( ship1.equals( ship2 ), expected);
    }

    @Test(dataProvider = "names")
    public void testNames(Ship ship, String expected) {
        assertEquals( ship.showName(), expected );
    }
}
