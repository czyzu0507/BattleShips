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

    @Test(dataProvider = "positions")
    public void testIfBoardContainsParticularField(Field field, boolean expected) {
        assertEquals( boardSet.contains( field ), expected );
    }
}
