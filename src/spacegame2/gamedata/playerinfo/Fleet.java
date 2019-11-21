package spacegame2.gamedata.playerinfo;

import spacegame2.gamedata.drawableobject.ship.Ship;

import java.util.LinkedList;
import java.util.List;

public class Fleet {
    private List<Ship> ships;

    public Fleet() {
        ships = new LinkedList<>();
    }

    public void addShip(Ship ship){
        ships.add(ship);
    }

    public void removeShip(Ship ship) {
        ships.remove(ship);
    }

}
