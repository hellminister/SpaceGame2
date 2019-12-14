package spacegame2.gamedata.playerinfo;

import org.apache.commons.lang3.StringUtils;
import spacegame2.gamedata.drawableobject.ship.Ship;
import spacegame2.gamedata.drawableobject.ship.ShipChassis;
import spacegame2.userinterface.ManualControl;

import java.io.*;
import java.util.List;
import java.util.Map;

public class Player implements Serializable {
    private Map<String, String> playerInfo;
    private Fleet fleet;
    private String saveFileNameStringFormat;
    private String[] saveFileNameArg;
    private final ManualControl manualControl;

    public Player(Map<String, String> info, List<String> saveFileName) {
        playerInfo = info;
        saveFileNameStringFormat = saveFileName.remove(0);
        saveFileNameArg = new String[saveFileName.size()];

        manualControl = ManualControl.manualControl;

        int i = 0;
        for (String s : saveFileName){
            saveFileNameArg[i] = playerInfo.get(s);
            i++;
        }

        fleet = new Fleet();

        //for testing purpose
        ShipChassis chassis = loadTestChassis();
        setFlagship(new Ship(manualControl, "Test", chassis));
    }

    private ShipChassis loadTestChassis() {
        String PLAYER_STRUCTURE_URL = "src/resources/data/shipchassis/test.txt";
        ShipChassis ship = new ShipChassis();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(PLAYER_STRUCTURE_URL)))) {
            String line = reader.readLine();
            String[] words;

            while (line != null) {
                words = line.split(" ");
                switch (words[0]){
                    case "name" :
                        ship.setName(StringUtils.join(words, " ", 1, words.length));
                        break;
                    case "description":
                        ship.setDescription(StringUtils.join(words, " ", 1, words.length));
                        break;
                    case "sprite":
                        ship.setSprite(words[1]);
                        break;
                    default:
                        ship.addNumericValue(words[0], Double.parseDouble(words[1]));
                }
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ship;
    }

    public String getValueFor(String attribName){
        return playerInfo.get(attribName);
    }

    public String getSaveFileName() {
        return String.format(saveFileNameStringFormat, (Object[]) saveFileNameArg);
    }

    public String getInfo() {
        return "Not Ready";
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.writeObject(playerInfo);
        s.writeObject(saveFileNameStringFormat);
        s.writeObject(saveFileNameArg);
    }

    public void setFlagship(Ship flagship){
        fleet.getFlagShip().ifPresent(Ship::returnToAI);
        flagship.changeController(manualControl);
        fleet.setFlagship(flagship);
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        @SuppressWarnings("unchecked")
        Map<String,String> playerInfo = (Map<String,String>) s.readObject();
        this.playerInfo = playerInfo;
        saveFileNameStringFormat = (String) s.readObject();
        saveFileNameArg = (String[]) s.readObject();
    }

    public Ship getFlagShip() {
        return fleet.getFlagShip().get();
    }
}
