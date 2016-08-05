package io.github.expansionteam.battleships.engine;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.*;

import static io.github.expansionteam.battleships.engine.Orientation.HORIZONTAL;
import static io.github.expansionteam.battleships.engine.Orientation.VERTICAL;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Test()
public class BoardTest {
    private final Board board = new Board.BoardBuilder().build();
    private final Board boardForSeqTest = new Board.BoardBuilder().build();
    private final Set<Field> boardSet = new TreeSet<>();

    @BeforeClass
    private void generateBoard() {
        board.forEach(boardSet::add);
    }

    @DataProvider(name = "positions")
    private Object[][] providePositions() {
        return new Object[][]{
                {new Field(-1, -1), false},
                {new Field(-1, 0), false},
                {new Field(0, 0), true},
                {new Field(0, 1), true},
                {new Field(1, 0), true},
                {new Field(9, 9), true},
                {new Field(9, 10), false},
                {new Field(10, 10), false}
        };
    }

    @DataProvider(name = "ofPosition")
    private Object[][] provideCoordinates() {
        return new Object[][]{
                {new Field(1, 1), new Field(1, 1)},
                {new Field(1, 2), new Field(1, 2)},
                {new Field(-1, 0), null},
                {new Field(-1, -2), null}
        };
    }

    @DataProvider(name = "setOfShipFields")
    private Object[][] provideDataToTestPointers() {
        Field startingField1 = new Field(-1, -1);
        Field startingField2 = new Field(0, 0);
        Field startingField3 = new Field(9, 9);

        return new Object[][]{
                {startingField1, HORIZONTAL, 3, null},
                {startingField1, VERTICAL, 1, null},
                {startingField2, HORIZONTAL, 2, new HashSet<>(Arrays.asList(new Field[]{new Field(0, 0), new Field(1, 0)}))},
                {startingField2, VERTICAL, 2, new HashSet<>(Arrays.asList(new Field[]{new Field(0, 0), new Field(0, 1)}))},
                {startingField3, HORIZONTAL, 1, new HashSet<>(Arrays.asList(new Field[]{new Field(9, 9)}))},
                {startingField3, VERTICAL, 1, new HashSet<>(Arrays.asList(new Field[]{new Field(9, 9)}))},
                {startingField3, HORIZONTAL, 2, null},
                {startingField3, VERTICAL, 2, null}
        };
    }

    @DataProvider(name = "appendShips")
    private Object[][] provideAppendShipsData() {
        return new Object[][]{
                {new Field(1, 1), HORIZONTAL, 4, new HashSet<>(Arrays.asList(new Field[]{new Field(1, 1), new Field(2, 1), new Field(3, 1), new Field(4, 1)}))},
                {new Field(1, 0), VERTICAL, 2, null},
                {new Field(7, 0), HORIZONTAL, 4, null},
                {new Field(7, 0), VERTICAL, 2, new HashSet<>(Arrays.asList(new Field[]{new Field(7, 0), new Field(7, 1)}))},
                {new Field(7, 1), VERTICAL, 4, null},
                {new Field(3, 6), VERTICAL, 3, new HashSet<>(Arrays.asList(new Field[]{new Field(3, 6), new Field(3, 7), new Field(3, 8)}))}
        };
    }

    @DataProvider(name = "appendShipsAdjacent")
    private Object[][] provideAppendShipsWithoutAdjacentData() {
        Board b = new Board.BoardBuilder().build();
        return new Object[][]{
                {b.appendShip(new Field(1, 1), HORIZONTAL, 4), true},
                {b.appendShip(new Field(5, 0), VERTICAL, 3), false},
                {b.appendShip(new Field(3, 6), VERTICAL, 3), true},
                {b.appendShip(new Field(2, 0), VERTICAL, 2), false},
                {b.appendShip(new Field(7, 7), VERTICAL, 1), true}
        };
    }

    @DataProvider(name = "appendAvailableShips")
    private Object[][] provideDataToTestAvailableShips() {
        Board board = new Board.BoardBuilder().build();
        return new Object[][]{
                {board.appendShip(new Field(0, 0), HORIZONTAL, 4), true},
                // {board.appendShip(new Field(0, 2), HORIZONTAL, 4), true},
                {board.appendShip(new Field(0, 4), HORIZONTAL, 4), false},

                {board.appendShip(new Field(0, 4), HORIZONTAL, 3), true},
                {board.appendShip(new Field(0, 6), HORIZONTAL, 3), true},
                // {board.appendShip(new Field(0, 8), HORIZONTAL, 3), true},
                {board.appendShip(new Field(4, 0), HORIZONTAL, 3), false},
        };
    }

