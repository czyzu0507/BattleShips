package io.github.expansionteam.battleships.engine;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Set;
import java.util.function.Function;

import static io.github.expansionteam.battleships.engine.Orientation.HORIZONTAL;
import static io.github.expansionteam.battleships.engine.Orientation.VERTICAL;
import static org.testng.Assert.*;

@Test()
public class FieldTest {
    @DataProvider(name = "equalityCheck")
    private Object[][] provideEqualObjects() {
        return new Object[][]{
                {new Field(1, 1), new Field(1, 1), true},
                {new Field(2, 2), new Field(2, 2), true},
                {new Field(1, 1), new Field(1, 2), false},
                {new Field(1, 1), new Field(2, 2), false},
                {new Field(5, 3), new Field(3, 5), false}
        };
    }

    @DataProvider(name = "compareCheck")
    private Object[][] provideObjectsToCompare() {
        // helper functions to determine sign
        Function<Integer, Boolean> fZero = in -> in == 0;
        Function<Integer, Boolean> fNegative = in -> in < 0;
        Function<Integer, Boolean> fPositive = in -> in > 0;

        return new Object[][]{
                {new Field(1, 1), new Field(1, 1), fZero},
                {new Field(1, 1), new Field(1, 2), fNegative},
                {new Field(1, 1), new Field(2, 1), fNegative},
                {new Field(2, 2), new Field(1, 2), fPositive},
                {new Field(2, 2), new Field(2, 1), fPositive},
        };
    }

    @DataProvider(name = "adjacentSwitch")
    private Object[][] provideHorizontalSwitchedFields() {
        return new Object[][]{
                {HORIZONTAL, new Field(1, 1), new Field(2, 1)},
                {HORIZONTAL, new Field(2, 4), new Field(3, 4)},
                {VERTICAL, new Field(1, 1), new Field(1, 2)},
                {VERTICAL, new Field(2, 3), new Field(2, 4)}
        };
    }

    @DataProvider(name = "shipPointers")
    private Object[][] provideDataToTestPointers() {
        return new Object[][]{
                {new Field(0, 1), false},
                {new Field(1, 1), true},
                {new Field(2, 1), true},
                {new Field(3, 1), true},
                {new Field(4, 1), true},
                {new Field(5, 1), false}
        };
    }

    @DataProvider(name = "shipPointerToParticularShip")
    private Object[][] provideDataToTestParticularShipPointers() {
        Board b = new Board();
        Ship s1 = new Ship.ShipBuilder(b.generateSetOfFieldsForShip(new Field(1, 1), HORIZONTAL, 4), 4).build();
        Ship s2 = new Ship.ShipBuilder(b.generateSetOfFieldsForShip(new Field(6, 1), HORIZONTAL, 4), 4).build();

        Field f1 = b.getFieldFromTheBoard(new Field(1, 1));
        Field f2 = b.getFieldFromTheBoard(new Field(3, 1));
        Field f3 = b.getFieldFromTheBoard(new Field(5, 5));
        Field f4 = b.getFieldFromTheBoard(new Field(7, 1));

        return new Object[][]{
                {f1, s1, true},
                {f2, s1, true},
                {f3, s1, false},
                {f4, s1, false},
                {f1, s2, false},
                {f2, s2, false},
                {f3, s2, false},
                {f4, s2, true}
        };
    }

    @DataProvider(name = "adjacentFields")
    private Object[][] provideSomeAdjacentFieldsWithResults() {
        Field field = new Field(1, 1);
        return new Object[][]{
                {field, field, false},
                {field, new Field(0, 0), true},
                {field, new Field(0, 1), true},
                {field, new Field(0, 2), true},
                {field, new Field(1, 0), true},
                {field, new Field(1, 2), true},
                {field, new Field(2, 0), true},
                {field, new Field(2, 1), true},
                {field, new Field(2, 2), true},
                {field, new Field(2, 3), false},
                {field, new Field(3, 2), false}
        };
    }

    @Test(dataProvider = "equalityCheck")
    public void checkHashCodeEquality(final Field f1, final Field f2, final boolean expected) {
        assertEquals(f1.hashCode() == f2.hashCode(), expected);
    }

    @Test(dataProvider = "equalityCheck")
    public void checkEquals(final Field f1, final Field f2, final boolean expected) {
        assertEquals(f1.equals(f2), expected);
    }

    @Test(dataProvider = "compareCheck")
    public void checkCompare(final Field f1, final Field f2, final Function<Integer, Boolean> fun) {
        int compareResult = f1.compareTo(f2);
        assertTrue(fun.apply(compareResult));
    }

    @Test
    public void testHitMarking() {
        // given
        Field f1 = new Field(1, 1), f2 = new Field(2, 2);
        // when
        f1.markAsHit();
        // then
        assertEquals(f1.isHit(), true);
        assertEquals(f2.isHit(), false);
    }

    @Test(dataProvider = "adjacentSwitch")
    public void testHorizontalSwitch(Orientation orientation, Field arg, Field expected) {
        assertEquals(arg.nextField(orientation), expected);
    }

    // checking if adding pointers to the ship works
    @Test(dataProvider = "shipPointers")
    public void testPointers(Field field, boolean expected) {
        // given
        final Board board = new Board();
        final Field startingField = new Field(1, 1);
        Set<Field> setOfFields = board.generateSetOfFieldsForShip(startingField, HORIZONTAL, 4);
        Ship ship = new Ship.ShipBuilder(setOfFields, 4).build();
        // when
        boolean actual = board.getFieldFromTheBoard(field).isPartOfTheShip();
        // then
        assertEquals(actual, expected);
    }

    // checking pointer for particular ship
    @Test(dataProvider = "shipPointerToParticularShip")
    public void testPointersToParticularShips(Field field, Ship ship, boolean expected) {
        // when
        boolean actual = field.isPartOfTheShip(ship);
        // then
        assertEquals(actual, expected);
    }

    // first attempt of adjacent fields
    @Test(dataProvider = "adjacentFields")
    public void testSimpleAdjacentFields(Field center, Field adjacent, boolean expected) {
        // when
        Set<Field> adjacents = center.createAllPossibleAdjacentFields();
        // then
        assertEquals(adjacents.contains(adjacent), expected);
    }
}