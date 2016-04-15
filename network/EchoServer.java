import java.net.*;
import java.io.*;

public class EchoServer {
    int portNum;

    public EchoServer(int portNum) {
        this.portNum = portNum;
    }

    public void run() {
        try (
            ServerSocket echoServer = new ServerSocket(portNum);
        ) {
            while(true) {
                Socket serverSocket = echoServer.accept();

                // start a new thread and run the run() method in it 
                (new EchoThread(serverSocket)).start();
            }
        }
        catch(IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void main(String args[]) {
        EchoServer echoServer = new EchoServer(Integer.parseInt(args[0]));
        echoServer.run();
    }
}
