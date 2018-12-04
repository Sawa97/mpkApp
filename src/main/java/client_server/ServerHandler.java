package client_server;

import client_server.data.DijkstraAlgorithm;
import client_server.data.Plan;
import controllers.data.BusStopShedule;
import data.Connection;
import data.Graph;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.joda.time.DateTime;
import org.joda.time.Minutes;

import javax.persistence.criteria.CriteriaQuery;
import java.io.*;
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


    public ServerHandler(Socket s, DataInputStream dis, DataOutputStream dos, String serverName) throws Exception {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
        this.serverName = serverName;
        this.outToClient = new ObjectOutputStream(s.getOutputStream());
        this.inFromClient = new ObjectInputStream(s.getInputStream());

    }

    @Override
    public void run()  {
        factory = new Configuration().configure().buildSessionFactory();
        while (true) {
            try {

                requestHandler();


            } catch (Exception e) {
                try {
                    s.close();
                    dis.close();
                    dos.close();
                    outToClient.close();
                    inFromClient.close();
                    return;
                }catch (IOException ex){
                }
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
                List<Graph> graph = session.createQuery(criteriaQuery).getResultList();
                outToClient.writeObject(graph.get(0));
                session.close();

                break;
            }

            case "GETSHEDULE": {
                Object object = inFromClient.readObject();
                Client client = (Client) object;
                List<BusStopShedule> listShedule = new ArrayList<>();
                for (Connection connection : client.getGraph().getConnections()) {
                    if (!connection.isWalk()) {

                        if (connection.getStartBusStop().getBusStopName().equals(client.getSearchedBusStop().getBusStopName())) {
                            int time = Minutes.minutesBetween(new DateTime(), connection.getDate()).getMinutes();
                            if (time <= 480 && time > 0) {
                                listShedule.add(new BusStopShedule(connection.getLine().getEndStation(), time, connection.getLine(), connection.getDate()));
                            }
                        }
                    }
                }
                outToClient.writeObject(listShedule);
                break;
            }

            case "SEARCH": {
                String actualLine = "";
                Object object = inFromClient.readObject();
                Client client = (Client) object;
                DijkstraAlgorithm dj = new DijkstraAlgorithm(client);
                dj.execute(client.getStartStation());
                LinkedList<Plan> path = dj.getPath(client.getEndStation());
                List<Plan> generalPlan = new LinkedList<>();
                Plan plan = new Plan();
                int time = 0;
                int stationInRow = 1;
                int i = 0;
                boolean onlyWalk = true;


                if (path != null) {
                    for (Plan p : path) {
                        System.out.println(p);
                        if(p.getWalkdistance()==0){
                            onlyWalk=false;
                        }
                        i++;
                        if (p.getWalkdistance() > 0) {
                            if(i>1&&plan.getWalkdistance()==0){
                                generalPlan.add(plan);
                            }
                            plan = new Plan();
                            plan.setStartStation(p.getStartStation());
                            plan.setEndStation(p.getEndStation());
                            plan.setWalkdistance(p.getWalkdistance());
                            plan.setTime(p.getTime());
                            generalPlan.add(plan);

                            continue;
                        }
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
                            System.out.println(path.size());
                            System.out.println(i);

                        } else if (actualLine.equals(p.getLine())) {
                            time += p.getTime();
                            stationInRow++;
                            plan.setCountofStation(stationInRow - 2);
                            plan.setEndTime(p.getEndTime());
                            plan.setTime(time);
                            plan.setEndStation(p.getEndStation());

                        }

                        if (!actualLine.equals(p.getLine())) {
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

                        if (i == path.size()) {
                            plan.setCountofStation(stationInRow - 2);
                            plan.setEndTime(p.getEndTime());
                            plan.setTime(time);
                            plan.setEndStation(p.getEndStation());
                            generalPlan.add(plan);
                        }

                    }
                    System.out.println("--------------------");
                    for (Plan p : generalPlan) {
                        System.out.println(p);
                    }
                }
                if(onlyWalk){
                    generalPlan = new LinkedList<>();
                }
                outToClient.writeObject(generalPlan);

            }
            default: {
            }

        }
    }

    public static SessionFactory getFactory() {
        return factory;
    }

    public static void setFactory(SessionFactory factory) {
        ServerHandler.factory = factory;
    }

    public static Session getSession() {
        return session;
    }

    public static void setSession(Session session) {
        ServerHandler.session = session;
    }
}