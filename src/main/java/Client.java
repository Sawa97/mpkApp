import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

// Client class
public class Client {
    private Scanner scn;
    private InetAddress ip;
    private Socket s;
    private DataInputStream dis;
    private DataOutputStream dos;

    public Client() {
        try {
            //tworzenie nowego połączenia
            scn = new Scanner(System.in);
            ip = InetAddress.getByName("localhost");
            s = new Socket(ip, 5056);

            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());


            //przekierowanie na odpowiedni serwer
            String received = dis.readUTF();
            changeServer(received);

            //obsługa zapytań i ich odbieranie
            while (true) {

                received = dis.readUTF();// tu leci info na serwer
                System.out.println(received);
                // If client sends exit,close this connection
                // and then break from the while loop


            }

            // closing resources

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client();
    }

    private void makeRequest(String req) throws IOException {

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
}