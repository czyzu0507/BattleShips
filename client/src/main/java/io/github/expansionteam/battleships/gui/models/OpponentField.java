package io.github.expansionteam.battleships.gui.models;

import org.apache.log4j.Logger;

public class OpponentField extends Field {

    private final static Logger log = Logger.getLogger(OpponentField.class);

    private boolean wasOccupied = false;
    private boolean wasShot = false;
    private boolean isOnTarget = false;

    OpponentField(Position position) {
        super(position);
    }

    public boolean wasShot() {
        return wasShot;
    }

    public void shoot(boolean wasOccupied) {
        this.wasOccupied = wasOccupied;
        this.wasShot = true;

        updateCss();
    }

    public void startTargeting() {
        this.isOnTarget = true;
        updateCss();
    }

    public void stopTargeting() {
        this.isOnTarget = false;
        updateCss();
    }

    @Override
    protected void updateCss() {
        String cssClass;
        if (isOnTarget) {
            if (wasShot) {
                cssClass = wasOccupied ? "occupied-field-shot-hint-illegal" : "empty-field-shot-hint-illegal";
            } else {
                cssClass = "field-shot-hint-legal";
            }
        } else {
            if (wasShot) {
                cssClass = wasOccupied ? "field-was-shot-hit" : "field-was-shot-miss";
            } else {
                cssClass = "field-is-empty";
            }
        }

        getStyleClass().clear();
        getStyleClass().add(cssClass);
    }

}
