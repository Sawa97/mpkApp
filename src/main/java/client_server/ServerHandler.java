package client_server;

import controllers.data.BusStopShedule;
import data.BusStop;
import data.Line;
import data.SingleInformation;
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
                if (serverName.equals("SERVER_ONE")) {
                    ServerOne.SERVERONE_INSTANCE.getSockets().remove(s.getPort());
                } else ServerTwo.SERVERTWO_INSTANCE.getSockets().remove(s.getPort());
                session.close();
                factory.close();
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
            case "GETLINES": {
                session = factory.openSession();
                CriteriaQuery<Line> criteriaQuery = session.getCriteriaBuilder().createQuery(Line.class);
                criteriaQuery.from(Line.class);
                List<Line> list;
                list = session.createQuery(criteriaQuery).getResultList();
                session.close();
                outToClient.writeObject(list);
                break;
            }

            case "GETBUSSTOPS": {
                session = factory.openSession();
                CriteriaQuery<BusStop> criteriaQuery = session.getCriteriaBuilder().createQuery(BusStop.class);
                criteriaQuery.from(BusStop.class);
                List<BusStop> list;
                list = session.createQuery(criteriaQuery).getResultList();
                outToClient.writeObject(list);
                session.close();
                break;


            }

            case "GETSHEDULE": {
                Object object = inFromClient.readObject();
                searchedBusStop = (BusStop) object;
                List<BusStopShedule> listShedule = new ArrayList<>();
                for (Line line : searchedBusStop.getListOfLine()) {
                    for (SingleInformation single : line.getPlan()) {
                        if (single.getBusStop().getBusStopName().equals(searchedBusStop.getBusStopName())) {

                            int time = Minutes.minutesBetween(new DateTime(), single.getDate()).getMinutes();
                            if (time <= 480 && time > 0) {
                                listShedule.add(new BusStopShedule(line.getEndStation(), time, line, single.getDate()));
                            }
                        }
                    }
                }
                outToClient.writeObject(listShedule);
                break;
            }
            default: {
            }

        }
    }



}