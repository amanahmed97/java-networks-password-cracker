import java.net.*;
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

class NetworkServerRun extends Thread{
    Socket cs;
    String t_name;
    public NetworkServerRun(String name, Socket cs1){
        this.cs = cs1;
        t_name = name;
    }
    public void run() {
        // Initialize variables
        System.out.println("\n\nTHREAD NAME : "+t_name+"\n\n");
        String clientMessage;
        String serverMessage;
        String measurementType;
        int probes=0;
        int messageSize=0;
        int serverDelay=0;
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

        // CSP
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

        String csp = "s(\\s)(rtt|tput)(\\s)[0-9]+(\\s)(\\d+)(\\s)(\\d+)";
        Pattern pattern = Pattern.compile(csp);
        Matcher matcher = pattern.matcher(clientMessage);
        boolean matchFound = matcher.find();
        if (matchFound) {
            System.out.println("CSP Init\n");
            String[] messageArray = clientMessage.split(" ");
            measurementType = messageArray[1];
            probes = Integer.parseInt(messageArray[2]);
            messageSize = Integer.parseInt(messageArray[3]);
            serverDelay = Integer.parseInt(messageArray[4]);
            serverMessage = response200;
        } else {
            serverMessage = response404;
        }
        System.out.println("Server Response : " + serverMessage);
        try {
            outToClient.writeBytes(serverMessage + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // MP
        if (serverMessage.equals(response200)) {
            for (int i = 1; i <= probes; i++) {
                try {
                    clientMessage = inFromClient.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                String mp = "m(\\s)[0-9]+(\\s)(\\p{ASCII}*)$";
                pattern = Pattern.compile(mp);
                matcher = pattern.matcher(clientMessage);
                matchFound = matcher.find();
                int probeCounter = Integer.parseInt(clientMessage.split(" ")[1]);
                if (matchFound && probeCounter == i) {
                    System.out.println("Client Message : m " + i + " payload:" + messageSize);
                    try {
                        Thread.sleep(serverDelay);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Server Echo");
                    try {
                        outToClient.writeBytes(clientMessage + "\n");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    serverMessage = response404InvalidMeasure;
                    System.out.println("Server Response : " + serverMessage);
                    try {
                        outToClient.writeBytes(serverMessage + "\n");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        cs.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            // CTP
            try {
                clientMessage = inFromClient.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Client Message : " + clientMessage);
            if (clientMessage.equals("t")) {
                serverMessage = response200Close;
                System.out.println("Server Response : " + serverMessage);
                try {
                    outToClient.writeBytes(serverMessage + "\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                // If CTP message not received as expected and MP phase continues
                serverMessage = response404InvalidMeasure;
                System.out.println("Server Response : " + serverMessage);
                try {
                    outToClient.writeBytes(serverMessage + "\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Connection close");
                try {
                    cs.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            System.out.println("Connection close");
            try {
                cs.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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

public class NetworkServerMultiThread {

    public static void main(String [] args) throws IOException, InterruptedException {

        System.out.println("Server Starting");
        System.out.print("Enter port number for server to run : ");
        Scanner ip = new Scanner(System.in);
        int port = ip.nextInt();

        // Server Socket created
        ServerSocket ss = new ServerSocket(port);
        System.out.println("Server running on port : "+port);
        int tcount=0;

        while(true){
            // Socket open TCP connection
            Socket cs1 = ss.accept();
            tcount++;
            new NetworkServerRun("Thread "+tcount,cs1).start();
        }

    }
}
