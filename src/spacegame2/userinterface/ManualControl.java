package spacegame2.userinterface;

import javafx.scene.input.KeyEvent;
import spacegame2.gamedata.drawableobject.ship.Ship;
import spacegame2.gamedata.drawableobject.ship.ShipControl;

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
        switch (e.getCode()) {
            case RIGHT:
                controlling.trajectory().turnRight();
                break;
            case LEFT:
                controlling.trajectory().turnLeft();
                break;
            case UP:
                controlling.trajectory().accelerate(true);
                break;
            case DOWN:
              //  controlling.trajectory().accelerate();
                break;
            default:
                break;
        }
    }

    public void gotKeyReleasedAction(KeyEvent e){
        switch (e.getCode()) {
            case UP:
                controlling.trajectory().accelerate(false);
                break;
            default:
                break;
        }
    }
}
