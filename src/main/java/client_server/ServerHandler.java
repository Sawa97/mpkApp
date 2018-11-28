package client_server;

import client_server.data.DijkstraAlgorithm;
import client_server.data.Plan;
import controllers.data.BusStopShedule;
import data.BusStop;
import data.Connection;
import data.Graph;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.joda.time.DateTime;
import org.joda.time.Minutes;

import javax.persistence.criteria.CriteriaQuery;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

class ServerHandler extends Thread {
    private final DataInputStream dis;
    private final DataOutputStream dos;
    private final Socket s;
    private final String serverName;
    private String req;
    private static SessionFactory factory;
    private static Session session;
    private ObjectOutputStream outToClient;
    private ObjectInputStream inFromClient;
    private BusStop searchedBusStop;
    private Plan plan;
    private Graph graph;


    // Constructor
    public ServerHandler(Socket s, DataInputStream dis, DataOutputStream dos, String serverName) throws Exception {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
        this.serverName = serverName;
        this.outToClient = new ObjectOutputStream(s.getOutputStream());
        this.inFromClient = new ObjectInputStream(s.getInputStream());
    }

    @Override
    public void run() {
        factory = new Configuration().configure().buildSessionFactory();
        while (true) {
            try {

                requestHandler();


            } catch (Exception e) {
                e.printStackTrace();
                if (serverName.equals("SERVER_ONE")) {
                    ServerOne.SERVERONE_INSTANCE.getSockets().remove(s.getPort());
                } else ServerTwo.SERVERTWO_INSTANCE.getSockets().remove(s.getPort());
                return;
            }
        }

    }

    private void requestHandler() throws Exception {

        this.req = dis.readUTF();
        requestManage();
        req = null;
    }

    private void requestManage() throws Exception {
        switch (req) {
            case "GETGRAPH": {
                session = factory.openSession();
                CriteriaQuery<Graph> criteriaQuery = session.getCriteriaBuilder().createQuery(Graph.class);
                criteriaQuery.from(Graph.class);
                List<Graph> graph= session.createQuery(criteriaQuery).getResultList();
                outToClient.writeObject(graph.get(0));
                session.close();

                break;
            }

            case "GETSHEDULE": {
                Object object = inFromClient.readObject();
                Client client = (Client) object;
                List<BusStopShedule> listShedule = new ArrayList<>();
                    for (Connection connection : client.getGraph().getConnections()) {
                        if (connection.getStartBusStop().getBusStopName().equals(client.getSearchedBusStop().getBusStopName())) {

                            int time = Minutes.minutesBetween(new DateTime(), connection.getDate()).getMinutes();
                            if (time <= 480 && time > 0) {
                                listShedule.add(new BusStopShedule(connection.getLine().getEndStation(), time, connection.getLine(),connection.getDate()));
                            }
                        }
                }
                outToClient.writeObject(listShedule);
                break;
            }

            case "SEARCH":{
                String actualLine="";
                Object object = inFromClient.readObject();
                Client client = (Client) object;
                DijkstraAlgorithm dj = new DijkstraAlgorithm(client);
                dj.execute(client.getStartStation());
                LinkedList<Plan> path = dj.getPath(client.getEndStation());
                List<Plan> generalPlan = new LinkedList<>();
                Plan plan = new Plan();
                int time = 0;
                int stationInRow = 1;
                int i=0;


               // outToClient.writeObject(path);
                for(Plan p : path){
                    i++;
                    if (actualLine.equals("")) {
                        actualLine = p.getLine();
                        plan = new Plan();
                        plan.setStartStation(p.getStartStation());
                        plan.setLine(p.getLine());
                        plan.setStartTime(p.getStartTime());
                        plan.setWaitingTimeforBus(p.getWaitingTimeforBus());
                        plan.setWalkdistance(p.getWalkdistance());
                        time = p.getTime();
                        stationInRow++;
                        continue;

                    }
                    else if(actualLine.equals(p.getLine())){
                        time+=p.getTime();
                        stationInRow++;
                        plan.setCountofStation(stationInRow-2);
                        plan.setEndTime(p.getEndTime());
                        plan.setTime(time);
                        plan.setEndStation(p.getEndStation());

                    }

                    if(!actualLine.equals(p.getLine())){
                        generalPlan.add(plan);
                        plan = new Plan();
                        plan.setStartStation(p.getStartStation());
                        plan.setLine(p.getLine());
                        plan.setStartTime(p.getStartTime());
                        plan.setWaitingTimeforBus(p.getWaitingTimeforBus());
                        plan.setWalkdistance(p.getWalkdistance());
                        actualLine = p.getLine();
                        stationInRow = 2;
                        time = p.getTime();
                    }

                    if(i==path.size()-1){
                        plan.setCountofStation(stationInRow-2);
                        plan.setEndTime(p.getEndTime());
                        plan.setTime(time);
                        plan.setEndStation(p.getEndStation());
                        generalPlan.add(plan);
                    }

                }
                System.out.println("--------------------");
                for(Plan p: generalPlan){
                    System.out.println(p);
                }

            }
            default: {
            }

        }
    }



//    private void searchMethod(ClientData clientData){
//
//        boolean directionDefault = true;
//        if(clientData.getActualTime()){
//           clientData.setSearchingTime(new DateTime());
//        }
//        if(clientData.getEndStation().getBusStopNumber()<clientData.getStartStation().getBusStopNumber()){
//            directionDefault = false;
//        }
//
//
//
//        if(clientData.getWayOfTravel().equals(WayOfTravel.fastest)){
//            if(clientData.getMaxCountofChange()==0){
//                for(Line lineS : clientData.getStartStation().getListOfLine()){
//                    for(Line lineE : clientData.getEndStation().getListOfLine()){
//                        if (lineE.getLineNumber().equals(lineS.getLineNumber())){
//                            if(directionDefault&&lineE.getLineNumber().contains("a")){
//                                findEarliestConnection(clientData.getStartStation(), clientData, lineE);
//                                clientData.getPlanList().add(new Plan(lineE,))
//                            }
//
//                        }
//                    }
//                }
//            }
//        }
//
//    }
//
//
//    private int findEarliestConnection(BusStop busStop, ClientData clientData, Line lineStart ){
//        int minTime=5000;
//        DateTime start;
//        DateTime end;
//        int time;
//        int relationNumber;
//
//        for(Line line : busStop.getListOfLine())
//        {
//            if(lineStart.getLineNumber().equals(line.getLineNumber())){
//            for(SingleInformation information : line.getPlan()){
//                if(Minutes.minutesBetween(clientData.getSearchingTime(),information.getDate()).getMinutes()<minTime)
//                {
//                    minTime=Minutes.minutesBetween(clientData.getSearchingTime(),information.getDate()).getMinutes();
//                    start = information.getDate();
//                    relationNumber = information.getRelationNumber();
//
//                    for(Line line2 :clientData.getEndStation().getListOfLine()){
//                        if(lineStart.getLineNumber().equals(line2.getLineNumber())){
//                            for(SingleInformation information1 : line.getPlan()){
//                                if(information1.getRelationNumber()==relationNumber){
//                                    end = information1.getDate();
//                                    time = Minutes.minutesBetween(start,information1.getDate()).getMinutes();
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        }
//
//return minTime;
//    }


}