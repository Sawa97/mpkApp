package client_server.data;

import data.BusStop;
import data.Line;

import java.util.ArrayList;
import java.util.List;

public class ClientData {

    private List<Line> allLines;
    private Line searchedLine;
    private List<BusStop> busStops;
    private BusStop searchedBusStop;

    public ClientData() {
        allLines = new ArrayList<>();
        busStops = new ArrayList<>();
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
}
