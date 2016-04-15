import java.io.*;
import java.io.*;
import java.net.*;

public class EchoClient {
    String hostname;
    int portNum;

    public EchoClient (String hostname, int portNum) {
        this.hostname = hostname;
        this.portNum = portNum;
    }

    void run () {
        try {
            // Create a client-side server
            Socket echoSocket = new Socket(hostname, portNum);

            System.out.printf("Connecting: hostname %s, port num %d\n", hostname, portNum);
            // Create a PrintWrite to write formatted data into a stream
            //      and BufferedReader to read from socket
            PrintWriter socketOut = new PrintWriter(echoSocket.getOutputStream(), true);
            BufferedReader socketIn = new BufferedReader(
                                            new InputStreamReader(echoSocket.getInputStream()));

            // Create a reader reading from System.in for echo
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

            String ret = stdIn.readLine();
            socketOut.println(ret);
            String echo = socketIn.readLine();
            System.out.println(echo);
        }
        catch (UnknownHostException e) {
            System.err.println("Unknown host.");
            System.exit(1);
        }
        catch (IOException e) {
            System.err.println("Bad IO.");
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        EchoClient echoclient = new EchoClient(args[0], Integer.parseInt(args[1]));
        echoclient.run();
    }
}
