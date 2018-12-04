import client_server.*;
import client_server.data.WayOfTravel;
import data.BusStop;
import data.Connection;
import data.Graph;
import data.Line;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

public class ClientServerTest {

   public static class ServerShedulerTest{
       public ServerSheduler serverSheduler;

       @Before
       public void setUp(){
           serverSheduler = new ServerSheduler();


       }


       @Test
       public void recconectTest() throws Exception {
           DateTime dt = ServerSheduler.getServerList().get(6001);
           ServerSheduler.getServerList().put(6001,dt.plus(1000));
           TaskInformation taskInformation = new TaskInformation(6001,5);


           final Socket testSocket = mock(Socket.class);
           assertEquals("reconnect6002",ServerSheduler.reconnect(taskInformation));
      }
   }

   public static class ClientTest{

       public Client client;
       public static Graph graph;
       @BeforeClass
       public static void setDatabase(){
           createDatabase();

       }

       @AfterClass
       public static void cleanDatabase(){
           final SessionFactory factory = new Configuration().configure().buildSessionFactory();
           final Session session = factory.openSession() ;
           session.beginTransaction();
           session.delete(graph);
           session.getTransaction().commit();
           session.close();
           factory.close();
       }

       @Before
       public void setUp(){
            client = new Client();

           String[] args = {};
           new Thread(){
               @Override
               public void run() {
                   try {
                     //  ServerOne.setFactory(new Configuration().configure().buildSessionFactory());
                       ServerOne.main(args);
                   }catch (Exception e){
                    //
                   }
               }
           }.start();
           new Thread(){
               @Override
               public void run() {
                   try {
                       ServerSheduler.main(args);
                   }catch (Exception e){
                        //
                   }
               }
           }.start();
           new Thread(){
               @Override
               public void run() {
                   try {
                     //  ServerTwo.setFactory(new Configuration().configure().buildSessionFactory());
                       ServerTwo.main(args);
                   }catch (Exception e){
                        //
                   }
               }
           }.start();


       }


       @Test
       public void changeServerTest() throws Exception{
           Client.CLIENT_INSTANCE.startConnection();
           assertTrue(Client.CLIENT_INSTANCE.changeServer("reconnect6001"));

       }

       @Test
       public void makeRequestGETGRAPH() throws Exception{
           Client.CLIENT_INSTANCE.setRequest("GETGRAPH");
           assertNotNull(Client.CLIENT_INSTANCE.getGraph());
       }

       @Test
       public void makeRequestGETSHEDULE() throws Exception{
           Client.CLIENT_INSTANCE.setRequest("GETGRAPH");


           Client.CLIENT_INSTANCE.setSearchedBusStop("Politechnika");
           Client.CLIENT_INSTANCE.setRequest("GETSHEDULE");
           assertTrue(Client.CLIENT_INSTANCE.getListBusStopShedule().size()>0);

       }

       @Test
       public void makeRequestSEARCH() throws Exception{
           Client.CLIENT_INSTANCE.setRequest("GETGRAPH");

           Client.CLIENT_INSTANCE.setStartStation("Dworzec Główny");
           Client.CLIENT_INSTANCE.setEndStation("Prądnik Czerwony");
           Client.CLIENT_INSTANCE.setSearchingTime(new DateTime());
           Client.CLIENT_INSTANCE.setActualTime(true);
           Client.CLIENT_INSTANCE.setWayOfTravel(WayOfTravel.fastest);
           Client.CLIENT_INSTANCE.setMaxCountofChange(3);

           Client.CLIENT_INSTANCE.setRequest("SEARCH");
           assertTrue(Client.CLIENT_INSTANCE.getPlanList().size()>0);




       }


