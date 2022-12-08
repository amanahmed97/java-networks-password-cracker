// Referred sample shared in class slides resources.
import java.net.*;
import java.io.*;
import java.util.*;



public class Client{
    public static void main(String [] args) throws IOException {
        // Initialize message variables
        String phase="r";
        String rType = "hash";
        int message_size=10;
        String sp=" ";
        String newLine ="\n";
        int count=1;
        int probes=10;
        String mp_message;
        String payload;
        String ctp_init;
        String hash;

        String clientInput;
        String serverResponse;

        System.out.println("Client Starting");

        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter host name for server : ");
//        String host = userInput.readLine();
        String host = "localhost";
        System.out.print("Enter port number for server : ");
//        int port = Integer.parseInt(userInput.readLine());
        int port = 1111;
        System.out.println("\nConnecting to server : "+host+" : "+port);


        // Begin connection with Server
        Socket clientSocket = new Socket(host, port);

        // TCP Connection
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        // CSP
        count = 1;
//        hash = "078ed3bfbe6f511d788aad9995ecccf6";
//        hash = "c436518fa5ab879dd2e9eb01a419c609";
        hash = "022c42348345525cf3afc06f46c242b4";

        String csp_init = phase + sp + rType + sp + hash + sp + count + newLine;
        System.out.println("\nCSP : " + csp_init);
        outToServer.writeBytes(csp_init);
        serverResponse = inFromServer.readLine();
        System.out.println("Server Response : " + serverResponse + "\n");

        if (!serverResponse.equals("200 OK: Ready")) clientSocket.close();

        // CTP
//        phase = "t";
//        ctp_init = phase + newLine;
//        System.out.println("CTP : " + ctp_init);
//        outToServer.writeBytes(ctp_init);
//        serverResponse = inFromServer.readLine();
//        System.out.println("Server Response : " + serverResponse);

        System.out.println("Connection Close\n\n\n");
        clientSocket.close();


    }
}
