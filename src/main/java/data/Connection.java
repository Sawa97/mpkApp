package data;

import org.hibernate.annotations.*;
import org.joda.time.DateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Connection implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int connectionId;
    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @NotFound(action = NotFoundAction.IGNORE)
    private BusStop startBusStop;
    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @NotFound(action = NotFoundAction.IGNORE)
    private BusStop endBusStop;
    private int time;
    private boolean walk = false;
    private int distance =0;
    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @NotFound(action = NotFoundAction.IGNORE)
    private Line line;
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime date;

    public Connection(BusStop startBusStop, BusStop endBusStop, int time, boolean walk, int distance) {
        this.startBusStop = startBusStop;
        this.endBusStop = endBusStop;
        this.time = time;
        this.walk = walk;
        this.distance = distance;
    }

    public Connection(BusStop startBusStop, BusStop endBusStop, int time, Line line, DateTime dateTime) {
        this.startBusStop = startBusStop;
        this.endBusStop = endBusStop;
        this.time = time;
        this.line = line;
        this.date = dateTime;
    }

    public Connection() {
    }

    public BusStop getStartBusStop() {
        return startBusStop;
    }

    public void setStartBusStop(BusStop startBusStop) {
        this.startBusStop = startBusStop;
    }

    public BusStop getEndBusStop() {
        return endBusStop;
    }

    public void setEndBusStop(BusStop endBusStop) {
        this.endBusStop = endBusStop;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public boolean isWalk() {
        return walk;
    }

    public void setWalk(boolean walk) {
        this.walk = walk;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(int connectionId) {
        this.connectionId = connectionId;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }
}
