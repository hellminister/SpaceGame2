package spacegame2.util;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ObservableDoubleValue;

public class DoubleSquareRootBinding extends DoubleBinding {

    private ObservableDoubleValue value;

    public DoubleSquareRootBinding(ObservableDoubleValue value) {
        this.value = value;
        super.bind(value);
    }

    @Override
    protected double computeValue() {
        return Math.sqrt(value.get());
    }

    @Override
    public String toString() {
        return "sqrt( " + value.toString() + " )";
    }
}
