package client_server.data;

import controllers.data.BusStopShedule;
import data.BusStop;
import data.Line;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class ClientData {


    private List<Line> allLines;
    private Line searchedLine;
    private List<BusStop> busStops;
    private BusStop searchedBusStop;
    private List<BusStopShedule> listBusStopShedule;
    private int maxCountofChange;
    private DateTime searchingTime;
    private Boolean actualTime = true;
    private WayOfTravel wayOfTravel = WayOfTravel.fastest;
    private BusStop startStation;
    private BusStop endStation;
    private InformationFromPanel informationFromPanel = InformationFromPanel.shedule;


    public ClientData() {
        allLines = new ArrayList<>();
        busStops = new ArrayList<>();
        listBusStopShedule = new ArrayList<>();
    }

    public List<Line> getAllLines() {
        return allLines;
    }

    public void setAllLines(List<Line> allLines) {
        this.allLines = allLines;
    }

    public List<Line> searchLines(String condition){
        List<Line> resultList = new ArrayList<>();
        for(Line line: allLines){
            if(line.getLineNumber().startsWith(condition)){
                resultList.add(line);
            }
        }
        return resultList;
    }

    public List<BusStop> busStops(String condition){
        List<BusStop> resultList = new ArrayList<>();
        for(BusStop busStop: busStops){
            if(busStop.getBusStopName().toLowerCase().startsWith(condition.toLowerCase())){
                resultList.add(busStop);
            }
        }
        return resultList;
    }

    public Line getSearchedLine() {
        return searchedLine;
    }

    public void setSearchedLine(String number) {
        for(Line line: allLines){
            if(line.getLineNumber().equals(number)){
                this.searchedLine = line;
            }
        }
    }


    public List<BusStop> getBusStops() {
        return busStops;
    }

    public BusStop getSearchedBusStop() {
        return searchedBusStop;
    }

    public void setSearchedBusStop(String busStopName) {
        for(BusStop busStop: busStops){
            if(busStop.getBusStopName().equals(busStopName)){
                this.searchedBusStop = busStop;
            }
        }
    }

    public void setBusStops(List<BusStop> busStops) {
        this.busStops = busStops;
    }

    public List<BusStopShedule> getListBusStopShedule() {
        return listBusStopShedule;
    }

    public void setListBusStopShedule(List<BusStopShedule> listBusStopShedule) {
        this.listBusStopShedule = listBusStopShedule;
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

    public BusStop getStartStation() {
        return startStation;
    }

    public void setStartStation(String busStopName) {
        for(BusStop busStop: busStops){
            if(busStop.getBusStopName().equals(busStopName)){
                this.startStation = busStop;
            }
        }
    }

    public BusStop getEndStation() {
        return endStation;
    }

    public void setEndStation(String busStopName) {
        for(BusStop busStop: busStops){
            if(busStop.getBusStopName().equals(busStopName)){
                this.endStation = busStop;
            }
        }
    }

    public InformationFromPanel getInformationFromPanel() {
        return informationFromPanel;
    }

    public void setInformationFromPanel(InformationFromPanel informationFromPanel) {
        this.informationFromPanel = informationFromPanel;
    }

    public void setStartStation(BusStop startStation) {
        this.startStation = startStation;
    }

    public void setEndStation(BusStop endStation) {
        this.endStation = endStation;
    }
}
