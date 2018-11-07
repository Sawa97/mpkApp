import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSheduler {


    public static void main(String[] args) throws IOException
    {
        // server is listening on port 5056
        ServerSocket ss = new ServerSocket(5056);

        // running infinite loop for getting
        // client request
        while (true)
        {
            Socket s = null;

            try
            {
                // socket object to receive incoming client requests
                s = ss.accept();

                System.out.println("A new client is connected : " + s);

                // obtaining input and out streams
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                System.out.println("Assigning new thread for this client");

                dos.writeUTF(reconnect());
                s.close();
                dos.close();
                dis.close();

            }
            catch (Exception e){
                s.close();
                e.printStackTrace();
            }
        }
    }

    private static String reconnect(){
        if(ServerOne.SERVERONE_INSTANCE.getSocketsCount()<=ServerTwo.SERVERTWO_INSTANCE.getSocketsCount()){

            return "reconnect6001";
        }
       return "reconnect6002";
    }
}
