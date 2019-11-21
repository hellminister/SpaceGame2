package spacegame2.userinterface.systemscreen;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.canvas.GraphicsContext;
import spacegame2.gamedata.drawableobject.StellarObject;

public class StellarObjectSprite extends Parent {

    private StellarObject owner;
    private Node sprite;
    private GraphicsContext gc;




    public ReadOnlyDoubleProperty posXProperty() {
        return owner.posXProperty();
    }

    public ReadOnlyDoubleProperty posYProperty() {
        return owner.posYProperty();
    }
}
