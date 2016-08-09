package io.github.expansionteam.battleships.gui.models;

public class PlayerField extends Field {

    private boolean isOccupied = false;
    private boolean isShot = false;

    PlayerField(Position position) {
        super(position);
    }

    public void occupy() {
        isOccupied = true;
        updateCss();
    }

    public void shoot() {
        isShot = true;
        updateCss();
    }

    @Override
    protected void updateCss() {
        String cssClass;
        if (isShot) {
            cssClass = isOccupied ? "" : "";
        } else {
            cssClass = isOccupied ? "field-is-occupied" : "field-is-empty";
        }

        getStyleClass().removeAll();
        getStyleClass().add(cssClass);
    }
    
}