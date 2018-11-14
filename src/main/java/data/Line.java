package data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Line implements Serializable {
    @Id
    private String lineNumber;
    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<SingleInformation> plan;
    private String startStation;
    private String endStation;

    public Line(String lineNumber, String startStation, String endStation) {
        this.lineNumber = lineNumber;
        this.startStation = startStation;
        this.endStation = endStation;
        this.plan = new ArrayList<>();
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

    public List<SingleInformation> getPlan() {
        return plan;
    }

    public void setPlan(List<SingleInformation> plan) {
        this.plan = plan;
    }
}
