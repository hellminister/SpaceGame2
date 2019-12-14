package spacegame2.gamedata.drawableobject.ship;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableDoubleValue;
import spacegame2.gamedata.drawableobject.Trajectory;
import spacegame2.util.GameTimeActivatedIncrementableDoubleBinding;
import spacegame2.util.SingleValueComplexFunctionDoubleBinding;

import static spacegame2.gamedata.drawableobject.ship.TurnDirection.STOP;

public class ControlledTrajectory implements Trajectory {
    private DoubleProperty posXProperty;
    private DoubleBinding posX;
    private DoubleProperty posYProperty;
    private DoubleBinding posY;

    private DoubleBinding speedX;
    private DoubleBinding speedY;

    private DoubleBinding turnSpeed;

    private boolean accelerating;
    private TurnDirection angularAccelerating;  // 1 for turning right, 0 for not turning, -1 for turning left

    private final DoubleBinding angle;     // the angle in radians
    private final DoubleBinding angleCos;
    private final DoubleBinding angleSin;

    private final DoubleProperty accThruster;
    private final DoubleProperty turnAcc;


    // Will need to rearrage this to be sure it wont go over a certain speed, angular or linear
    public ControlledTrajectory() {
        accThruster = new SimpleDoubleProperty();

        turnAcc = new SimpleDoubleProperty();

        accelerating = false;
        angularAccelerating = STOP;

        turnSpeed = new GameTimeActivatedIncrementableDoubleBinding(0.0, () -> turnAcc.get() * angularAccelerating.getSign());

        angle = new GameTimeActivatedIncrementableDoubleBinding(0.0, () -> (turnSpeed.get()));

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

    public void bindAccThruster(ObservableDoubleValue accThrust){
        accThruster.bind(accThrust);
    }

    public void bindTurnAcc(ObservableDoubleValue turnAc){
        turnAcc.bind(turnAc);
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

    public void turn(TurnDirection angularAccDirection) { angularAccelerating = angularAccDirection; }

    public void accelerate(boolean acc){
        accelerating = acc;
    }
}
