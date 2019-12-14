package spacegame2.gamedata.drawableobject.ship;

import javafx.beans.binding.DoubleBinding;
import javafx.scene.image.ImageView;
import spacegame2.gamedata.drawableobject.StellarObject;
import spacegame2.gamedata.objectstructure.Formulas;
import spacegame2.gamedata.playerinfo.Fleet;
import spacegame2.userinterface.systemscreen.StellarObjectSprite;
import spacegame2.util.DoubleSumBinding;

import java.util.*;

public class Ship extends StellarObject {

    private final Map<String, DoubleBinding> formulas;
    private final Map<String, DoubleSumBinding> numericProperty;

    private final Equipment shipBasicStats;
    
    private final SortedMap<String, Equipment> shipInternalEquipment;

    private final List<EquipmentSlot> shipSlot;

    private ShipControl controller;
    private ShipControl ai;
    private Fleet inFleet;

    public Ship(ShipControl ai, String name, ShipChassis shipBasicStats) {
        super(new ControlledTrajectory(),true, name, new StellarObjectSprite(new ImageView(shipBasicStats.getSprite())));

        this.ai = ai;
        controller = ai;
        controller.sittingIn(this);
        formulas = new HashMap<>();
        numericProperty = new HashMap<>();
        Formulas.populateMapFor(Formulas.FormulaOwner.SHIP, formulas, numericProperty);

        this.shipBasicStats = shipBasicStats;

        shipBasicStats.insertValuesIn(numericProperty);

        // dont remember how i wanted to use these
        shipInternalEquipment = new TreeMap<>();
        shipSlot = new ArrayList<>();

        trajectory().bindAccThruster(formulas.get("accThruster"));
        trajectory().bindTurnAcc(formulas.get("turnAcc"));
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

    /**
     * Makes sure that the ship is in at most 1 fleet
     * @param fleet
     */
    public void inFleet(Optional<Fleet> fleet) {
        if (inFleet != null){
            inFleet.removeShip(this);
        }
        inFleet = fleet.orElse(null);
    }
}
