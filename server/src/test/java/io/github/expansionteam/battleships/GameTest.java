package io.github.expansionteam.battleships;

import io.github.expansionteam.battleships.engine.Board;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;

public class GameTest {
    private String currentThreadName;

    @BeforeMethod
    public void saveCurrentThreadName() {
        currentThreadName = Thread.currentThread().getName();
    }

    @AfterMethod
    public void restoreCurrentThreadName() {
        Thread.currentThread().setName(currentThreadName);
    }

    @DataProvider
    private Object[][] playerBoard() {
        return new Object[][]{
                {"Player_1", 1, 0},
                {"Player_2", 0, 1}
        };
    }

    @Test(dataProvider = "playerBoard")
    public void getsPlayerShips(String player, int player1boardCalls, int player2boardCalls) {
        // given
        Thread.currentThread().setName(player);
        Board player1BoardMock = mock(Board.class);
        Board player2BoardMock = mock(Board.class);
        Game testedGame = new Game(player1BoardMock, player2BoardMock);

        // when
        testedGame.getPlayerShips();

        // then
        verify(player1BoardMock, times(player1boardCalls)).getShips();
        verify(player2BoardMock, times(player2boardCalls)).getShips();
    }

    @Test(dataProvider = "playerBoard")
    public void shootsOpponentField(String player, int player1boardCalls, int player2boardCalls) {
        // given
        Thread.currentThread().setName(player);
        Board player1BoardMock = mock(Board.class);
        Board player2BoardMock = mock(Board.class);
        Game testedGame = new Game(player1BoardMock, player2BoardMock);
        int x = 0;
        int y = 0;

        // when
        testedGame.shootOpponentField(x, y);

        // then
        verify(player1BoardMock, times(player2boardCalls)).shootField(x, y);
        verify(player2BoardMock, times(player1boardCalls)).shootField(x, y);
    }

    @Test(dataProvider = "playerBoard")
    public void isOpponentShipHit(String player, int player1boardCalls, int player2boardCalls) {
        // given
        Thread.currentThread().setName(player);
        Board player1BoardMock = mock(Board.class);
        Board player2BoardMock = mock(Board.class);

        Game testedGame = new Game(player1BoardMock, player2BoardMock);
        int x = 0;
        int y = 0;

        // when
        testedGame.isOpponentShipHit(x, y);

        // then
        verify(player1BoardMock, times(player2boardCalls)).isShipField(x, y);
        verify(player2BoardMock, times(player1boardCalls)).isShipField(x, y);
    }

    @Test(dataProvider = "playerBoard")
    public void isOpponentShipDestroyed(String player, int player1boardCalls, int player2boardCalls) {
        // given
        Thread.currentThread().setName(player);
        Board player1BoardMock = mock(Board.class);
        Board player2BoardMock = mock(Board.class);
        Game testedGame = new Game(player1BoardMock, player2BoardMock);
        int x = 0;
        int y = 0;

        // when
        testedGame.isOpponentShipDestroyed(x, y);

        // then
        verify(player1BoardMock, times(player2boardCalls)).isDestroyedShip(x, y);
        verify(player2BoardMock, times(player1boardCalls)).isDestroyedShip(x, y);
    }

    @Test(dataProvider = "playerBoard")
    public void getsAdjacentToOpponentShip(String player, int player1boardCalls, int player2boardCalls) {
        // given
        Thread.currentThread().setName(player);
        Board player1BoardMock = mock(Board.class);
        Board player2BoardMock = mock(Board.class);
        Game testedGame = new Game(player1BoardMock, player2BoardMock);
        int x = 0;
        int y = 0;

        // when
        testedGame.getAdjacentToOpponentShip(x, y);

        // then
        verify(player1BoardMock, times(player2boardCalls)).getAdjacentToShip(x, y);
        verify(player2BoardMock, times(player1boardCalls)).getAdjacentToShip(x, y);
    }

    @Test(dataProvider = "playerBoard")
    public void isEnded(String player, int player1boardCalls, int player2boardCalls) {
        // given
        Thread.currentThread().setName(player);
        Board player1BoardMock = mock(Board.class);
        Board player2BoardMock = mock(Board.class);
        Game testedGame = new Game(player1BoardMock, player2BoardMock);

        // when
        testedGame.isEnded();

        // then
        verify(player1BoardMock, times(player2boardCalls)).areAllShipsDestroyed();
        verify(player2BoardMock, times(player1boardCalls)).areAllShipsDestroyed();
    }
}