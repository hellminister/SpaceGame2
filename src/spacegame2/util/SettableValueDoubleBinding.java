package spacegame2.util;

import javafx.beans.value.ObservableDoubleValue;

public interface SettableValueDoubleBinding extends ObservableDoubleValue {
    void set(double value);
}
