package client_server;

import data.Line;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.criteria.CriteriaQuery;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

class ServerHandler extends Thread
{
    private final DataInputStream dis;
    private final DataOutputStream dos;
    private final Socket s;
    private final String serverName;
    private String req;
    private static SessionFactory factory;
    private static Session session;
    private ObjectOutputStream outToClient;



    // Constructor
    public ServerHandler(Socket s, DataInputStream dis, DataOutputStream dos, String serverName) throws Exception
    {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
        this.serverName = serverName;
        this.outToClient = new ObjectOutputStream(s.getOutputStream());
    }

    @Override
    public void run()
    {
        factory = new Configuration().configure().buildSessionFactory();
        session = factory.openSession() ;
        while (true)
        {
            try {

                requestHandler();



            } catch (IOException e) {
                if(serverName.equals("SERVER_ONE")){
                    ServerOne.SERVERONE_INSTANCE.getSockets().remove(s.getPort());
                }
                else ServerTwo.SERVERTWO_INSTANCE.getSockets().remove(s.getPort());
                session.close();
                factory.close();
               return;
            }
        }

    }

    private void requestHandler() throws IOException{

        this.req = dis.readUTF();
        requestManage();
        req=null;
    }

    private String requestManage() throws IOException{
        switch (req){
            case "EXIT": {

                return "";
            }
            case "GETLINES":{
                CriteriaQuery<Line> criteriaQuery = session.getCriteriaBuilder().createQuery(Line.class);
                criteriaQuery.from(Line.class);
                List<Line> list;
                list = session.createQuery(criteriaQuery).getResultList();
                outToClient.writeObject(list);


            }
            default:{
                return "";
            }

        }
    }



//    private String getLIne(){
//        session.beginTransaction();
//        session.createQuery("")
//
//
//
//
//    }
}