package spacegame2.gamedata.objectstructure;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class PlayerStructure implements Iterator<Triple<String, String, Optional<Set<String>>>>{

    private static final String PLAYER_STRUCTURE_URL = "src/resources/data/objectstructure/PlayerStructure.txt";

    private static final Map<String, String> playerStringAttribute;
    private static final Map<String, Set<String>> attributeValue;
    private static final Map<String, Map<String, Set<String>>> linkedAttributeValue;
    private static final Map<String, String> linkedAttribute;
    private static final List<String> saveFileName;

    static {
        playerStringAttribute = new LinkedHashMap<>();
        attributeValue = new HashMap<>();
        linkedAttributeValue = new HashMap<>();
        linkedAttribute = new HashMap<>();
        saveFileName = new ArrayList<>();


        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(PLAYER_STRUCTURE_URL)))) {
            String line = reader.readLine();
            String[] words;
            String msg;
            String filling = "";
            int size = 0;

            while (line != null) {
                words = line.split(" ");

                switch (words[0]){
                    case "s":
                        msg = String.join(" ", Arrays.copyOfRange(words, 2, words.length));
                        playerStringAttribute.put(words[1], msg);
                        break;
                    case "l":
                        msg = String.join(" ", Arrays.copyOfRange(words, 3, words.length));
                        playerStringAttribute.put(words[1], msg);
                        attributeValue.put(words[1], Set.of(words[2].split("#")));
                        break;
                    case "m":
                        msg = String.join(" ", Arrays.copyOfRange(words, 3, words.length));
                        playerStringAttribute.put(words[1], msg);
                        linkedAttribute.put(words[1], words[2]);
                        linkedAttributeValue.put(words[1], new HashMap<>());
                        filling = words[1];
                        size = attributeValue.get(words[2]).size();
                        break;
                    case "f":
                        msg = String.join(" ", Arrays.copyOfRange(words, 1, words.length-1));
                        saveFileName.add(msg);
                        saveFileName.addAll(Arrays.asList(words[words.length-1].split(",")));
                        break;
                    default:
                        if (!filling.isEmpty()){
                            linkedAttributeValue.get(filling).put(words[0], Set.of(words[1].split("#")));
                            size--;
                            if (size <= 0){
                                filling = "";
                                size = 0;
                            }
                        } else {
                            Logger.getLogger(PlayerStructure.class.getName()).log(Level.WARNING, words[0] + " is untreated.");
                        }
                }
                line = reader.readLine();
            }

        } catch (IOException ex) {
            Logger.getLogger(PlayerStructure.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static List<String> getSaveFileName(){
        return saveFileName;
    }

    private Iterator<String> currentAttribute;
    private Map<String, String> values;

    public PlayerStructure(Map<String, String> answers){
        currentAttribute = playerStringAttribute.keySet().iterator();
        values = answers;
    }

    @Override
    public boolean hasNext() {
        return currentAttribute.hasNext();
    }

    @Override
    public Triple<String, String, Optional<Set<String>>> next() {
        String attribName = currentAttribute.next();
        String question = playerStringAttribute.get(attribName);
        Optional<Set<String>> possibleChoice = Optional.empty();

        Set<String> choices = attributeValue.get(attribName);
        if (choices != null && !choices.isEmpty()){
            possibleChoice = Optional.of(Collections.unmodifiableSet(choices));
        } else {
            String linked = linkedAttribute.get(attribName);
            if (linked != null && !linked.isEmpty()){
                choices = linkedAttributeValue.get(attribName).get(values.get(linked));
                if (choices != null && !choices.isEmpty()){
                    possibleChoice = Optional.of(Collections.unmodifiableSet(choices));
                } else {
                    throw new RuntimeException(String.join(" ", "Missing elements for ", attribName, linked, values.get(linked)));
                }
            }
        }


        return new Triple<>(attribName, question, possibleChoice);
    }
}
