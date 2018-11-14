package client_server;

import client_server.data.ClientData;
import data.Line;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class Client extends ClientData {
    private InetAddress ip;
    private Socket s;
    public static Client CLIENT_INSTANCE = new Client();
    private DataInputStream dis;
    private DataOutputStream dos;
    private String request = null;
    private String name;
    private ObjectInputStream inFromServer;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void startConnection(){
        try {
            //tworzenie nowego połączenia
            ip = InetAddress.getByName("localhost");
            s = new Socket(ip, 5056);

            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());


            //przekierowanie na odpowiedni serwer
            String received = dis.readUTF();
            changeServer(received);




        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void makeRequest() throws Exception {

        if(this.request!=null&&!this.request.equals("")) {
            dos.writeUTF(this.request);
            switch(request){
                case "GETLINES":{
                    Object object =  inFromServer.readObject();
                    CLIENT_INSTANCE.setAllLines((ArrayList<Line>) object);
                    break;
                }
                default:{
                    break;
                }
            }

            System.out.println(CLIENT_INSTANCE.getAllLines().get(0).getLineNumber());
            request="";
        }
    }

    public boolean changeServer(String received) throws Exception {

        if (received.contains("reconnect")) {
            s.close();
            dis.close();
            dos.close();
            int port = Integer.parseInt(received.replace("reconnect", ""));


            s = new Socket(ip, port);
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
            inFromServer = new ObjectInputStream(s.getInputStream());
            return true;
        }
        return false;

    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) throws Exception {
        try {
            this.request = request;
            makeRequest();
        }catch (IOException e){
            e.getMessage();
        }
    }
}