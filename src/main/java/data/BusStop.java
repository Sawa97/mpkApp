package data;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class BusStop implements Serializable {
    @Id
    private int busStopNumber;
    private String busStopName;
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @NotFound(action = NotFoundAction.IGNORE)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Line> listOfLine;
    @ManyToMany(cascade = CascadeType.ALL)
    @NotFound(action = NotFoundAction.IGNORE)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<WalkTime> listOfWalkTime;

    public BusStop(int busStopNumber, String busStopName) {
        this.busStopName = busStopName;
        this.busStopNumber = busStopNumber;
        this.listOfLine = new ArrayList<>();
        this.listOfWalkTime = new ArrayList<>();
    }

    public BusStop() {
    }

    public int getBusStopNumber() {
        return busStopNumber;
    }

    public void setBusStopNumber(int busStopNumber) {
        this.busStopNumber = busStopNumber;
    }

    public List<Line> getListOfLine() {
        return listOfLine;
    }

    public void setListOfLine(List<Line> listOfLine) {
        this.listOfLine = listOfLine;
    }

    public List<WalkTime> getListOfWalkTime() {
        return listOfWalkTime;
    }

    public void setListOfWalkTime(List<WalkTime> listOfWalkTime) {
        this.listOfWalkTime = listOfWalkTime;
    }

    public String getBusStopName() {
        return busStopName;
    }

    public void setBusStopName(String busStopName) {
        this.busStopName = busStopName;
    }
}
