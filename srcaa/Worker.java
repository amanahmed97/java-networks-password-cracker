import java.net.*;
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class Worker {
    static int workerId;

    public static void main(String [] args) throws IOException, InterruptedException {
        int[] workerPorts = {1112,1113};

        Scanner ip = new Scanner(System.in);
        System.out.println("Worker Starting");
        System.out.print("Enter Worker Id : ");
        workerId = ip.nextInt();
        int port = workerPorts[workerId-1];

        // Server Socket created
        ServerSocket ss = new ServerSocket(port);
        System.out.println("Worker running on port : "+port);
        int tcount=0;

        int numberWorkers = 2;
        System.out.println("Number of Workers : "+numberWorkers);

        while(true){
            // Socket open TCP connection
            Socket cs1 = ss.accept();
            tcount++;
            new WorkerRun("Thread "+tcount,cs1).start();
        }

    }
}


class WorkerRun extends Thread{
    Socket cs;
    String t_name;
    public WorkerRun(String name, Socket cs1){
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
        int requestId=0;
        String cracked;
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

        String request = "w(\\s)(hash)(\\s)[a-f0-9]{32}(\\s)(\\d+)";
        Pattern pattern = Pattern.compile(request);
        Matcher matcher = pattern.matcher(clientMessage);
        boolean matchFound = matcher.find();

        if (matchFound) {
            System.out.println("Server Hash Init\n");
            String[] messageArray = clientMessage.split(" ");
            requestType = messageArray[1];
            hash = messageArray[2];
            requestId = Integer.parseInt(messageArray[3]);
            serverMessage = response200;

        } else {
            serverMessage = response404;
        }
        System.out.println("Worker Response : " + serverMessage);

        // Decode Hash
        if (serverMessage.equals(response200)) {
            System.out.println("Hash Received : "+hash);
            System.out.println("Decoding Hash");
        } else {
            System.out.println("Connection close");
            try {
                cs.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // Send cracked password back to Server
        try {
//            String cracked = "cracked password : TRYING";
            cracked = "TRYING"+Worker.workerId;
            outToClient.writeBytes(cracked + "\n");
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
}


