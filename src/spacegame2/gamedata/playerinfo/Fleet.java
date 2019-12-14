package spacegame2.gamedata.playerinfo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import spacegame2.gamedata.drawableobject.ship.Ship;

import java.util.ArrayList;
import java.util.Optional;


public class Fleet {
    private Ship flagship;
    private ObservableList<Ship> ships;

    public Fleet() {
        ships = FXCollections.observableList(new ArrayList<>());
    }

    public void addShip(Ship ship){
        if(!ships.contains(ship)) {
            ships.add(ship);
            ship.inFleet(Optional.of(this));
        }
    }

    /**
     *
     * @param ship
     * @return the flagship
     */
    public Optional<Ship> removeShip(Ship ship) {
        ships.remove(ship);
        ship.inFleet(Optional.empty());
        if (flagship == ship){
            flagship = ships.isEmpty() ? null : ships.get(0);
        }
        return getFlagShip();
    }

    public void setFlagship(Ship flagship) {
        addShip(flagship);
        this.flagship = flagship;
    }

    public Optional<Ship> getFlagShip(){
        return Optional.ofNullable(flagship);
    }

    public boolean isEmpty(){
        return ships.isEmpty();
    }
}
