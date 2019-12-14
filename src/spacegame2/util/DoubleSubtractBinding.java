package spacegame2.util;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ObservableDoubleValue;

public class DoubleSubtractBinding extends DoubleBinding {
    private ObservableDoubleValue v1;
    private ObservableDoubleValue v2;


    /**
     * calculation will be treat1 - treat
     * @param treat
     * @param treat1
     */
    public DoubleSubtractBinding(ObservableDoubleValue treat, ObservableDoubleValue treat1) {
        v2 = treat;
        v1 = treat1;
        super.bind(treat, treat1);
    }

    @Override
    protected double computeValue() {
        return v1.get() - v2.get();
    }

    @Override
    public String toString() {
        return "( " + v1.toString() + " - " + v2.toString() + " )";
    }
}
