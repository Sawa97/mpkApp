package data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Entity
public class WalkTime implements Serializable {
    @Id
    private int id;
   @OneToOne
    private BusStop busStop;
    private int time;
    private int distance;

    public WalkTime(int id, BusStop busStop, int time, int distance) {
        this.id = id;
        this.busStop = busStop;
        this.time = time;
        this.distance = distance;
    }

    public WalkTime() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BusStop getBusStop() {
        return busStop;
    }

    public void setBusStop(BusStop busStop) {
        this.busStop = busStop;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
