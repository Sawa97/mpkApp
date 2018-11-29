package client_server.data;

import data.BusStop;
import org.joda.time.DateTime;

import java.io.Serializable;

public class Plan implements Serializable {
    private BusStop startStation;
    private BusStop endStation;
    private String line;
    private int time;
    private int walkdistance;
    private DateTime startTime;
    private DateTime endTime;
    private int countofStation;
    private int waitingTimeforBus;

    public Plan(BusStop startStation, BusStop endStation, String line, int time, int walkdistance, DateTime startTime, DateTime endTime, int countofStation, int waitingTimeforBus) {
        this.startStation = startStation;
        this.endStation = endStation;
        this.line = line;
        this.time = time;
        this.walkdistance = walkdistance;
        this.startTime = startTime;
        this.endTime = endTime;
        this.countofStation = countofStation;
        this.waitingTimeforBus = waitingTimeforBus;
    }

    public Plan() {
    }

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

    public String getLine() { // zmienić żeby znalazł linie
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getWalkdistance() {
        return walkdistance;
    }

    public void setWalkdistance(int walkdistance) {
        this.walkdistance = walkdistance;
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

    public int getCountofStation() {
        return countofStation;
    }

    public void setCountofStation(int countofStation) {
        this.countofStation = countofStation;
    }

    public int getWaitingTimeforBus() {
        return waitingTimeforBus;
    }

    public void setWaitingTimeforBus(int waitingTimeforBus) {
        this.waitingTimeforBus = waitingTimeforBus;
    }

    @Override
    public String toString() {
        return "Plan{" +
                "startStation=" + startStation +
                ", endStation=" + endStation +
                ", line='" + line + '\'' +
                ", time=" + time +
                ", walkdistance=" + walkdistance +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", countofStation=" + countofStation +
                ", waitingTimeforBus=" + waitingTimeforBus +
                '}';
    }
}
