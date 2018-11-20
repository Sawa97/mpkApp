package controllers.data;

import data.Line;
import org.joda.time.DateTime;

public class BusStopShedule  {
    private String lastStation;
    private int timeToLeave;
    private Line line;
    private DateTime time;


    public BusStopShedule(String lastStation, int timeToLeave, Line line, DateTime time ) {
        this.lastStation = lastStation;
        this.timeToLeave = timeToLeave;
        this.line = line;
        this.time = time;
    }

    public String getLastStation() {
        return lastStation;
    }

    public void setLastStation(String lastStation) {
        this.lastStation = lastStation;
    }

    public int getTimeToLeave() {
        return timeToLeave;
    }

    public void setTimeToLeave(int timeToLeave) {
        this.timeToLeave = timeToLeave;
    }

    public Line getLine() {
        return line;
    }


    public void setLine(Line line) {
        this.line = line;
    }

    public DateTime getTime() {
        return time;
    }

    public void setTime(DateTime time) {
        this.time = time;
    }
}


