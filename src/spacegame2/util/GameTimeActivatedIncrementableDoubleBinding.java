package spacegame2.util;

import javafx.beans.binding.DoubleBinding;
import spacegame2.gamedata.gamestate.GameWorld;

/**
 * Created by user on 2016-12-23.
 * will remove this class, it complicates thing unnecessarily
 */
public class GameTimeActivatedIncrementableDoubleBinding extends DoubleBinding implements SettableValueDoubleBinding {
    protected double oldValue;
    protected ComputeFunction function;

    public GameTimeActivatedIncrementableDoubleBinding(double initialValue, ComputeFunction incrementFunction) {
        super.bind(GameWorld.accessGameWorld().getCurrentStarDate().timeProperty());
        oldValue = initialValue;
        function = incrementFunction;
    }

    @Override
    protected double computeValue() {
        oldValue += function.getValue();
        return oldValue;
    }

    public double previousValue(){
        return oldValue;
    }

    @Override
    public void set(double v){
        oldValue = v;
    }
}
