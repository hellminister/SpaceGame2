package spacegame2.util;

import javafx.beans.binding.DoubleBinding;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Formula {

    private static final Logger LOG = Logger.getLogger(Formula.class.getName());
    private static List<String> OPERATORS = List.of("+", "-", "/", "*", "^", "sqrt", "(", ")");

    private String valueName;
    private List<String> formula;

    public Formula(String rawFormula) {
        var elements = rawFormula.split(" ");
        valueName = elements[0];
        formula = toPostFix(Arrays.copyOfRange(elements,2, elements.length));
    }

    private static List<String> toPostFix(String... elements) {
        Stack<String> output = new Stack<>();
        Stack<String> operators = new Stack<>();

        String v2;

        for (String v : elements){
            if (OPERATORS.contains(v)){
                if (operators.empty()){
                    operators.push(v);
                } else if (v.equals("(")){
                    operators.push(v);
                } else if (v.equals(")")){
                    v2 = operators.pop();
                    while (!v2.equals("(")){
                        output.push(v2);
                        v2 = operators.pop();
                    }
                } else if (higherPrecedence(v, operators.peek())){
                    operators.push(v);
                } else {
                    output.push(operators.pop());
                    operators.push(v);
                }

            } else {
                output.push(v);
            }
        }

        while (!operators.empty()){
            output.push(operators.pop());
        }
        return Collections.unmodifiableList(output);
    }

    private static boolean higherPrecedence(String v, String peek) {
        if (v.equals("^") || v.equals("sqrt")){
            return !(peek.equals("sqrt"));
        } else if (v.equals("*") || v.equals("/")){
            return !(peek.equals("*") || peek.equals("/"));
        }
        return false;
    }

    /**
     * Adds the formula plus any missing arguments of the formula to the giving map
     *
     * missing arguments are added as DoubleSumBinding
     *
     * @param values
     */
    public void addFormulaTo(Map<String, DoubleBinding> values, Map<String, DoubleSumBinding> numericProperty) {
        LinkedList<String> data = new LinkedList<>();
        data.addAll(formula);
        DoubleBinding result = treat(values, numericProperty, data);
        values.put(valueName, result);
    }

    private DoubleBinding treat(Map<String, DoubleBinding> values, Map<String, DoubleSumBinding> numericProperty, LinkedList<String> rest){
        String v = rest.removeLast();
        if (OPERATORS.contains(v)){
            DoubleBinding result = null;
            switch (v) {
                case "*":
                    result = new DoubleMultiplyBinding(treat(values, numericProperty, rest), treat(values, numericProperty, rest));
                    break;
                case "/":
                    result = new DoubleDivideBinding(treat(values, numericProperty, rest), treat(values, numericProperty, rest));
                    break;
                case "+":
                    result = new DoubleAddBinding(treat(values, numericProperty, rest), treat(values, numericProperty, rest));
                    break;
                case "-":
                    result = new DoubleSubtractBinding(treat(values, numericProperty, rest), treat(values, numericProperty, rest));
                    break;
                case "^":
                    result = new DoubleExponentBinding(treat(values, numericProperty, rest), treat(values, numericProperty, rest));
                    break;
                case "sqrt":
                    result = new DoubleSquareRootBinding(treat(values, numericProperty, rest));
                    break;
                default:
                    LOG.log(Level.SEVERE, v + " is untreated or got a parentesis");
            }
            return result;



        } else if (NumberUtils.isParsable(v)){
            return new DoubleBinding() {
                @Override
                protected double computeValue() {
                    return Double.parseDouble(v);
                }

                @Override
                public String toString() {
                    return Double.toString(get());
                }
            };
        } else {
            if (values.containsKey(v)){
                return values.get(v);
            }
            return numericProperty.computeIfAbsent(v, t -> new DoubleSumBinding(t));
        }
    }

    /**
     * Testing to be sure it gives the right result
     * @param args
     */
    public static void main(String[] args) {
        // ( ( longueur ^ 2 + largeur ^ 2) / 12 ) * mass / turnThrusting
        Formula test = new Formula("x = ( ( longueur ^ 2 + largeur ^ 2 ) / 12 ) * mass / turnThrusting");
        System.out.println("( ( longueur ^ 2 + largeur ^ 2 ) / 12 ) * mass / turnThrusting");
        System.out.println(Arrays.toString(test.formula.toArray()));
        Map<String, DoubleBinding> result = new HashMap<>();
        Map<String, DoubleSumBinding> sums = new HashMap<>();
        test.addFormulaTo(result, sums);
        System.out.println(result.get(test.valueName));
        System.out.println();


        // 3 + 4 × 2 ÷ ( 1 − 5 ) ^ 2 ^ 3
        test = new Formula("x = 3 + 4 * 2 / ( 1 − 5 ) ^ 2 ^ 3");
        System.out.println("3 + 4 * 2 / ( 1 − 5 ) ^ 2 ^ 3");
        System.out.println(Arrays.toString(test.formula.toArray()));
        // ( ( longueur ^ 2 + sqrt largeur ^ 2) / 12 ) * mass / turnThrusting
        test = new Formula("x = ( ( longueur ^ 2 + sqrt largeur ^ 2 ) / 12 ) * mass / turnThrusting");
        System.out.println(Arrays.toString(test.formula.toArray()));
        System.out.println();

        // ( ( 5 ^ 2 + 4 ^ 2 ) / 12 ) * 3 / 10")
        test = new Formula("x = ( ( 5 ^ 2 + 4 ^ 2 ) / 12 ) * 3 / 10");
        System.out.println("( ( 5 ^ 2 + 4 ^ 2 ) / 12 ) * 3 / 10");
        System.out.println(Arrays.toString(test.formula.toArray()));
        result = new HashMap<>();
        sums = new HashMap<>();
        test.addFormulaTo(result, sums);
        System.out.println(result.get(test.valueName));
        System.out.println();

    }

}
