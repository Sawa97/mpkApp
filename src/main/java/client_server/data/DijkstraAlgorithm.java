package client_server.data;

import client_server.Client;
import data.*;
import org.joda.time.DateTime;
import org.joda.time.Minutes;

import java.util.*;

public class DijkstraAlgorithm {
    private final List<BusStop> nodes;
    private final List<Connection> edges;
    private Set<BusStop> settledNodes;
    private Set<BusStop> unSettledNodes;
    private Map<BusStop, Plan> predecessors;
    private Map<BusStop, Integer> distance;
    private int changes;
    private String lastLineNumber;
    private String actualLineNumber;
    private Client clientData;
    private Plan plan = new Plan();
    private int minTime = Integer.MAX_VALUE;





    public DijkstraAlgorithm(Client clientData) {
        // create a copy of the array so that we can operate on this array
        this.nodes = new ArrayList<BusStop>(clientData.getGraph().getBusStops());
        this.edges = new ArrayList<Connection>(clientData.getGraph().getConnections());
        this.clientData = clientData;

    }

    public void execute(BusStop source) {
        changes=0;
        settledNodes = new HashSet<BusStop>();
        unSettledNodes = new HashSet<BusStop>();
        distance = new HashMap<BusStop, Integer>();
        predecessors = new HashMap<BusStop, Plan>();
        distance.put(source, 0);
        unSettledNodes.add(source);
        while (unSettledNodes.size() > 0) {
            minTime = Integer.MAX_VALUE;
            BusStop node = getMinimum(unSettledNodes);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistances(node);
        }
    }

    private void findMinimalDistances(BusStop node) {
        List<BusStop> adjacentNodes = getNeighbors(node);

        for (BusStop target : adjacentNodes) {
            plan = new Plan();


                if (getShortestDistance(target) > getShortestDistance(node)
                        + getDistance(node, target)) {



                    distance.put(target, getShortestDistance(node)
                            + getDistance(node, target));
                    plan.setStartStation(node);
                    plan.setEndStation(target);
                    predecessors.put(target, plan);
                    unSettledNodes.add(target);
                }
            }



    }


    public int findWaitingTimeForBus(BusStop node, Connection edge){

        DateTime dt = clientData.getSearchingTime().plusMinutes(getShortestDistance(node));
        if(!actualLineNumber.equals(lastLineNumber)||!edge.isWalk()) {
            int minutes;
                                minutes = Minutes.minutesBetween(dt, edge.getDate()).getMinutes();
                                if (minutes <= minTime && minutes >= 0) {
                                    minTime = minutes;
                                    plan.setStartTime(edge.getDate());
                                    return minTime;
                                }
            plan.setStartTime(edge.getDate());
            return 200;
        }
        else {
            return 0;
        }
    }

    private int getDistance(BusStop node, BusStop target) {
        int addTime = 0;
        for (Connection edge : edges) {

            if (edge.getStartBusStop().equals(node)
                    && edge.getEndBusStop().equals(target)) {
                if(edge.isWalk()){
                    plan.setWalkdistance(edge.getDistance());
                    plan.setTime(edge.getTime());
                    if (clientData.getWayOfTravel().equals(WayOfTravel.minDistance)){
                        return 1000;
                    }
                    return edge.getTime();
                }
                if(lastLineNumber==null){
                    lastLineNumber = edge.getLine().getLineNumber();
                    actualLineNumber = edge.getLine().getLineNumber();
                }
                else{
                    lastLineNumber = actualLineNumber;
                    actualLineNumber = edge.getLine().getLineNumber();
                }

                plan.setLine(actualLineNumber);
                if(!lastLineNumber.equals(actualLineNumber)&&clientData.getWayOfTravel().equals(WayOfTravel.minCountOfChanges)){
                    addTime = 10;
                    if(lastLineNumber.equals(actualLineNumber)){
                        addTime =0;
                    }

                }

                                    if(!lastLineNumber.equals(actualLineNumber)&&clientData.getMaxCountofChange()==0){

                        return 1000;
                    }
                plan.setTime(edge.getTime());
                int waitingTime = findWaitingTimeForBus(node,edge);
                plan.setWaitingTimeforBus(waitingTime);
                plan.setEndTime(plan.getStartTime().plusMinutes(plan.getTime()));

                return edge.getTime()+waitingTime+addTime;
            }
        }
        throw new RuntimeException("Should not happen");
    }


    private List<BusStop> getNeighbors(BusStop node) {
        List<BusStop> neighbors = new ArrayList<BusStop>();
        for (Connection edge : edges) {
            if (edge.getStartBusStop().equals(node)
                    && !isSettled(edge.getEndBusStop())) {
                neighbors.add(edge.getEndBusStop());
            }
        }
        return neighbors;
    }

    private BusStop getMinimum(Set<BusStop> vertexes) {   //check
        BusStop minimum = null;
        for (BusStop vertex : vertexes) {
            if (minimum == null) {
                minimum = vertex;
            } else {
                if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
                    minimum = vertex;
                }
            }
        }
        return minimum;
    }

    private boolean isSettled(BusStop vertex) {
        return settledNodes.contains(vertex);
    }

    private int getShortestDistance(BusStop destination) {
        Integer d = distance.get(destination);
        if (d == null) {
            return Integer.MAX_VALUE;
        } else {
            return d;
        }
    }

    /*
     * This method returns the path from the source to the selected target and
     * NULL if no path exists
     */
    public LinkedList<Plan> getPath(BusStop target) {
        LinkedList<Plan> path = new LinkedList<Plan>();
        BusStop step = target;
        // check if a path exists
        if (predecessors.get(step) == null) {
            return null;
        }
        while (predecessors.get(step) != null) {
            Plan plan = predecessors.get(step);
            if(plan.getWaitingTimeforBus()>=200){
                return null;
            }
            step = predecessors.get(step).getStartStation();
            path.add(plan);
        }
        // Put it into the correct order
        Collections.reverse(path);
        return path;
    }






}
