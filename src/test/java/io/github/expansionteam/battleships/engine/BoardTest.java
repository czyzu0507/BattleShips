package io.github.expansionteam.battleships.engine;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import static io.github.expansionteam.battleships.engine.Orientation.*;
import static org.testng.Assert.*;

@Test()
public class BoardTest {
    private final Board board = new Board();
    private final Set<Field> boardSet = new TreeSet<>();

    @BeforeClass
    private void generateBoard() {
        board.forEach( boardSet::add );
    }

    @DataProvider(name = "positions")
    private Object[][] providePositions() {
        return new Object[][] {
                {new Field(-1,-1), false},
                {new Field(-1,0), false},
                {new Field(0,0), true},
                {new Field(0,1), true},
                {new Field(1,0), true},
                {new Field(9,9), true},
                {new Field(9,10), false},
                {new Field(10,10), false}
        };
    }

    @DataProvider(name = "ofPosition")
    private Object[][] provideCoordinates() {
        return new Object[][] {
                {new Field(1,1), new Field(1,1)},
                {new Field(1,2), new Field(1,2)},
                {new Field(-1,0), null},
                {new Field(-1,-2), null}
        };
    }

    @DataProvider(name = "setOfShipFields")
    private Object[][] provideDataToTestPointers() {
        Field startingField1 = new Field(-1,-1);
        Field startingField2 = new Field(0,0);
        Field startingField3 = new Field(9,9);

        return new Object[][] {
                {startingField1, HORIZONTAL, 3, null},
                {startingField1, VERTICAL, 1, null},
                {startingField2, HORIZONTAL, 2, new HashSet<>(Arrays.asList( new Field[] { new Field(0,0), new Field(1, 0) } ) )},
                {startingField2, VERTICAL, 2, new HashSet<>(Arrays.asList( new Field[] { new Field(0,0), new Field(0, 1) } ) )},
                {startingField3, HORIZONTAL, 1, new HashSet<>(Arrays.asList( new Field[] { new Field(9,9) } ))},
                {startingField3, VERTICAL, 1, new HashSet<>(Arrays.asList( new Field[] { new Field(9,9) } ))},
                {startingField3, HORIZONTAL, 2, null},
                {startingField3, VERTICAL, 2, null}
        };
    }

    @Test(dataProvider = "positions")
    public void testIfBoardContainsParticularField(Field field, boolean expected) {
        assertEquals( boardSet.contains( field ), expected );
    }

    @Test(dataProvider = "ofPosition")
    public void testGetFieldOfPosition(Field toFind, Field expected) {
        assertEquals( board.getFieldFromTheBoard( toFind ), expected );
    }

    @Test(dataProvider = "setOfShipFields")
    public void testReturnedSetsOfFields(Field startingField, Orientation orientation, int length, Set<Field> set) {
        // when
        Set<Field> actual = board.generateSetOfFieldsForShip(startingField, orientation, length);
        // then
        assertEquals(actual, set);
    }
}
