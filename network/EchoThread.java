import java.io.*;
import java.net.*;

public class EchoThread extends Thread {
    Socket serverSocket = null;

    public EchoThread(Socket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void run() {
        try(
            PrintWriter socketOut = new PrintWriter(serverSocket.getOutputStream(), true);
            BufferedReader socketIn = new BufferedReader(
                                        new InputStreamReader(serverSocket.getInputStream()));
        ) {
            String ret = socketIn.readLine();
            socketOut.println(ret);
        }
        catch(IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
