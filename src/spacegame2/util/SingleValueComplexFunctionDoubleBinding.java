package spacegame2.util;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ObservableDoubleValue;

/**
 * Created by user on 2016-12-23.
 */
public class SingleValueComplexFunctionDoubleBinding extends DoubleBinding {
    protected ComputeFunction function;

    public SingleValueComplexFunctionDoubleBinding(ObservableDoubleValue value, ComputeFunction funct) {
        super.bind(value);
        function = funct;
    }

    @Override
    protected double computeValue() {
        return function.getValue();
    }
}
