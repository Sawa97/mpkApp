package client_server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class ServerOne {
    private static HashMap<Integer, Socket> sockets = new HashMap<Integer, Socket>();
    public static ServerOne SERVERONE_INSTANCE = new ServerOne();
    private final String SERVER_NAME = "SERVER ONE";

    public static void main(String[] args) throws Exception{
        new ServerOne();
        ServerSocket s1 = new ServerSocket(6001);


        while (true)
        {
            Socket clientSocket = null;

            try
            {
                clientSocket = s1.accept();

                SERVERONE_INSTANCE.getSockets().put(clientSocket.getPort(),clientSocket);

                System.out.println("A new client is connected : " + clientSocket);

                DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());

                System.out.println("Assigning new thread for this client");

                Thread clientThread = new ServerHandler(clientSocket,dis,dos,SERVERONE_INSTANCE.SERVER_NAME);

                clientThread.start();

            }
            catch (Exception e){
                clientSocket.close();
                e.printStackTrace();
            }
        }
    }

    private ServerOne() {
    }

    public HashMap<Integer, Socket> getSockets() {
        return sockets;
    }

    public int getSocketsCount(){
        return sockets.size();
    }

    public static void setSockets(HashMap<Integer, Socket> sockets) {
        ServerOne.sockets = sockets;
    }
}