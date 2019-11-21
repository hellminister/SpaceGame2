package spacegame2.gamedata.drawableobject;

import javafx.beans.property.*;
import spacegame2.userinterface.systemscreen.StellarObjectSprite;

public class StellarObject {
    protected final Trajectory position;

    protected final BooleanProperty canCollide;
    protected StellarObjectSprite sprite;

    protected String name;

    public StellarObject(Trajectory trajectory, boolean canCollide, String name, StellarObjectSprite sprite) {
        this.name = name;

        this.sprite = sprite;
        this.position = trajectory;

        this.canCollide = new SimpleBooleanProperty(canCollide);
    }

    public ReadOnlyDoubleProperty posXProperty() {
        return position.posXProperty();
    }

    public ReadOnlyDoubleProperty posYProperty() {
        return position.posYProperty();
    }
}
