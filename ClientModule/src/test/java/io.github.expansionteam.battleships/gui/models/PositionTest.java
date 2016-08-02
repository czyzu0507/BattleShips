package io.github.expansionteam.battleships.gui.models;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class PositionTest {

    @DataProvider(name = "dataForNextHorizontalPositionTesting")
    private Object[][] getDataForNextHorizontalPosition() {
        return new Object[][]{
                {Position.of(0, 0), Position.of(1, 0)},
                {Position.of(8, 9), Position.of(9, 9)}
        };
    }

    @Test(dataProvider = "dataForNextHorizontalPositionTesting")
    public void testNextPositionHorizontally(Position position, Position nextHorizontalPosition) throws Exception {
        //Assert
        assertEquals(position.nextPositionHorizontally(), nextHorizontalPosition);
    }

    @DataProvider(name = "dataForNextVerticalPositionTesting")
    private Object[][] getDataForNextVerticalPosition() {
        return new Object[][]{
                {Position.of(0, 0), Position.of(0, 1)},
                {Position.of(9, 8), Position.of(9, 9)}
        };
    }

    @Test(dataProvider = "dataForNextVerticalPositionTesting")
    public void testNextPositionVertically(Position position, Position nextVerticalPosition) throws Exception {
        //Assert
        assertEquals(position.nextPositionVertically(), nextVerticalPosition);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIllegalNextHorizontalPosition() {
        //When
        Position position = Position.of(9, 9);
        //Then
        position.nextPositionHorizontally();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIllegalNextVerticalPosition() {
        //When
        Position position = Position.of(9, 9);
        //Then
        position.nextPositionVertically();
    }

    @DataProvider(name = "dataForTestEquality")
    private Object[][] getDataForEqualityTest() {
        Position temp = Position.of(3, 5);
        return new Object[][]{{Position.of(0, 0), Position.of(0, 0), true},
                {Position.of(1, 2), Position.of(2, 1), false},
                {Position.of(1, 2), null, false},
                {Position.of(9, 8), Position.of(9, 8), true},
                {temp, temp, true}
        };
    }

    @Test(dataProvider = "dataForTestEquality")
    public void testEquals(Position position1, Position position2, boolean condition) throws Exception {
        //Assert
        assertEquals(position1.equals(position2), condition);
    }

    @DataProvider(name = "dataForTestHashCode")
    private Object[][] getDataForHashCodeTest() {
        Position temp = Position.of(3, 5);
        return new Object[][]{{Position.of(0, 0), Position.of(0, 0), true},
                {Position.of(1, 2), Position.of(2, 1), false},
                {temp, temp, true}
        };
    }

    @Test(dataProvider = "dataForTestHashCode")
    public void testHashCode(Position position1, Position position2, boolean condition) throws Exception {
        //Assert
        assertEquals(position1.hashCode() == position2.hashCode(), condition);
    }

    @DataProvider(name = "dataForToString")
    private Object[][] getDataForToStringTest() {
        return new Object[][]{{Position.of(0, 0), "[0, 0]"},
                {Position.of(5, 2), "[5, 2]"},
                {Position.of(9, 9), "[9, 9]"},
        };
    }

    @Test(dataProvider = "dataForToString")
    public void testToString(Position position, String stringRepresentation) throws Exception {
        //Assert
        assertEquals(position.toString(), stringRepresentation);
    }

    @DataProvider(name = "illegalArgumentsData")
    private Object[][] getIllegalArguments() {
        return new Object[][]{{-1, 5}, {2, -2}, {10, 10}, {-1, -1}, {13, 10}, {10, 5}};
    }

    @Test(dataProvider = "illegalArgumentsData", expectedExceptions = IllegalArgumentException.class)
    public void testIllegalArguments(int x, int y) {
        Position.of(x, y);
    }
}