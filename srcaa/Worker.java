import java.math.BigInteger;
import java.net.*;
import java.io.*;
import java.security.MessageDigest;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class Worker {
    static int workerId=0;
    static int numberWorkers = 4;
    static int[] workerPorts = {1112, 1113, 1114, 1115};

    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner ip = new Scanner(System.in);
        System.out.println("Worker Starting");

        System.out.print("Enter Worker Id : ");
        workerId = Integer.parseInt(args[0]);

        System.out.println("Worker Id : "+workerId);

        int port = workerPorts[workerId];

        // Server Socket created
        ServerSocket ss = new ServerSocket(port);
        System.out.println("Worker running on port : " + port);
        int tcount = 0;

        System.out.println("Number of Workers : " + numberWorkers);

        while (true) {
            // Socket open TCP connection
            Socket cs1 = ss.accept();
            tcount++;
            new WorkerRun("Thread " + tcount, cs1).start();
        }

    }
}


class WorkerRun extends Thread {
    Socket cs;
    String t_name;
    static int useWorkers=2;

    public WorkerRun(String name, Socket cs1) {
        this.cs = cs1;
        t_name = name;
    }

    public void run() {
        // Initialize variables
        System.out.println("\n\nTHREAD NAME : " + t_name + "\n\n");
        String clientMessage;
        String serverMessage;
        String requestType;
        String hash = new String();
        int requestId = 0;
        String cracked = "null";
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
            useWorkers = Integer.parseInt(messageArray[3]);
            serverMessage = response200;

        } else {
            serverMessage = response404;
        }
        System.out.println("Worker Response : " + serverMessage);

        // Decode Hash
        if (serverMessage.equals(response200)) {
            System.out.println("Hash Received : " + hash);
            System.out.println("Decoding Hash");
            cracked = crackPassword(hash);
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
            System.out.println("CRACKED : " + cracked);
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

    public String crackPassword(String hash) {
        String constructedPassword = "null";
        char[] passwordFromHash = new char[5];

        // Define all characters needed
        char[] allCharactersNeeded = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
                'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
                'u', 'v', 'w', 'x', 'y', 'z'};

        // Run loops to match
        for (int i = Worker.workerId; i < allCharactersNeeded.length; i += WorkerRun.useWorkers) {
            passwordFromHash[0] = allCharactersNeeded[i];

            for (int j = 0; j < allCharactersNeeded.length; j++) {
                passwordFromHash[1] = allCharactersNeeded[j];

                for (int k = 0; k < allCharactersNeeded.length; k++) {
                    passwordFromHash[2] = allCharactersNeeded[k];

                    for (int l = 0; l < allCharactersNeeded.length; l++) {
                        passwordFromHash[3] = allCharactersNeeded[l];

                        for (int m = 0; m < allCharactersNeeded.length; m++) {
                            passwordFromHash[4] = allCharactersNeeded[m];

                            constructedPassword = String.copyValueOf(passwordFromHash);
                            System.out.println(constructedPassword);
                            String constructedHash = getHashValue(constructedPassword);

                            if (hash.equals(constructedHash)) {
                                System.out.println("PASSWORD CRACKED : "+constructedPassword);
                                System.out.println("Password Generated from Hash is " + constructedPassword);

                                return constructedPassword;
//                                break OUTER;
                            }
                        }
                    }
                }
            }
        }


        return "null";
    }

    public static String getHashValue(String password) {
        String HashGenerated = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger bigInt = new BigInteger(1, messageDigest);
            HashGenerated = bigInt.toString(16);
            if (HashGenerated.length() < 32) {
                HashGenerated = '0' + HashGenerated;
            }
            System.out.println("Hash : " +HashGenerated);
        } catch (Exception e) {
            System.out.println("Exception :" + e);
        }
        return HashGenerated;
    }

}


