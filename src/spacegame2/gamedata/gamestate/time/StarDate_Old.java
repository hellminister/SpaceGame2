/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spacegame2.gamedata.gamestate.time;

import javafx.beans.property.LongProperty;
import javafx.beans.property.ReadOnlyLongProperty;
import javafx.beans.property.SimpleLongProperty;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class StarDate_Old implements Serializable {

    private static final Logger LOG = Logger.getLogger(StarDate_Old.class.getName());
    private static final long serialVersionUID = 1L;
    private static final String FILE_PATH = "src/resources/data/objectstructure/time/timeDescription.txt";

    public static final int STARTING_YEAR;
    public static final int WEEKS_IN_MONTH;
    public static final int DAYS_IN_WEEK;
    public static final int MONTHS_IN_YEARS;
    public static final int SECONDS_IN_MINUTES;
    public static final int MINUTES_IN_HOURS;
    public static final int HOURS_IN_DAY;
    public static final int SECONDS_IN_HOURS;
    public static final int SECONDS_IN_DAY;
    public static final int DAYS_IN_MONTH;
    public static final int DAYS_IN_YEAR;

    public static final List<String> dayNames;
    public static final List<String> monthNames;

    /*
    File Structure :
    StartingYear (int)
    WeeksInMonth (int)
    SecondsInMinute (int)
    MinutesInHour (int)
    HoursInDay (int)
    DayNames (a | separated list)
    MonthNames (a | separated list)
     */
    static {
        int[] temp = new int[5];
        List<String> a = Collections.EMPTY_LIST;
        List<String> b = Collections.EMPTY_LIST;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(FILE_PATH)))) {
            temp[0] = Integer.parseInt(reader.readLine());
            temp[1] = Integer.parseInt(reader.readLine());
            temp[2] = Integer.parseInt(reader.readLine());
            temp[3] = Integer.parseInt(reader.readLine());
            temp[4] = Integer.parseInt(reader.readLine());
            String toSplit = reader.readLine();
            a = List.of(toSplit.split(" "));
            System.out.println(a);
            toSplit = reader.readLine();
            b = List.of(toSplit.split(" "));
            System.out.println(b);
        } catch (IOException ex) {
            Logger.getLogger(StarDate_Old.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            STARTING_YEAR = temp[0];
            WEEKS_IN_MONTH = temp[1];
            SECONDS_IN_MINUTES = temp[2];
            MINUTES_IN_HOURS = temp[3];
            HOURS_IN_DAY = temp[4];
            dayNames = a;
            DAYS_IN_WEEK = dayNames.size();
            monthNames = b;
            MONTHS_IN_YEARS = monthNames.size();

            SECONDS_IN_HOURS = SECONDS_IN_MINUTES * MINUTES_IN_HOURS;
            SECONDS_IN_DAY = SECONDS_IN_MINUTES * MINUTES_IN_HOURS * HOURS_IN_DAY;
            DAYS_IN_MONTH = DAYS_IN_WEEK * WEEKS_IN_MONTH;
            DAYS_IN_YEAR = DAYS_IN_MONTH * MONTHS_IN_YEARS;
        }
    }

    private LongProperty time;

    /**
     * Creates a new stardate starting on the first game day
     */
    public StarDate_Old() {
        this(0);
    }

    /**
     * creates a new stardate starting at the specified date
     * @param time
     */
    public StarDate_Old(long time) {
        this.time = new SimpleLongProperty(time);
    }
    
    /**
     * Adds the number of given days to the date
     * @param nbDay MUST be positive or zero
     */
    public void add(long nbDay){
        if (nbDay < 0){
            throw new IllegalArgumentException("Cannot add a negative number of days : " + nbDay);
        }
        addSeconds(nbDay * SECONDS_IN_DAY);
    }

    public void addSeconds(long seconds){
        time.set(time.get() + seconds);
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.writeLong(time.get());
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        time = new SimpleLongProperty(s.readLong());
    }

    public ReadOnlyLongProperty timeProperty() {
        return time;
    }

    public String getCurrentDayName(){
        return dayNames.get((int)(getCurrentDay()-1)%DAYS_IN_WEEK);
    }

    public long getCurrentSeconds(){
        return time.get() % SECONDS_IN_MINUTES;
    }

    public long getCurrentMinutes(){
        return (time.get() / SECONDS_IN_MINUTES) % MINUTES_IN_HOURS;
    }

    public long getCurrentHours(){
        return (time.get() / SECONDS_IN_HOURS) % HOURS_IN_DAY;
    }

    public long getCurrentDay(){
        return ((time.get() / SECONDS_IN_DAY) % DAYS_IN_MONTH) + 1;
    }

    public long getCurrentMonth(){
        return (((time.get() / SECONDS_IN_DAY) / DAYS_IN_MONTH) % MONTHS_IN_YEARS) + 1;
    }

    public long getCurrentYear(){
        return STARTING_YEAR + ((time.get() / SECONDS_IN_DAY) / DAYS_IN_YEAR);
    }

    @Override
    public String toString() {
        return String.format("%s, %d-%d-%d %02d:%02d",getCurrentDayName(), getCurrentDay(), getCurrentMonth(), getCurrentYear(), getCurrentHours(), getCurrentMinutes());
    }

    public String toLongString(){
        return String.format("%s, %d-%d-%d %02d:%02d:%02d",getCurrentDayName(), getCurrentDay(), getCurrentMonth(), getCurrentYear(), getCurrentHours(), getCurrentMinutes(), getCurrentSeconds());
    }
}
