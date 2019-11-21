package spacegame2.gamedata.drawableobject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class StarSystem {

    private String systemName;
    private final Map<String /* The Stellar Object Name */, StellarObject> celestialBodies; //thing intrinsec to the system like planets, stars, moons, etc

    private final Set<StellarObject> transientObjects; // Things like ships

    public StarSystem() {
        celestialBodies = new HashMap<>();
        transientObjects = new HashSet<>();
    }
}
