import java.net.*;
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class NetworkServer{


    public static void main(String [] args) throws IOException, InterruptedException {
        // Initialize variables
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

        System.out.println("Server Starting");
        System.out.print("Enter port number for server to run : ");
        Scanner ip = new Scanner(System.in);
        int port = ip.nextInt();

        // Server Socket created
        ServerSocket ss = new ServerSocket(port);
        System.out.println("Server running on port : "+port);

        while(true){
            // Socket open TCP connection
            Socket cs = ss.accept();
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(cs.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(cs.getOutputStream());

            // CSP
            clientMessage = inFromClient.readLine();
            System.out.println("Client Message : "+clientMessage);
            if (clientMessage==null){
                System.out.println("Connection Close");
                cs.close();
                break;
            }
            String csp = "s(\\s)(rtt|tput)(\\s)[0-9]+(\\s)(\\d+)(\\s)(\\d+)";
            Pattern pattern = Pattern.compile(csp);
            Matcher matcher = pattern.matcher(clientMessage);
            boolean matchFound = matcher.find();
            if(matchFound) {
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
            System.out.println("Server Response : "+serverMessage);
            outToClient.writeBytes(serverMessage+"\n");

            // MP
            if(serverMessage.equals(response200)){
                for(int i=1;i<=probes;i++){
                    clientMessage = inFromClient.readLine();
                    String mp = "m(\\s)[0-9]+(\\s)(\\p{ASCII}*)$";
                    pattern = Pattern.compile(mp);
                    matcher = pattern.matcher(clientMessage);
                    matchFound = matcher.find();
                    int probeCounter = Integer.parseInt(clientMessage.split(" ")[1]);
                    if (matchFound && probeCounter==i){
                        System.out.println("Client Message : m "+i+" payload:"+messageSize);
                        Thread.sleep(serverDelay);
                        System.out.println("Server Echo");
                        outToClient.writeBytes(clientMessage+"\n");
                    }else{
                        serverMessage = response404InvalidMeasure;
                        System.out.println("Server Response : "+serverMessage);
                        outToClient.writeBytes(serverMessage+"\n");
                        cs.close();
                    }
                }
                // CTP
                clientMessage = inFromClient.readLine();
                System.out.println("Client Message : "+clientMessage);
                if(clientMessage.equals("t")){
                    serverMessage = response200Close;
                    System.out.println("Server Response : "+serverMessage);
                    outToClient.writeBytes(serverMessage+"\n");
                }else{
                    // If CTP message not received as expected and MP phase continues
                    serverMessage = response404InvalidMeasure;
                    System.out.println("Server Response : "+serverMessage);
                    outToClient.writeBytes(serverMessage+"\n");
                    System.out.println("Connection close");
                    cs.close();
                }
            }else{
                System.out.println("Connection close");
                cs.close();
            }

            // CTP
            System.out.println("Connection close");
            cs.close();
        }

    }
}
