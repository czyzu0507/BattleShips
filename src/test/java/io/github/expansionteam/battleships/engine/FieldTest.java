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
        return new Object[][] {
                {new Field(1,1), new Field(1,1), true},
                {new Field(2,2), new Field(2,2), true},
                {new Field(1,1), new Field(1,2), false},
                {new Field(1,1), new Field(2,2), false},
                {new Field(5,3), new Field(3,5), false}
        };
    }

    @DataProvider(name = "compareCheck")
    private Object[][] provideObjectsToCompare() {
        // helper functions to determine sign
        Function<Integer, Boolean> fZero = in -> in == 0;
        Function<Integer, Boolean> fNegative = in -> in < 0;
        Function<Integer, Boolean> fPositive = in -> in > 0;

        return new Object[][] {
                {new Field(1,1), new Field(1,1), fZero},
                {new Field(1,1), new Field(1,2), fNegative},
                {new Field(1,1), new Field(2,1), fNegative},
                {new Field(2,2), new Field(1,2), fPositive},
                {new Field(2,2), new Field(2,1), fPositive},
        };
    }

    @DataProvider(name = "adjacentSwitch")
    private Object[][] provideHorizontalSwitchedFields() {
        return new Object[][] {
                {HORIZONTAL, new Field(1,1), new Field(2,1)},
                {HORIZONTAL, new Field(2,4), new Field(3,4)},
                {VERTICAL, new Field(1,1), new Field(1,2)},
                {VERTICAL, new Field(2,3), new Field(2,4)}
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
        assertTrue( fun.apply( compareResult ) );
    }

    @Test
    public void testHitMarking() {
        // given
        Field f1 = new Field(1,1), f2 = new Field(2,2);
        // when
        f1.markAsHit();
        // then
        assertEquals(f1.isHit(), true);
        assertEquals(f2.isHit(), false);
    }

    @Test(dataProvider = "adjacentSwitch")
    public void testHorizontalSwitch(Orientation orientation, Field arg, Field expected) {
        assertEquals( arg.nextField( orientation ), expected );
    }

    @DataProvider(name = "shipPointers")
    private Object[][] provideDataToTestPointers() {
        return new Object[][] {
                {new Field(0,1), false},
                {new Field(1,1), true},
                {new Field(2,1), true},
                {new Field(3,1), true},
                {new Field(4,1), true},
                {new Field(5,1), false}
        };
    }

    // checking if adding pointers to the ship works
    @Test(dataProvider = "shipPointers")
    public void testPointers(Field field, boolean expected) {
        // given
        final Board board = new Board();
        final Field startingField = new Field(1,1);
        Set<Field> setOfFields = board.generateSetOfFieldsForShip( startingField, HORIZONTAL, 4 );
        Ship ship = new Ship.ShipBuilder(setOfFields, 4).build();
        // when
        boolean actual = board.getFieldFromTheBoard( field ).isPartOfTheShip();
        // then
        assertEquals(actual, expected);
    }
}