       private static void createDatabase(){

           DateTime dt1 = new DateTime().plusMinutes(10);
           DateTime dt2 = new DateTime().plusMinutes(8);
           DateTime dt3 = new DateTime().plusMinutes(9);

           BusStop a1 = new BusStop(11,"Dworzec Główny");
           BusStop a2 = new BusStop(12,"Politechnika"); //b2
           BusStop a3 = new BusStop(13,"Biskupa");
           BusStop a4 = new BusStop(14,"Opolska Estakada");
           BusStop a5 = new BusStop(15,"Rondo Młyńskie");
           BusStop a6 = new BusStop(16,"Imbramowska");
           BusStop a7 = new BusStop(17,"Okulickiego"); //c4
           BusStop a8 = new BusStop(18,"Prądnik Czerwony");
           BusStop b1 = new BusStop(21,"Czerwone Maki");
           BusStop b3 = new BusStop(23,"Rondo Mateczne");
           BusStop b4 = new BusStop(24,"Słowackiego");
           BusStop b5 = new BusStop(25,"Nowohucka");//c1
           BusStop c2 = new BusStop(32,"Ruczaj");
           BusStop c3 = new BusStop(33,"Nowe Osiedle");
           BusStop c5 = new BusStop(35,"Borek Fałęcki");//d1
           Line line129a = new Line("129a","Dworzec Główny","Prądnik Czerwony");
           Line line129b = new Line("129b","Prądnik Czerwony","Dworzec Główny");
           Line line139a = new Line("139a","Czerwone Maki","Nowohucka");
           Line line139b = new Line("139b","Nowohucka","Czerwone Maki");
           Line line150a = new Line("150a","Nowohucka","Borek Fałęcki");
           Line line150b = new Line("150b","Borek Fałęcki","Nowohucka");
           a1.getListOfLine().add(line129a);
           b1.getListOfLine().add(line139a);
           a2.getListOfLine().add(line129a);
           a2.getListOfLine().add(line139a);
           a3.getListOfLine().add(line129a);
           a4.getListOfLine().add(line129a);
           a5.getListOfLine().add(line129a);
           a6.getListOfLine().add(line129a);
           b3.getListOfLine().add(line139a);
           b4.getListOfLine().add(line139a);
           b5.getListOfLine().add(line139a);
           a7.getListOfLine().add(line129a);
           b5.getListOfLine().add(line150a);
           c2.getListOfLine().add(line150a);
           c3.getListOfLine().add(line150a);
           a7.getListOfLine().add(line150a);
           c5.getListOfLine().add(line150a);
           a8.getListOfLine().add(line129a);


           a1.getListOfLine().add(line129b);
           b1.getListOfLine().add(line139b);
           a2.getListOfLine().add(line129b);
           a2.getListOfLine().add(line139b);
           a3.getListOfLine().add(line129b);
           a4.getListOfLine().add(line129b);
           a5.getListOfLine().add(line129b);
           a6.getListOfLine().add(line129b);
           b3.getListOfLine().add(line139b);
           b4.getListOfLine().add(line139b);
           b5.getListOfLine().add(line139b);
           a7.getListOfLine().add(line129b);
           b5.getListOfLine().add(line150b);
           c2.getListOfLine().add(line150b);
           c3.getListOfLine().add(line150b);
           a7.getListOfLine().add(line150b);
           c5.getListOfLine().add(line150b);
           a8.getListOfLine().add(line129b);



           //dodać tutaj connectiony


           Connection connection1 = new Connection(a1,a2,3,line129a,dt1);
           Connection connection2 = new Connection(a2,a3,2,line129a,dt1.plusMinutes(3));
           Connection connection3 = new Connection(a3,a4,4,line129a,dt1.plusMinutes(5));
           Connection connection4 = new Connection(a4,a5,2,line129a,dt1.plusMinutes(9));
           Connection connection5 = new Connection(a5,a6,3,line129a,dt1.plusMinutes(11));
           Connection connection6 = new Connection(a6,a7,2,line129a,dt1.plusMinutes(14));
           Connection connection7 = new Connection(a7,a8,1,line129a,dt1.plusMinutes(16));
           Connection connection8 = new Connection(b1,a2,2,line139a,dt2);
           Connection connection9 = new Connection(a2,b3,3,line139a,dt2.plusMinutes(2));
           Connection connection10 = new Connection(b3,b4,2,line139a,dt2.plusMinutes(5));
           Connection connection11 = new Connection(b4,b5,3,line139a,dt2.plusMinutes(7));
           Connection connection12 = new Connection(b5,c2,3,line150a,dt3.plusMinutes(9));
           Connection connection13 = new Connection(c2,c3,3,line150a,dt3.plusMinutes(12));
           Connection connection14 = new Connection(c3,a7,2,line150a,dt3.plusMinutes(15));
           Connection connection15 = new Connection(a7,c5,2,line150a,dt3.plusMinutes(17));

           //to samo w drugą stronę


           Connection connection16 = new Connection(a8,a7,3,line129b,dt1);
           Connection connection17 = new Connection(a7,a6,2,line129b,dt1.plusMinutes(3));
           Connection connection18 = new Connection(a6,a5,4,line129b,dt1.plusMinutes(5));
           Connection connection19 = new Connection(a5,a4,2,line129b,dt1.plusMinutes(9));
           Connection connection20 = new Connection(a4,a3,3,line129b,dt1.plusMinutes(11));
           Connection connection21 = new Connection(a3,a2,2,line129b,dt1.plusMinutes(14));
           Connection connection22 = new Connection(a2,a1,1,line129b,dt1.plusMinutes(16));
           Connection connection23 = new Connection(b5,b4,2,line139b,dt2);
           Connection connection24 = new Connection(b4,b3,3,line139b,dt2.plusMinutes(2));
           Connection connection25 = new Connection(b3,a2,2,line139b,dt2.plusMinutes(5));
           Connection connection26 = new Connection(a2,b1,3,line139b,dt2.plusMinutes(7));
           Connection connection27 = new Connection(c5,a7,3,line150b,dt3);
           Connection connection28 = new Connection(a7,c3,3,line150b,dt3.plusMinutes(3));
           Connection connection29 = new Connection(c3,c2,2,line150b,dt3.plusMinutes(6));
           Connection connection30 = new Connection(c2,b5,2,line150b,dt3.plusMinutes(8));

           //walk

           Connection connection31 = new Connection(a1,b1,12,true,500);
           Connection connection32 = new Connection(a1,b3,15,true,650);
           Connection connection33 = new Connection(b1,a3,17,true,800);
           Connection connection34 = new Connection(b3,a3,10,true,300);
           Connection connection35 = new Connection(b4,c2,13,true,550);
           Connection connection36 = new Connection(c3,a6,10,true,300);
           Connection connection37 = new Connection(c5,a6,12,true,500);
           Connection connection38 = new Connection(c5,a8,10,true,300);

           Connection connection39 = new Connection(b1,a1,12,true,500);
           Connection connection40 = new Connection(b3,a1,15,true,650);
           Connection connection41 = new Connection(a3,b1,17,true,800);
           Connection connection42 = new Connection(a3,b3,10,true,300);
           Connection connection43 = new Connection(c2,b4,13,true,550);
           Connection connection44 = new Connection(a6,c3,10,true,300);
           Connection connection45 = new Connection(a6,c5,12,true,500);
           Connection connection46 = new Connection(a8,c5,10,true,300);

           List<BusStop> busStops = new ArrayList<BusStop>(){
               {
                   add(a1);
                   add(a2);
                   add(a3);
                   add(a4);
                   add(a5);
                   add(a6);
                   add(a7);
                   add(a8);
                   add(b1);
                   add(b3);
                   add(b4);
                   add(b5);
                   add(c2);
                   add(c3);
                   add(c5);
               }
           };
           List<Connection> connectionList = new ArrayList<Connection>(){{
               add(connection1);
               add(connection2);
               add(connection3);
               add(connection4);
               add(connection5);
               add(connection6);
               add(connection7);
               add(connection8);
               add(connection9);
               add(connection10);
               add(connection11);
               add(connection12);
               add(connection13);
               add(connection14);
               add(connection15);
               add(connection16);
               add(connection17);
               add(connection18);
               add(connection19);
               add(connection20);
               add(connection21);
               add(connection22);
               add(connection23);
               add(connection24);
               add(connection25);
               add(connection26);
               add(connection27);
               add(connection28);
               add(connection29);
               add(connection30);
               add(connection31);
               add(connection32);
               add(connection33);
               add(connection34);
               add(connection35);
               add(connection36);
               add(connection37);
               add(connection38);
               add(connection39);
               add(connection40);
               add(connection41);
               add(connection42);
               add(connection43);
               add(connection44);
               add(connection45);
               add(connection46);

           }};

           line129a.getBusStopList().add(a1);
           line129a.getBusStopList().add(a2);
           line129a.getBusStopList().add(a3);
           line129a.getBusStopList().add(a4);
           line129a.getBusStopList().add(a5);
           line129a.getBusStopList().add(a6);
           line129a.getBusStopList().add(a7);
           line129a.getBusStopList().add(a8);

           line139a.getBusStopList().add(b1);
           line139a.getBusStopList().add(a2);
           line139a.getBusStopList().add(b3);
           line139a.getBusStopList().add(b4);
           line139a.getBusStopList().add(b5);

           line139b.getBusStopList().add(b5);
           line139b.getBusStopList().add(b4);
           line139b.getBusStopList().add(b3);
           line139b.getBusStopList().add(a2);
           line139b.getBusStopList().add(b1);

           line129b.getBusStopList().add(a8);
           line129b.getBusStopList().add(a7);
           line129b.getBusStopList().add(a6);
           line129b.getBusStopList().add(a5);
           line129b.getBusStopList().add(a4);
           line129b.getBusStopList().add(a3);
           line129b.getBusStopList().add(a2);
           line129b.getBusStopList().add(a1);

           line150a.getBusStopList().add(b5);
           line150a.getBusStopList().add(c2);
           line150a.getBusStopList().add(c3);
           line150a.getBusStopList().add(a7);
           line150a.getBusStopList().add(c5);


           line150b.getBusStopList().add(c5);
           line150b.getBusStopList().add(a7);
           line150b.getBusStopList().add(c3);
           line150b.getBusStopList().add(c2);
           line150b.getBusStopList().add(b5);

           List<Line> lineList = new ArrayList<Line>(){{
               add(line129a);
               add(line129b);
               add(line139a);
               add(line139b);
               add(line150a);
               add(line150b);
           }};





           graph = new Graph(busStops,connectionList,lineList);





           //////////////////////////////////////////////


           final SessionFactory factory = new Configuration().configure().buildSessionFactory();
           final Session session = factory.openSession() ;
           session.beginTransaction();
           session.saveOrUpdate(graph);
           session.getTransaction().commit();

           session.delete(graph);
           session.close();
           factory.close();
       }

    }




}
