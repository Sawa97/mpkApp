package client_server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

class ServerHandler extends Thread
{
    private final DataInputStream dis;
    private final DataOutputStream dos;
    private final Socket s;
    private final String serverName;
    private String req;



    // Constructor
    public ServerHandler(Socket s, DataInputStream dis, DataOutputStream dos, String serverName)
    {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
        this.serverName = serverName;
    }

    @Override
    public void run()
    {
        while (true)
        {
            try {

                requestHandler();



            } catch (IOException e) {
                if(serverName.equals("SERVER_ONE")){
                    ServerOne.SERVERONE_INSTANCE.getSockets().remove(s.getPort());
                }
                else ServerTwo.SERVERTWO_INSTANCE.getSockets().remove(s.getPort());
               return;
            }
        }

    }

    private void requestHandler() throws IOException{

        this.req = dis.readUTF();
        dos.writeUTF(requestManage());
        req=null;
    }

    private String requestManage() throws IOException{
        switch (req){
            case "EXIT": {
                this.s.close();
                this.dos.close();
                this.dis.close();
                if(serverName.equals("SERVER_ONE")){
                    ServerOne.SERVERONE_INSTANCE.getSockets().remove(s.getPort());
                }
                else ServerTwo.SERVERTWO_INSTANCE.getSockets().remove(s.getPort());
                return "";
            }
            default:{
                return "";
            }

        }
    }
}