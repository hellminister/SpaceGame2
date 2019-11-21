package spacegame2.util;

import javafx.beans.binding.DoubleBinding;
import spacegame2.gamedata.gamestate.GameWorld;

/**
 * Created by user on 2016-12-23.
 */
public class GameTimeActivatedIncrementableDoubleBinding extends DoubleBinding {
    protected double oldValue;
    protected ComputeFunction value;

    public GameTimeActivatedIncrementableDoubleBinding(double initialValue, ComputeFunction incrementFunction) {
        super.bind(GameWorld.accessGameWorld().getCurrentStarDate().fractionalDateProperty());
        oldValue = initialValue;
        value = incrementFunction;
    }

    @Override
    protected double computeValue() {
        oldValue += value.getValue();
        return oldValue;
    }

    public void set(double v){
        oldValue = v;
    }
}
