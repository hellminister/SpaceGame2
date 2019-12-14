package spacegame2.util;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ObservableDoubleValue;

public class DoubleExponentBinding extends DoubleBinding {

    private ObservableDoubleValue value;
    private ObservableDoubleValue exponent;

    public DoubleExponentBinding(ObservableDoubleValue exponent, ObservableDoubleValue value) {
        this.value = value;
        this.exponent = exponent;
        super.bind(value, exponent);
    }

    @Override
    protected double computeValue() {
        return Math.pow(value.get(), exponent.get());
    }

    @Override
    public String toString() {
        return "( " + value.toString() + " ^ " + exponent.toString() + " )";
    }
}
