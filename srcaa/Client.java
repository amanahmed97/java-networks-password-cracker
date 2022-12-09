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
        int useWorkers=2;
        int totalWorkers=4;
        String ctp_init;
        String hash;

        String clientInput;
        String serverResponse;

        System.out.println("Client Starting");

        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

//        System.out.print("Enter host name for server : ");
//        String host = userInput.readLine();
        String host = "localhost";
//        System.out.print("Enter port number for server : ");
//        int port = Integer.parseInt(userInput.readLine());
        int port = 1111;
        host = "pcvm5-13.geni.uchicago.edu";

        System.out.println("\nConnecting to server : "+host+" : "+port);

        Scanner ip = new Scanner(System.in);
        System.out.println("Enter hash : ");
        hash = ip.nextLine();

        // Begin connection with Server
        Socket clientSocket = new Socket(host, port);
        System.out.println("open socket");
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
        // hash = "078ed3bfbe6f511d788aad9995ecccf6";
//        hash = "7efc5160dc70371bebfebfaee8867447";
        

        System.out.print("Enter number of workers to use : ");        
        useWorkers = ip.nextInt();
        if(useWorkers>totalWorkers)
            useWorkers=totalWorkers;        
        

        String csp_init = phase + sp + rType + sp + hash + sp + useWorkers + newLine;
        System.out.println("\nCSP : " + csp_init);
        double startTime = System.currentTimeMillis();
        outToServer.writeBytes(csp_init);
        serverResponse = inFromServer.readLine();
        double endTime = System.currentTimeMillis();

        System.out.println("Server Response :\nCracked Password : " + serverResponse + "\n");
        System.out.println("Time taken for response : "+(endTime-startTime)+" ms");
        System.out.println("Time taken for response : "+(endTime-startTime)/1000+" seconds");

        if (!serverResponse.equals("200 OK: Ready")) clientSocket.close();

        // CTP
        System.out.println("Connection Close\n\n\n");
        clientSocket.close();


    }
}
