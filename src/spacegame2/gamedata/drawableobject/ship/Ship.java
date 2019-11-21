package spacegame2.gamedata.drawableobject.ship;

import javafx.beans.property.DoubleProperty;
import spacegame2.gamedata.drawableobject.StellarObject;

import java.util.*;

public class Ship extends StellarObject {

    private final Map<String, DoubleProperty> numericProperty;
    
    private final SortedMap<String, Equipment> shipInternalEquipment;

    private final List<EquipmentSlot> shipSlot;

    private ShipControl controller;
    private ShipControl ai;

    public Ship(ShipControl ai, String name) {
        super(new ControlledTrajectory(), true, name, null);
        this.ai = ai;
        controller = ai;
        controller.sittingIn(this);
        numericProperty = new HashMap<>();
        shipInternalEquipment = new TreeMap<>();
        shipSlot = new ArrayList<>();
    }

    public ShipControl controller() {
        return controller;
    }

    public ControlledTrajectory trajectory() { return (ControlledTrajectory) position;}

    public void returnToAI() {
        controller = ai;
    }

    public void changeController(ShipControl control){
        controller = control;
        controller.sittingIn(this);
    }
}
