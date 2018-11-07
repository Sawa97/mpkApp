import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

class ServerHandler extends Thread
{
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;


    // Constructor
    public ServerHandler(Socket s, DataInputStream dis, DataOutputStream dos)
    {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
    }

    @Override
    public void run()
    {
        Scanner scn = new Scanner(System.in);
        String received;

//        try {
//
//            String tosend = scn.nextLine();
//            dos.writeUTF(tosend);
//            received = dis.readUTF();// tu leci info na serwer
//            System.out.println(received);
//
//
//            if (received.equals("Exit")) {
//                System.out.println("Client " + this.s + " sends exit...");
//                System.out.println("Closing this connection.");
//                this.s.close();
//                System.out.println("Connection closed");
//            }
//
//        }catch (IOException e){
//            e.getMessage();
//        }

        while (true)
        {
            try {

                System.out.println(s.getPort());
                System.out.println("df");
                String tosend = scn.nextLine();
                dos.writeUTF(tosend);
                //received = dis.readUTF();// tu leci info na serwer
                //System.out.println(received);





            } catch (IOException e) {
                e.printStackTrace();
            }
        }

//        try
//        {
//            // closing resources
//           // this.dis.close();
//          //  this.dos.close();
//
//        }catch(IOException e){
//            e.printStackTrace();
//        }
    }
}