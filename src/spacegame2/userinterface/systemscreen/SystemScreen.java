package spacegame2.userinterface.systemscreen;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.DoublePropertyBase;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import spacegame2.SpaceGame;

public class SystemScreen extends Scene {

    private static final double DIMENSION = 100000000;
    private static final double FIFTY_PERCENT = 0.5d;



    private final StackPane root;
    private final MovingBackground background;

    private final ScrollPane viewport;
    private final StackPane fullSystemArea;

    private final SpaceGame spacegame;


    private SceneAnimator sceneAnimation;

    public SystemScreen(SpaceGame spaceGame){
        super(new StackPane());
        this.spacegame = spaceGame;
        root = (StackPane) getRoot();
        sceneAnimation = new SceneAnimator();
        background = new MovingBackground();

        fullSystemArea = new StackPane();

        fullSystemArea.getChildren().add(background);
        StackPane.setAlignment(background, Pos.CENTER);

        Rectangle sizing = new Rectangle(DIMENSION, DIMENSION);
        sizing.setFill(Color.TRANSPARENT);

        fullSystemArea.maxHeightProperty().bind(sizing.heightProperty());
        fullSystemArea.maxWidthProperty().bind(sizing.widthProperty());

        viewport = new ScrollPane(fullSystemArea) {
            @Override
            public void requestFocus() {
                /* so theres no focus */
            }
        };

        viewport.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        viewport.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        fullSystemArea.getChildren().add(sizing);

        root.getChildren().add(viewport);

        viewport.prefViewportHeightProperty().bind(root.heightProperty());
        viewport.prefViewportWidthProperty().bind(root.widthProperty());

        Shape circle = new Circle(0, 0, 5, Paint.valueOf("yellow"));
        fullSystemArea.getChildren().add(circle);

        finishBindings(circle);


    }

    /**
     * This method binds the scroll pane so that the players main ship is always centered
     * @param circle
     */
    public void finishBindings(Shape circle) {
        DoublePropertyBase halfY = new SimpleDoubleProperty();

        halfY.bind(fullSystemArea.heightProperty().subtract(viewport.heightProperty()));

        DoublePropertyBase halfX = new SimpleDoubleProperty();

        DoubleProperty x = new SimpleDoubleProperty(0);
        DoubleProperty y = new SimpleDoubleProperty(0);

        halfX.bind(fullSystemArea.widthProperty().subtract(viewport.widthProperty()));

        viewport.vvalueProperty().bind(circle.translateYProperty().divide(halfY).add(FIFTY_PERCENT));
        viewport.hvalueProperty().bind(circle.translateYProperty().divide(halfX).add(FIFTY_PERCENT));

        background.bindedTo(x, y);

    }


}
