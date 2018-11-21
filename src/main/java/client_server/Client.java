package client_server;

import client_server.data.ClientData;
import controllers.data.BusStopShedule;
import data.BusStop;
import data.Line;

import java.io.*;
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
    private ObjectInputStream inFromServer;
    private ObjectOutputStream outToServer;

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
        Object object;

        if(this.request!=null&&!this.request.equals("")) {
            dos.writeUTF(this.request);
            switch(request){
                case "GETLINES":{
                    object =  inFromServer.readObject();
                    CLIENT_INSTANCE.setAllLines((ArrayList<Line>) object);
                    break;
                }

                case "GETBUSSTOPS":{
                    object = inFromServer.readObject();
                    CLIENT_INSTANCE.setBusStops((ArrayList<BusStop>) object);
                    break;
                }

                case "GETSHEDULE":{
                    outToServer.writeObject(CLIENT_INSTANCE.getSearchedBusStop()); //wysłanie wybranego przystanku
                    object = inFromServer.readObject();
                    CLIENT_INSTANCE.setListBusStopShedule((ArrayList<BusStopShedule>) object);
                    break;
                }
                default:{
                    break;
                }
            }


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
            outToServer = new ObjectOutputStream(s.getOutputStream());
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

    public Socket getS() {
        return s;
    }

    public void setS(Socket s) {
        this.s = s;
    }
}