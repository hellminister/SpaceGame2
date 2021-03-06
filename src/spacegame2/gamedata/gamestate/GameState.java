/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spacegame2.gamedata.gamestate;

import spacegame2.gamedata.gamestate.time.StarDate_Old;
import spacegame2.gamedata.playerinfo.Player;

import java.io.*;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class GameState implements Serializable {

    private static final Logger LOG = Logger.getLogger(GameState.class.getName());
    private static final long serialVersionUID = 1L;

    /**
     * Contains the players info
     *
     * @serial
     */
    private Player player;
    private StarDate_Old time;

    /**
     * Creates a gameState for a new player
     */
    public GameState() {
        time = new StarDate_Old();
    }

    public String getInfo() {
        return player.getInfo();
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.writeObject(time);
        s.writeObject(player);

    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        time = (StarDate_Old) s.readObject();
        player = (Player) s.readObject();
    }


    public Player getPlayerState() {
        return player;
    }

    public String getSaveFileName() {
        return player.getSaveFileName();
    }

    public StarDate_Old getStarDate() {
        return time;
    }

    public void setPlayer(Player newPlayerInfo) {
        player = newPlayerInfo;
    }
}
