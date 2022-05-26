import lombok.Setter;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

@Setter
public class ServerMain {
    public static ArrayList<MySocket> sockets = new ArrayList<>();

    public ServerMain() throws Exception{

        ServerSocket server_socket = new ServerSocket(2022);
        //System.out.println("Server slusa na portu 2022.");

        while(true){
            Socket socket = server_socket.accept();
            MySocket mySocket = new MySocket(socket);
            ServerThread server_thread = new ServerThread(mySocket);
            Thread thread = new Thread(server_thread); //za citanje
            sockets.add(mySocket);
            thread.start();
        }

    }

    public static void main(String[] args) {
        try {
            new ServerMain();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
