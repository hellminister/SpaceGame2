package spacegame2.gamedata.drawableobject.ship;

import spacegame2.util.DoubleSumBinding;

import java.util.Map;

public interface Equipment {
    void insertValuesIn(Map<String, DoubleSumBinding> numericProperty);
}
