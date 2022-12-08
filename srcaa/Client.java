// Referred sample shared in class slides resources.
import java.net.*;
import java.io.*;
import java.util.*;



public class Client{
    public static void main(String [] args) throws IOException {
        // Initialize message variables
        String phase="r";
        String rType = "hash";
        String sp=" ";
        String newLine ="\n";
        int useWorkers=1;
        int totalWorkers=1;
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

        // Get number of available workers
        String cnum_init = "n workers" + newLine;
        System.out.println("\nCNUM : " + cnum_init);
        outToServer.writeBytes(cnum_init);
        outToServer.flush();
        serverResponse = inFromServer.readLine();
        System.out.println("Server Response : " + serverResponse + "\n");
        totalWorkers = Integer.parseInt(serverResponse);
        System.out.println("Total available workers : "+totalWorkers);
        System.out.println("Close connection");
        clientSocket.close();

        // CSP
        clientSocket = new Socket(host, port);
        outToServer = new DataOutputStream(clientSocket.getOutputStream());
        inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        useWorkers = 2;
        hash = "078ed3bfbe6f511d788aad9995ecccf6";
        System.out.println("Enter number of workers to use : ");
        Scanner ip = new Scanner(System.in);
        useWorkers = ip.nextInt();
        if(useWorkers>totalWorkers)
            useWorkers=totalWorkers;

        String csp_init = phase + sp + rType + sp + hash + sp + useWorkers + newLine;
        System.out.println("\nCSP : " + csp_init);
        long startTime = System.currentTimeMillis();
        outToServer.writeBytes(csp_init);
        serverResponse = inFromServer.readLine();
        long endTime = System.currentTimeMillis();

        System.out.println("Server Response :\nCracked Password : " + serverResponse + "\n");
        System.out.println("Time taken for response : "+(endTime-startTime)+" ms");

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
