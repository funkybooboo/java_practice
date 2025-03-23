import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

public class connect {
    private static boolean done = false;

    public static void main(String[] args) {
        String Host = "192.168.4.241";
        int Port = 5555;
        try {
            listenForMessages(Port);
            Scanner scan = new Scanner(System.in);
            Socket senderSocket;
            while (!done){
                System.out.print("Send: ");
                System.out.println();
                String userInput = scan.nextLine();
                senderSocket = new Socket(Host, Port);
                sendMessage(senderSocket, userInput);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void listenForMessages(int Port) {
        new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(Port);
                while (!done) {
                    Socket getSocket = serverSocket.accept();
                    String theMessage = getMessage(getSocket);
                    System.out.println("Responce: " + theMessage);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static void sendMessage(Socket socket, String sms) {
        try {
            OutputStream out = socket.getOutputStream();
            out.write(sms.getBytes(StandardCharsets.UTF_8));
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getMessage(Socket socket) {
        try {
            InputStream in = socket.getInputStream();
            byte[] bytes = new byte[65000];
            int numRead = in.read(bytes);
            String s = new String(bytes, 0, numRead);
            return s;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
