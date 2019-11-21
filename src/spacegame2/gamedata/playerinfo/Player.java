package spacegame2.gamedata.playerinfo;

import spacegame2.userinterface.ManualControl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
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

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        playerInfo = (Map<String,String>) s.readObject();
        saveFileNameStringFormat = (String) s.readObject();
        saveFileNameArg = (String[]) s.readObject();
    }
}
