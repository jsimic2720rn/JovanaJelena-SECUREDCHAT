import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Scanner;

public class Client {
    public Client() throws Exception{

        Socket socket = new Socket("localhost", 2022);

        // ulaz-izlaz
        BufferedReader socket_in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter socket_out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

        String messageIN; // ovo ce da budu poruke od servera
        String messageOUT;
        String username;
        Scanner tastatura = new Scanner(System.in); // unos sa tastature
        //String klijent_pogadja;
        messageIN = socket_in.readLine();
        if(messageIN.equalsIgnoreCase("Unesi username")) {
            System.out.println(messageIN);
            username = tastatura.nextLine();
            socket_out.println(username);
        }

        Thread citaj = new Thread(new Runnable() {
            @Override
            public void run() {
                String content;
                while(true) {
                    try {
                        content = socket_in.readLine();
                        String[] data = content.split(":");

                        System.out.println(data[0] + ":" + RSAUtil.decrypt(data[1],RSAUtil.privateKey));
                    } catch (Exception e) {
                       // e.printStackTrace();
                    }
                }
            }
        });

        Thread pisi = new Thread(new Runnable() {
            @Override
            public void run() {
                String content;
                while(true) {
                    try {
                        content = tastatura.nextLine();

                        if(content.equalsIgnoreCase("exit")) {
                            socket_out.println("exit");
                            socket.close();
                            socket_out.close();
                            socket_in.close();
                            break;
                        }

                        String[] newcontent = content.split(":");
                        String enc = Base64.getEncoder().encodeToString(RSAUtil.encrypt(newcontent[1], RSAUtil.publicKey));
                        socket_out.println(newcontent[0] + ":" + enc);

                        //System.out.println(content);
                    } catch (Exception e) {
                        // e.printStackTrace();
                    }
                }
            }
        });

        citaj.start();
        pisi.start();

        //socket.close();
    }

    private static String xoring(String poruka) {
        String[] poruke = poruka.split(":");
        StringBuilder sb = new StringBuilder();
        char key = 'x';
        for(int i = 1; i < poruke.length; i++) {
            sb = sb.append(poruke[i]);
        }
        String enkriptovan = "";
        String plainText = sb.toString();
        for(int i = 0; i < plainText.length(); i++) {
            if(plainText.charAt(i) == ' ') {
                continue;
            }
            enkriptovan += Character.toString((char)(plainText.charAt(i) ^ key));
        }
        //System.out.println(enkriptovan);
        return poruke[0] + ": " + enkriptovan;
    }

    public static void main(String[] args) {
        try {
            new Client();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
