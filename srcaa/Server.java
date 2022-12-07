import java.net.*;
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class Server {

    static int numberWorkers;
    static int[] workerPorts = {1112,1113};

    public static void main(String [] args) throws IOException, InterruptedException {

        System.out.println("Server Starting");
        // System.out.print("Enter port number for server to run : ");
        Scanner ip = new Scanner(System.in);
        // int port = ip.nextInt();
        int port = 1111;

        // Server Socket created
        ServerSocket ss = new ServerSocket(port);
        System.out.println("Server running on port : "+port);
        int tcount=0;

        numberWorkers = 2;
        System.out.println("Number of Workers : "+numberWorkers);

        while(true){
            // Socket open TCP connection
            Socket cs1 = ss.accept();
            tcount++;
            new ServerRun("Thread "+tcount,cs1).start();
        }

    }
}


class ServerRun extends Thread{
    Socket cs;
    String t_name;

    public ServerRun(String name, Socket cs1){
        this.cs = cs1;
        t_name = name;
    }
    public void run() {
        // Initialize variables
        System.out.println("\n\nTHREAD NAME : "+t_name+"\n\n");
        String clientMessage;
        String serverMessage;
        String requestType;
        String hash = new String();
        String cracked = new String();
        int requestId=0;
        String response200 = "200 OK: Ready";
        String response404 = "404 ERROR: Invalid Connection Setup Message";
        String response200Close = "200 OK: Closing Connection";
        String response404InvalidMeasure = "404 ERROR: Invalid Measurement Message";

        // Socket open TCP connection
        BufferedReader inFromClient = null;
        try {
            inFromClient = new BufferedReader(new InputStreamReader(cs.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        DataOutputStream outToClient = null;
        try {
            outToClient = new DataOutputStream(cs.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Get the Hash
        try {
            clientMessage = inFromClient.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Client Message : " + clientMessage);
        if (clientMessage == null) {
            System.out.println("Connection Close");
            try {
                cs.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        String request = "r(\\s)(hash)(\\s)[a-f0-9]{32}(\\s)(\\d+)";
        Pattern pattern = Pattern.compile(request);
        Matcher matcher = pattern.matcher(clientMessage);
        boolean matchFound = matcher.find();
        if (matchFound) {
            System.out.println("Request Init\n");
            String[] messageArray = clientMessage.split(" ");            
            requestType = messageArray[1];
            hash = messageArray[2];
            requestId = Integer.parseInt(messageArray[3]);
            serverMessage = response200;

        } else {
            serverMessage = response404;
        }
        System.out.println("Server Response : " + serverMessage);

        // Send hash to workers
         if (serverMessage.equals(response200)) {
             try {
                 cracked = sendHashWorkers(hash);
             } catch (IOException e) {
                 throw new RuntimeException(e);
             }
         } else {
            System.out.println("Connection close");
            try {
                cs.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // Send cracked password back to client
        try {
            String crackedOutput = "cracked password : "+cracked;
            outToClient.writeBytes(crackedOutput + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // CTP
        System.out.println("Connection close");
        try {
            cs.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public String sendHashWorkers(String hash) throws IOException{
        System.out.println("Hash Received : "+hash);
        System.out.println("Sending hash to workers");

        // Initialize
        int port;
        String host = "localhost";
        String phase="w";
        String rType = "hash";
        String sp=" ";
        String newLine ="\n";
        int count=1;
        String workerResponse = "null";
        Socket[] workerSockets = new Socket[Server.numberWorkers];
        DataOutputStream[] outToWorkers = new DataOutputStream[Server.numberWorkers];
        BufferedReader[] inFromWorkers = new BufferedReader[Server.numberWorkers];

        // Server as a client sends hash to Workers
        for(int i=0; i<Server.numberWorkers; i++){

            // Connection
            port = Server.workerPorts[i];
            System.out.println("\nConnecting to worker "+(i+1)+" : "+host+" : "+port);
            workerSockets[i] = new Socket(host, port);

            // TCP Connection
            outToWorkers[i] = new DataOutputStream(workerSockets[i].getOutputStream());
            inFromWorkers[i] = new BufferedReader(new InputStreamReader(workerSockets[i].getInputStream()));

            String csp_init = phase + sp + rType + sp + hash + sp + count + newLine;
            System.out.println("\nCSP : " + csp_init);
            outToWorkers[i].writeBytes(csp_init);
            workerResponse = inFromWorkers[i].readLine();
            System.out.println("Worker Response : " + workerResponse + "\n");

            if (!workerResponse.equals("200 OK: Ready")) workerSockets[i].close();

            System.out.println("Connection Close\n\n\n");
            workerSockets[i].close();

        }

        return workerResponse;
    }
}


