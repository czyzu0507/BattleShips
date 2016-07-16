package io.github.expansionteam.battleships.engine;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Set;
import java.util.TreeSet;

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

    @Test(dataProvider = "positions")
    public void testIfBoardContainsParticularField(Field field, boolean expected) {
        assertEquals( boardSet.contains( field ), expected );
    }

    @Test(dataProvider = "ofPosition")
    public void testGetFieldOfPosition(Field toFind, Field expected) {
        assertEquals( board.getFieldFromTheBoard( toFind ), expected );
    }
}
