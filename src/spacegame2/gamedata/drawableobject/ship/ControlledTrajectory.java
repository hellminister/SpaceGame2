package spacegame2.gamedata.drawableobject.ship;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import spacegame2.gamedata.drawableobject.Trajectory;
import spacegame2.util.GameTimeActivatedIncrementableDoubleBinding;
import spacegame2.util.SingleValueComplexFunctionDoubleBinding;

public class ControlledTrajectory implements Trajectory {
    private DoubleProperty posXProperty;
    private DoubleBinding posX;
    private DoubleProperty posYProperty;
    private DoubleBinding posY;

    private DoubleBinding speedX;
    private DoubleBinding speedY;

    private boolean accelerating;

    private final DoubleProperty angle;     // the angle in radians
    private final DoubleBinding angleCos;
    private final DoubleBinding angleSin;

    private DoubleProperty accThruster;
    private DoubleProperty turnSpeed;

    public ControlledTrajectory() {
        accelerating = false;

        angle = new SimpleDoubleProperty();

        angleCos = new SingleValueComplexFunctionDoubleBinding(angle, () -> Math.cos(angle.get()));

        angleSin = new SingleValueComplexFunctionDoubleBinding(angle, () -> Math.sin(angle.get()));

        speedX = new GameTimeActivatedIncrementableDoubleBinding(0.0, () ->  accelerating ? (accThruster.get() * angleCos.get() * -1) : 0);
        speedY = new GameTimeActivatedIncrementableDoubleBinding(0.0, () ->  accelerating ? (accThruster.get() * angleSin.get() * -1) : 0);

        posX = new GameTimeActivatedIncrementableDoubleBinding(0.0, speedX::get);

        posY = new GameTimeActivatedIncrementableDoubleBinding(0.0, speedY::get);

        posXProperty = new SimpleDoubleProperty();
        posXProperty.bind(posX);
        posYProperty = new SimpleDoubleProperty();
        posYProperty.bind(posY);
    }

    @Override
    public ReadOnlyDoubleProperty posXProperty() {
        return posXProperty;
    }

    @Override
    public ReadOnlyDoubleProperty posYProperty() {
        return posYProperty;
    }

    public void setPosition(double x, double y){
        ((GameTimeActivatedIncrementableDoubleBinding)posX).set(x);
        ((GameTimeActivatedIncrementableDoubleBinding)posY).set(y);
    }

    public void turnRight(){
        angle.add(turnSpeed);
    }

    public void turnLeft(){
        angle.subtract(turnSpeed);
    }

    public void accelerate(boolean acc){
        accelerating = acc;
    }
}
