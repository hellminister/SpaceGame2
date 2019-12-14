package spacegame2.userinterface;

import javafx.scene.input.KeyEvent;
import spacegame2.gamedata.drawableobject.ship.Ship;
import spacegame2.gamedata.drawableobject.ship.ShipControl;

import static spacegame2.gamedata.drawableobject.ship.TurnDirection.*;

public final class ManualControl implements ShipControl {
    public static final ManualControl manualControl = new ManualControl();

    private ManualControl(){

    }

    private Ship controlling;

    @Override
    public void sittingIn(Ship ship) {
        if (controlling != null){
            controlling.returnToAI();
        }
        controlling = ship;
    }

    public void gotKeyPressedAction(KeyEvent e){
        System.out.println("key pressed " + e);
        switch (e.getCode()) {
            case RIGHT:
                controlling.trajectory().turn(RIGHT);
                break;
            case LEFT:
                controlling.trajectory().turn(LEFT);
                break;
            case UP:
                controlling.trajectory().accelerate(true);
                break;
            default:
                break;
        }
    }

    public void gotKeyReleasedAction(KeyEvent e){
        System.out.println("key released " + e);
        switch (e.getCode()) {
            case UP:
                controlling.trajectory().accelerate(false);
                break;
            case RIGHT:
            case LEFT:
                controlling.trajectory().turn(STOP);
            default:
                break;
        }
    }
}
