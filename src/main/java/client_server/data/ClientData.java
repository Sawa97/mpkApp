package client_server.data;

import controllers.data.BusStopShedule;
import data.BusStop;
import data.Graph;
import data.Line;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ClientData extends SearchDataHandler implements Serializable {


    private Line searchedLine;
    private BusStop searchedBusStop;
    private List<BusStopShedule> listBusStopShedule;
    private InformationFromPanel informationFromPanel = InformationFromPanel.shedule;
    private Graph graph;
    private boolean isGraph = false;


    public ClientData() {
        listBusStopShedule = new ArrayList<>();
    }



    public List<Line> searchLines(String condition){
        List<Line> resultList = new ArrayList<>();
        for(Line line: graph.getLineList()){
            if(line.getLineNumber().startsWith(condition)){
                resultList.add(line);
            }
        }
        return resultList;
    }

    public List<BusStop> busStops(String condition){
        List<BusStop> resultList = new ArrayList<>();
        for(BusStop busStop:  graph.getBusStops()){
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
        for(Line line: graph.getLineList()){
            if(line.getLineNumber().equals(number)){
                this.searchedLine = line;
            }
        }
    }


    public BusStop getSearchedBusStop() {
        return searchedBusStop;
    }

    public void setSearchedBusStop(String busStopName) {
        for(BusStop busStop: graph.getBusStops()){
            if(busStop.getBusStopName().equals(busStopName)){
                this.searchedBusStop = busStop;
            }
        }
    }


    public List<BusStopShedule> getListBusStopShedule() {
        return listBusStopShedule;
    }

    public void setListBusStopShedule(List<BusStopShedule> listBusStopShedule) {
        this.listBusStopShedule = listBusStopShedule;
    }





    public void setStartStation(String busStopName) {
        for(BusStop busStop: graph.getBusStops()){
            if(busStop.getBusStopName().equals(busStopName)){
                this.setStartStation(busStop);
            }
        }
    }

    public boolean isGraph() {
        return isGraph;
    }

    public void setGraph(boolean graph) {
        isGraph = graph;
    }

    public void setEndStation(String busStopName) {
        for(BusStop busStop: graph.getBusStops()){
            if(busStop.getBusStopName().equals(busStopName)){
               this.setEndStation(busStop);
            }
        }
    }

    public InformationFromPanel getInformationFromPanel() {
        return informationFromPanel;
    }

    public void setInformationFromPanel(InformationFromPanel informationFromPanel) {
        this.informationFromPanel = informationFromPanel;
    }

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
        this.isGraph=true;
    }
}
