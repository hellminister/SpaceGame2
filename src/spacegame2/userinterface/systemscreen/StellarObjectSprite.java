package spacegame2.userinterface.systemscreen;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.canvas.GraphicsContext;
import spacegame2.gamedata.drawableobject.StellarObject;

public class StellarObjectSprite extends Parent {

    private StellarObject owner;
    private Node sprite;
    private GraphicsContext gc;

    public StellarObjectSprite(Node sprite) {
        this.sprite = sprite;
        super.getChildren().add(sprite);
    }



    public void setOwner(StellarObject stellarObject) {
        owner = stellarObject;
        sprite.translateYProperty().bind(owner.posYProperty());
        sprite.translateXProperty().bind(owner.posXProperty());

    }
}
