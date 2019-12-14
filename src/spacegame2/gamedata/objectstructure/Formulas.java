package spacegame2.gamedata.objectstructure;

import javafx.beans.binding.DoubleBinding;
import spacegame2.util.DoubleSumBinding;
import spacegame2.util.Formula;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Formulas {

    public static enum FormulaOwner{
        SHIP("src/resources/data/objectstructure/formula/ship.txt"),
        ;

        private String fileName;

        FormulaOwner(String fileName){
            this.fileName = fileName;
        }

        public String getFileName() {
            return fileName;
        }
    }

    private static final Map<FormulaOwner, Formulas> allFormulas;

    static {
        Map<FormulaOwner, Formulas> forms = new EnumMap<>(FormulaOwner.class);

        for (FormulaOwner fo : FormulaOwner.values()){
            forms.put(fo, new Formulas(fo.getFileName()));
        }


        allFormulas = Collections.unmodifiableMap(forms);
    }

    public static void populateMapFor(FormulaOwner fo, Map<String, DoubleBinding> map, Map<String, DoubleSumBinding> numericProperty){
        allFormulas.get(fo).populateMap(map, numericProperty);
    }

    private final List<Formula> formulas;


    private Formulas(String filePath){
        List<Formula> forms = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)))) {
            String line = reader.readLine();
            while (line != null) {
                Formula f = new Formula(line);
                forms.add(f);

                line = reader.readLine();
            }
        } catch (IOException ex) {
            Logger.getLogger(Formulas.class.getName()).log(Level.SEVERE, null, ex);
        }

        formulas = Collections.unmodifiableList(forms);
    }


    private void populateMap(Map<String, DoubleBinding> valueMap, Map<String, DoubleSumBinding> numericProperty) {
        for (Formula f : formulas){
            f.addFormulaTo(valueMap, numericProperty);
        }
    }

}
