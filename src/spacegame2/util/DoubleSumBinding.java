package spacegame2.util;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ObservableDoubleValue;

import java.util.HashSet;
import java.util.Set;

public class DoubleSumBinding extends DoubleBinding {

    Set<ObservableDoubleValue> valuesToSum;
    String name;

    public DoubleSumBinding(){
        this("unnamed");
    }

    public DoubleSumBinding(String t) {
        valuesToSum = new HashSet<>();
        name = t;
    }

    @Override
    protected double computeValue() {
        double sum = 0.0;

        for(ObservableDoubleValue v : valuesToSum){
            sum += v.get();
        }

        return sum;
    }

    public void addToSum(ObservableDoubleValue v){
        valuesToSum.add(v);
        super.bind(v);
    }

    public void removeFromSum(ObservableDoubleValue v){
        valuesToSum.remove(v);
        super.unbind(v);
    }

    @Override
    public String toString() {
        return name;
    }
}
