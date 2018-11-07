package client_server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    private InetAddress ip;
    private Socket s;
    private DataInputStream dis;
    private DataOutputStream dos;
    private String request = null;


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



    public String makeRequest() throws IOException {

        if(this.request!=null&&!this.request.equals("")) {
            dos.writeUTF(this.request);
            String data = dis.readUTF();
            request="";
            return data;
        }
        return null;
    }

    private void changeServer(String received) throws Exception {

        if (received.contains("reconnect")) {
            s.close();
            dis.close();
            dos.close();
            int port = Integer.parseInt(received.replace("reconnect", ""));


            s = new Socket(ip, port);
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
        }

    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        try {
            this.request = request;
            makeRequest();
        }catch (IOException e){
            e.getMessage();
        }
    }
}