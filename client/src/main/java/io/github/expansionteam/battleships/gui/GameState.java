package io.github.expansionteam.battleships.gui;

public class GameState {

    private Turn currentTurn;

    public synchronized Turn getCurrentTurn() {
        return currentTurn;
    }

    public synchronized void setCurrentTurn(Turn currentTurn) {
        this.currentTurn = currentTurn;
    }

    public enum Turn {

        PLAYER_TURN, OPPONENT_TURN;

    }

}
