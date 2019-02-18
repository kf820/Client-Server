import java.util.Scanner;
import java.io.*;

public class ClientMain{

    public static void main(String args[]) throws IOException {
		if (args.length != 2) {
            System.err.println(
                "Usage: java ClientMain <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
        ClientObject obj = new ClientObject(hostName, portNumber);
    }

}