package data;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Line implements Serializable {
    @Id
    private String lineNumber;
    private String startStation;
    private String endStation;
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @NotFound(action = NotFoundAction.IGNORE)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<BusStop> busStopList;
    private int generalNumber;

    public Line(String lineNumber, String startStation, String endStation) {
        this.lineNumber = lineNumber;
        this.startStation = startStation;
        this.endStation = endStation;
        this.generalNumber = Integer.parseInt(lineNumber.substring(0,lineNumber.length()-1));
        this.busStopList = new LinkedList<>();
    }

    public Line() {
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getStartStation() {
        return startStation;
    }

    public void setStartStation(String startStation) {
        this.startStation = startStation;
    }

    public String getEndStation() {
        return endStation;
    }

    public void setEndStation(String endStation) {
        this.endStation = endStation;
    }

    public int getGeneralNumber() {
        return generalNumber;
    }

    public void setGeneralNumber(int generalNumber) {
        this.generalNumber = generalNumber;
    }

    public List<BusStop> getBusStopList() {
        return busStopList;
    }

    public void setBusStopList(List<BusStop> busStopList) {
        this.busStopList = busStopList;
    }
}
