package spacegame2.gamedata.gamestate.time;

import javafx.beans.property.LongProperty;
import javafx.beans.property.ReadOnlyLongProperty;
import javafx.beans.property.SimpleLongProperty;

public class StarDate {

    private LongProperty time;

    public StarDate() {
        this(0);
    }

    /**
     * creates a new stardate starting at the specified date
     * @param time
     */
    public StarDate(long time) {
        this.time = new SimpleLongProperty(time);
    }

    public void addSeconds(long seconds){
        time.set(time.get() + seconds);
    }

    public ReadOnlyLongProperty timeProperty() {
        return time;
    }

    public long getTime(){
        return time.get();
    }
}
