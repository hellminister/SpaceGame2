/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spacegame2.gamedata.gamestate;


import java.util.logging.Logger;

/**
 * Made this a singleton to reduce coupling of classes
 * And anyway this was going to be a final field in the main game class
 * as there is supposed to be only 1 such instance
 * @author user
 */
public final class GameWorld {

    private static final Logger LOG = Logger.getLogger(GameWorld.class.getName());
    private static final GameWorld theGameWorld;
    static {
        theGameWorld = new GameWorld();
    }

    private GameState currentState;

    /**
     * Initialize the gameWorld
     */
    private GameWorld() {
        currentState = null;
    }

    public static GameWorld accessGameWorld(){
        return theGameWorld;
    }

    public void setPlayerState(GameState gameData) {
        currentState = gameData;
    }

    public GameState getState() {
        return currentState;
    }


    public StarDate getCurrentStarDate(){
        return getState().getStarDate();
    }

}
