package client_server.data;

import data.BusStop;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class SearchDataHandler implements Serializable {
    private BusStop startStation;
    private BusStop endStation;
    private int maxCountofChange;
    private DateTime searchingTime = new DateTime();
    private Boolean actualTime = true;
    private WayOfTravel wayOfTravel = WayOfTravel.fastest;
    private List<Plan> planList = new LinkedList<>();
    private int totalTime;
    private DateTime startTime;
    private DateTime endTime;
    private int waitingTime;

    public BusStop getStartStation() {
        return startStation;
    }

    public void setStartStation(BusStop startStation) {
        this.startStation = startStation;
    }

    public BusStop getEndStation() {
        return endStation;
    }

    public void setEndStation(BusStop endStation) {
        this.endStation = endStation;
    }

    public int getMaxCountofChange() {
        return maxCountofChange;
    }

    public void setMaxCountofChange(int maxCountofChange) {
        this.maxCountofChange = maxCountofChange;
    }

    public DateTime getSearchingTime() {
        return searchingTime;
    }

    public void setSearchingTime(DateTime searchingTime) {
        this.searchingTime = searchingTime;
    }

    public Boolean getActualTime() {
        return actualTime;
    }

    public void setActualTime(Boolean actualTime) {
        this.actualTime = actualTime;
    }

    public WayOfTravel getWayOfTravel() {
        return wayOfTravel;
    }

    public void setWayOfTravel(WayOfTravel wayOfTravel) {
        this.wayOfTravel = wayOfTravel;
    }

    public List<Plan> getPlanList() {
        return planList;
    }

    public void setPlanList(List<Plan> planList) {
        this.planList = planList;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public DateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(DateTime startTime) {
        this.startTime = startTime;
    }

    public DateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(DateTime endTime) {
        this.endTime = endTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }
}
