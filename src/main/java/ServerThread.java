import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread implements Runnable{
    private MySocket mySocket;

    public ServerThread (MySocket mySocket){
        this.mySocket = mySocket;
    }

    @Override
    public void run(){
        try {
//            StringBuilder sb = new StringBuilder();
            // ulaz-izlaz

            String message; // ovo ce da budu poruke od klijenta

            mySocket.getSocket_out().println("Unesi username");
            String username = mySocket.getSocket_in().readLine();
            mySocket.setUsername(username);
            System.out.println("KLijent username: " + username);

//svaka poruka mora da bude
//<<primalac>> : <<poruka>>
            while(true) {
                message = mySocket.getSocket_in().readLine();
                if(message.equalsIgnoreCase("exit")) {
                    mySocket.getSocket_out().close();
                    mySocket.getSocket_in().close();
                    mySocket.getSocket().close();
                    break;
                }
                String[] messages = message.split(":");
                if(messages.length < 2) {
                    continue;
                }
                for(MySocket mySockett : ServerMain.sockets) {
                    if(mySockett.getUsername().equalsIgnoreCase(messages[0])) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(username + ":");
                        for(int i = 1; i < messages.length; i++) {
                            sb.append(messages[i]);
                        }
                        mySockett.getSocket_out().println(sb.toString());
                        break;
                    }
                }
                System.out.println("Klijent: " + username + ", message: " + message);
            }
            mySocket.getSocket_out().close();
            mySocket.getSocket_in().close();
            mySocket.getSocket().close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
