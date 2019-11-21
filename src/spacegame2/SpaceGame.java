/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spacegame2;

import javafx.application.Application;
import javafx.stage.Stage;
import spacegame2.gamedata.gamestate.GameState;
import spacegame2.userinterface.startscreen.StartScreen;
import spacegame2.userinterface.systemscreen.SystemScreen;

import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class SpaceGame extends Application {

    private static final Logger LOG = Logger.getLogger(SpaceGame.class.getName());

    private final StartScreen startScreen;
    private final SystemScreen systemScreen;
    private Stage stage;

    private GameState currentState;


    /**
     * main game constructor that instanciates all the differents scenes
     */
    public SpaceGame() {

        startScreen = new StartScreen(this);
        systemScreen = new SystemScreen(this);
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;

        stage.setTitle("My magic space game (find a better title)!");
        stage.setScene(startScreen);
        stage.sizeToScene();

        stage.show();

        stage.setMinHeight(primaryStage.getHeight());
        stage.setMinWidth(primaryStage.getWidth());
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public GameState getPlayerState() {
        return currentState;
    }

    public void setPlayerState(GameState load) {
        currentState = load;
    }

    public boolean changeSceneToSystemScreen() {
        stage.setScene(systemScreen);
        return true;
    }
}
