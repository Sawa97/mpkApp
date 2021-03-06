package client_server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTwo {
    public static ServerTwo SERVERTWO_INSTANCE = new ServerTwo();
    private final String SERVER_NAME = "SERVER ONE";

    public static void main(String[] args) throws Exception {
       new ServerTwo();
        ServerSocket s1 = new ServerSocket(6002);

        while (true) {
            Socket clientSocket = null;

            try {
                clientSocket = s1.accept();


                System.out.println("A new client is connected : " + clientSocket);

                DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());

                System.out.println("Assigning new thread for this client");

                Thread clientThread = new ServerHandler(clientSocket, dis, dos,SERVERTWO_INSTANCE.SERVER_NAME);

                clientThread.start();

            } catch (Exception e) {
                clientSocket.close();
                e.printStackTrace();
            }

        }
    }

    private ServerTwo() {
    }


}