package spacegame2.gamedata.drawableobject.ship;

public enum TurnDirection {
    LEFT(-1),
    RIGHT(1),
    STOP(0),
    ;

    private int value;

    TurnDirection(int i) {
        value = i;
    }

    public int getSign() {
        return value;
    }
}
