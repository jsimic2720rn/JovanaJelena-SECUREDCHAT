import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.net.Socket;

@Getter
@Setter
public class MySocket {
    private Socket socket;
    private String username;
    private BufferedReader socket_in;
    private PrintWriter socket_out;

    public MySocket(Socket socket) {
        this.socket = socket;
        this.username = "";
        try {
            socket_in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            socket_out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
