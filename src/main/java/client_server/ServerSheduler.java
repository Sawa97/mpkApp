package client_server;

import org.joda.time.DateTime;
import org.joda.time.Seconds;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ServerSheduler implements Serializable {

    public static ServerSheduler SERVERSHEDULER = new ServerSheduler();
    private final String SERVER_NAME = "SERVER SHEDULER";
   private static Map<Integer, DateTime> serverList;
   private long time = 0;


    public ServerSheduler() {
        serverList = new HashMap<>();
        serverList.put(6001,new DateTime());
        serverList.put(6002,new DateTime());
    }

    public static void main(String[] args) throws IOException {
        // server is listening on port 5056
        ServerSocket ss = new ServerSocket(5056);

        // running infinite loop for getting
        // client request
        while (true) {


            Socket s = null;

            try {
                // socket object to receive incoming client requests
                s = ss.accept();

                System.out.println("A new client is connected : " + s);

                // obtaining input and out streams
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                ObjectOutputStream outToServer = new ObjectOutputStream(s.getOutputStream());
                ObjectInputStream inFromServer = new ObjectInputStream(s.getInputStream());

                System.out.println("Assigning new thread for this client");


                TaskInformation taskInformation;
                Object object;
                object = inFromServer.readObject();
                taskInformation = (TaskInformation) object;
                if (!taskInformation.isEmpty()) {

                    SERVERSHEDULER.time= taskInformation.getSeconds();
                    DateTime dt =serverList.get(taskInformation.getServerPort()).plus(SERVERSHEDULER.time);

                    serverList.put(taskInformation.getServerPort(),dt);
                }
                dos.writeUTF(reconnect(taskInformation));


                s.close();
                dos.close();
                dis.close();
                inFromServer.close();
                outToServer.close();


            } catch (Exception e) {
                System.out.println("2");
                s.close();
                e.printStackTrace();
            }

        }
    }

    public static String reconnect(TaskInformation taskInformation){
        if(Seconds.secondsBetween(serverList.get(6002),serverList.get(6001)).getSeconds()>0){
            if(taskInformation.isEmpty()){
                DateTime dt =serverList.get(6002).plus(SERVERSHEDULER.time);
                serverList.put(6002,dt);
            }
            return "reconnect6002";

        }

        if(taskInformation.isEmpty()){
            DateTime dt =serverList.get(6001).plus(SERVERSHEDULER.time);
            serverList.put(6001,dt);
        }
        return "reconnect6001";
    }

    public static Map<Integer, DateTime> getServerList() {
        return serverList;
    }

    public static void setServerList(Map<Integer, DateTime> serverList) {
        ServerSheduler.serverList = serverList;
    }
}