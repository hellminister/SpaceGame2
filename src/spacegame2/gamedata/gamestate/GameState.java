/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spacegame2.gamedata.gamestate;

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
    private StarDate time;

    /**
     * Creates a gameState for a new player
     * @param playerInfo 
     */
    public GameState(Player playerInfo) {
        this.player = playerInfo;
        time = new StarDate();
    }

    public String getInfo() {
        return player.getInfo();
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.writeObject(time);
        s.writeObject(player);

    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        time = (StarDate) s.readObject();
        player = (Player) s.readObject();
    }


    public Player getPlayerState() {
        return player;
    }

    public String getSaveFileName() {
        return player.getSaveFileName();
    }

    public StarDate getStarDate() {
        return time;
    }
}
