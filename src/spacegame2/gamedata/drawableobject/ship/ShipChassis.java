package spacegame2.gamedata.drawableobject.ship;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.Image;
import spacegame2.userinterface.ImageLibrary;
import spacegame2.util.DoubleSumBinding;

import java.util.HashMap;
import java.util.Map;

public class ShipChassis implements Equipment {

    private String name;
    private String description;
    private Image sprite;
    private Map<String, DoubleProperty> numericvalues;

    public ShipChassis(String name, String description) {
        this.name = name;
        this.description = description;
        numericvalues = new HashMap<>();
    }

    public ShipChassis() {
        numericvalues = new HashMap<>();
    }

    public void setName(String n){
        name = n;
    }

    public void setSprite(String fileName){
        sprite = ImageLibrary.getImage(fileName);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addNumericValue(String name, double value){
        numericvalues.put(name, new SimpleDoubleProperty(value));
    }

    @Override
    public void insertValuesIn(Map<String, DoubleSumBinding> numericProperty) {
        numericvalues.forEach((key, value) -> {
            numericProperty.computeIfAbsent(key, DoubleSumBinding::new).addToSum(value);
        });
    }

    public Image getSprite() {
        return sprite;
    }
}
