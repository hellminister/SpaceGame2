package spacegame2.gamedata.drawableobject;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class StellarObjectPositioningMap {
    private final SortedMap<Double, StellarObject> leftX;
    private final SortedMap<Double, StellarObject> rightX;
    private final SortedMap<Double, StellarObject> upperY;
    private final SortedMap<Double, StellarObject> lowerY;

    public StellarObjectPositioningMap() {
        this.leftX = new TreeMap<>();
        this.rightX = new TreeMap<>();
        this.upperY = new TreeMap<>();
        this.lowerY = new TreeMap<>();
    }

    public void clear(){
        leftX.clear();
        rightX.clear();
        upperY.clear();
        lowerY.clear();
    }

    public Set<StellarObject> getAllInSquare(double left, double right, double upper, double lower){
        Set<StellarObject> result = new HashSet<>();


        return result;
    }
}
