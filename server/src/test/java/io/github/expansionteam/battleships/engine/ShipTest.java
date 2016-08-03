package io.github.expansionteam.battleships.engine;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import static org.testng.Assert.assertEquals;

@Test()
public class ShipTest {
    private Set<Field> firstShipFields;
    private Set<Field> secondShipFields;
    private Ship ship1;
    private Ship ship2;

    Field startFieldMock = null;
    Orientation orientationMock = null;
    int sizeMock = 0;

    @BeforeClass
    private void generateShips() {

        firstShipFields = new TreeSet<>();
        secondShipFields = new TreeSet<>();

        firstShipFields.add(new Field(1, 1));
        firstShipFields.add(new Field(1, 2));
        firstShipFields.add(new Field(1, 3));
        firstShipFields.add(new Field(1, 4));

        secondShipFields.add(new Field(1, 1));
        secondShipFields.add(new Field(2, 1));
        secondShipFields.add(new Field(3, 1));
        secondShipFields.add(new Field(4, 1));

        ship1 = new Ship.ShipBuilder(firstShipFields, startFieldMock, orientationMock, sizeMock).build();
        ship2 = new Ship.ShipBuilder(secondShipFields, startFieldMock, orientationMock, sizeMock).build();
    }

    @DataProvider(name = "horizontal")
    private Object[][] provideSetOfPositionsAndResultsHorizontal() {
        return new Object[][]{
                {new TreeSet<>(Arrays.asList(new Field(1, 1), new Field(1, 2), new Field(1, 3), new Field(1, 4))), true},
                {new TreeSet<>(Arrays.asList(new Field(1, 1), new Field(2, 1), new Field(3, 1), new Field(4, 1), new Field(5, 1))), false},
                {new TreeSet<>(Arrays.asList(new Field(0, 1), new Field(1, 1), new Field(2, 1), new Field(3, 1), new Field(4, 1))), false}
        };
    }

    @DataProvider(name = "vertical")
    private Object[][] provideSetOfPositionsAndResultsVertical() {
        return new Object[][]{
                {new TreeSet<>(Arrays.asList(new Field(1, 1), new Field(2, 1), new Field(3, 1), new Field(4, 1))), true},
                {new TreeSet<>(Arrays.asList(new Field(1, 1), new Field(1, 2), new Field(1, 3), new Field(1, 4), new Field(1, 5))), false},
                {new TreeSet<>(Arrays.asList(new Field(1, 0), new Field(1, 1), new Field(1, 2), new Field(1, 3), new Field(1, 4))), false}
        };
    }

    @DataProvider(name = "equality")
    private Object[][] provideSomeObjectsForEqualityTest() {


        return new Object[][]{
                {ship1, ship2, false},
                {ship1, new Ship.ShipBuilder(firstShipFields, startFieldMock, orientationMock, sizeMock).build(), true},
                {ship2, new Ship.ShipBuilder(secondShipFields, startFieldMock, orientationMock, sizeMock).build(), true},
                {ship1, new Ship.ShipBuilder(secondShipFields, startFieldMock, orientationMock, sizeMock).build(), false},
                {ship2, new Ship.ShipBuilder(firstShipFields, startFieldMock, orientationMock, sizeMock).build(), false}
        };
    }

    @DataProvider(name = "names")
    private Object[][] provideObjectsToCheckNames() {
        return new Object[][]{
                {new Ship.ShipBuilder(new HashSet<>(Arrays.asList(new Field(1, 1))), startFieldMock, orientationMock, sizeMock).build(), "Submarine"},
                {new Ship.ShipBuilder(new HashSet<>(Arrays.asList(new Field(1, 1), new Field(1, 2))), startFieldMock, orientationMock, sizeMock).build(), "Destroyer"},
                {new Ship.ShipBuilder(new HashSet<>(Arrays.asList(new Field(1, 1), new Field(1, 2), new Field(1, 3))), startFieldMock, orientationMock, sizeMock).build(), "Cruiser"},
                {new Ship.ShipBuilder(new HashSet<>(Arrays.asList(new Field(1, 1), new Field(1, 2), new Field(1, 3), new Field(1, 4))), startFieldMock, orientationMock, sizeMock).build(), "Battleship"},
                {new Ship.ShipBuilder(new HashSet<>(Arrays.asList(new Field(1, 1), new Field(1, 2), new Field(1, 3), new Field(1, 4), new Field(1, 5))), startFieldMock, orientationMock, sizeMock).build(), "Aircraft carrier"},
        };
    }

    @SuppressWarnings("unchecked")
    @Test(dataProvider = "horizontal")
    public void testHorizontalFieldsOccupiedByShip(Set<Field> set, boolean expected) throws NoSuchFieldException, IllegalAccessException {
        java.lang.reflect.Field occupiedFields = ship1.getClass().getDeclaredField("occupiedFields");
        occupiedFields.setAccessible(true);
        Set<Field> setOfFields = (Set<Field>) occupiedFields.get(ship1);
        assertEquals(setOfFields.containsAll(set), expected);
    }

    @SuppressWarnings("unchecked")
    @Test(dataProvider = "vertical")
    public void testVerticalFieldsOccupiedByShip(Set<Field> set, boolean expected) throws NoSuchFieldException, IllegalAccessException {
        java.lang.reflect.Field occupiedFields = ship2.getClass().getDeclaredField("occupiedFields");
        occupiedFields.setAccessible(true);
        Set<Field> setOfFields = (Set<Field>) occupiedFields.get(ship2);
        assertEquals(setOfFields.containsAll(set), expected);
    }

    @Test(dataProvider = "equality")
    public void testHashCode(Ship ship1, Ship ship2, boolean expected) {
        assertEquals(ship1.hashCode() == ship2.hashCode(), expected);
    }

    @Test(dataProvider = "equality")
    public void testEquals(Ship ship1, Ship ship2, boolean expected) {
        assertEquals(ship1.equals(ship2), expected);
    }

    @Test(dataProvider = "names")
    public void testNames(Ship ship, String expected) {
        assertEquals(ship.toString(), expected);
    }
}