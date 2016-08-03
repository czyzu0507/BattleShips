package io.github.expansionteam.battleships.engine;

public enum Orientation {

    HORIZONTAL {
        @Override
        public String toString(){
            return "HORIZONTAL";
        }
    },

    VERTICAL {
        @Override
        public String toString(){
            return "VERTICAL";
        }
    };

}