    @DataProvider(name = "allPositions")
    private Object[][] provideAllPositions() {
        Object[][] objects = new Object[9][1];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                objects[i * 3 + j][0] = new Field(i, j);
            }
        }
        return objects;
    }

    @DataProvider(name = "markHitAdjacent")
    private Object[][] provideScenariosForMarkingAdjacentFields() {
        return new Object[][]{
                {new Field(0, 0), Orientation.HORIZONTAL, 4},
                {new Field(0, 0), Orientation.HORIZONTAL, 1},
                {new Field(2, 2), Orientation.VERTICAL, 4},
                {new Field(0, 4), Orientation.HORIZONTAL, 3},
                {new Field(4, 0), Orientation.HORIZONTAL, 2},
                {new Field(3, 9), Orientation.HORIZONTAL, 1},
                {new Field(9, 2), Orientation.VERTICAL, 1},
                {new Field(9, 9), Orientation.HORIZONTAL, 1},
        };
    }

    @Test(dataProvider = "positions")
    public void testIfBoardContainsParticularField(Field field, boolean expected) {
        assertEquals(boardSet.contains(field), expected);
    }

    @Test(dataProvider = "ofPosition")
    public void testGetFieldOfPosition(Field toFind, Field expected) {
        assertEquals(board.getFieldFromTheBoard(toFind.getX(), toFind.getY()), expected);
    }

    @Test(dataProvider = "setOfShipFields")
    public void testReturnedSetsOfFields(Field startingField, Orientation orientation, int length, Set<Field> set) {
        // when
        Set<Field> actual = new Board.ShipAdderHelper().generateSetOfFieldsForShip(board, startingField, orientation, length);
        // then
        assertEquals(actual, set);
    }

    @Test(dataProvider = "appendShipsAdjacent")
    public void testAdjacentAppendingShips(boolean actual, boolean expected) {
        // then
        assertEquals(actual, expected);
    }

    @Test(dataProvider = "appendAvailableShips")
    public void testIfShipIsAvailable(boolean addsShip, boolean expected) {
        // then
        assertEquals(addsShip, expected);
    }

    @Test(dataProvider = "allPositions")
    public void shootsNotHitField(Field field) {
        // given
        Board notHitBoard = new Board.BoardBuilder().build();

        // when
        notHitBoard.shootField(field.getX(), field.getY());
        Field shotField = notHitBoard.getFieldFromTheBoard(field.getX(), field.getY());

        // then
        assertTrue(shotField.isHit());
    }

    @Test(dataProvider = "markHitAdjacent")
    public void marksAdjacentFieldsIfShipsDestroyed(Field field, Orientation orientation, int length) {
        // given
        Board board = new Board.BoardBuilder().build();
        board.appendShip(field, orientation, length);
        Set<Field> occupiedFields = board.getFieldFromTheBoard(field.getX(), field.getY()).getParentShip().occupiedFields;
        Set<Field> adjacentFields = Ship.generateSetOfAdjacentFields(board, occupiedFields);

        // when
        occupiedFields.forEach(e -> board.shootField(e.getX(), e.getY()));

        // then
        for (Field adjacentField : adjacentFields) {
            assertEquals(adjacentField.isHit(), true);
        }
    }

    @Test
    public void isDestroyed() {
        // given
        Board board = new Board.BoardBuilder().build();
        board.appendShip(new Field(3, 5), HORIZONTAL, 1);
        board.shootField(3, 5);

        // when
        boolean destroyed = board.isDestroyedShip(3, 5);

        // then
        assertEquals(destroyed, true);
    }

    @Test
    public void isNotDestroyed() {
        // given
        Board board = new Board.BoardBuilder().build();
        board.appendShip(new Field(3, 5), HORIZONTAL, 3);
        board.shootField(3, 5);

        // when
        boolean destroyed = board.isDestroyedShip(3, 5);

        // then
        assertEquals(destroyed, false);
    }

    @Test
    public void isNotAShip() {
        // given
        Board board = new Board.BoardBuilder().build();

        // when
        boolean destroyed = board.isDestroyedShip(3, 5);

        // then
        assertEquals(destroyed, false);
    }

    @Test
    public void getsEmptyToNotAShip() {
        // given
        Board board = new Board.BoardBuilder().build();

        // when
        Collection<Field> actualAdjacentFields = board.getAdjacentToShip(2, 5);

        // then
        assertEquals(actualAdjacentFields.size(), 0);
    }

    @Test
    public void getsAdjacentToShipInCenter() {
        // given
        Board board = new Board.BoardBuilder().build();
        board.appendShip(new Field(2, 5), HORIZONTAL, 1);

        Collection<Field> expectedAdjacent = new HashSet<>();
        expectedAdjacent.add(new Field(1, 4));
        expectedAdjacent.add(new Field(2, 4));
        expectedAdjacent.add(new Field(3, 4));
        expectedAdjacent.add(new Field(1, 5));
        expectedAdjacent.add(new Field(3, 5));
        expectedAdjacent.add(new Field(1, 6));
        expectedAdjacent.add(new Field(2, 6));
        expectedAdjacent.add(new Field(3, 6));

        // when
        Collection<Field> actualAdjacentFields = board.getAdjacentToShip(2, 5);

        // then
        assertEquals(actualAdjacentFields, expectedAdjacent);
    }

    @Test
    public void getsAdjacentToShipInCorner() {
        // given
        Board board = new Board.BoardBuilder().build();
        board.appendShip(new Field(0, 0), HORIZONTAL, 1);

        Collection<Field> expectedAdjacent = new HashSet<>();
        expectedAdjacent.add(new Field(0, 1));
        expectedAdjacent.add(new Field(1, 0));
        expectedAdjacent.add(new Field(1, 1));

        // when
        Collection<Field> actualAdjacentFields = board.getAdjacentToShip(0, 0);

        // then
        assertEquals(actualAdjacentFields, expectedAdjacent);
    }
}
