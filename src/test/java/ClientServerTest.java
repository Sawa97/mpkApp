import client_server.Client;
import client_server.ServerOne;
import client_server.ServerSheduler;
import org.junit.Before;
import org.junit.Test;

import java.net.Socket;

import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
           final Socket testSocket = mock(Socket.class);
          ServerOne.SERVERONE_INSTANCE.getSockets().put(testSocket.getPort(),testSocket);
           assertEquals(ServerSheduler.reconnect(),"reconnect6002");
       }
   }

   public static class ClientTest{

       public Client client;
       @Before
       public void setUp(){
            client = new Client();


       }


       @Test
       public void changeServerTest() throws Exception{
           String[] args = {};
           new Thread(){
               @Override
               public void run() {
                   try {
                       ServerOne.main(args);
                   }catch (Exception e){

                   }
               }
           }.start();
           new Thread(){
               @Override
               public void run() {
                   try {
                       ServerSheduler.main(args);
                   }catch (Exception e){

                   }
               }
           }.start();
           client.startConnection();
           assertTrue(client.changeServer("reconnect6001"));

       }

    }




}
