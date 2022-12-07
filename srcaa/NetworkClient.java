// Referred sample shared in class slides resources.
import java.net.*;
import java.io.*;
import java.util.*;

// RTT size: 1, 100, 200, 400, 800 and 1000 bytes.
// Throughput size: 1K, 2K, 4K, 8K, 16K and 32K bytes.

public class NetworkClient{
    public static void main(String [] args) throws IOException {
        // Initialize message variables
        String phase="s";
        String rtt="rtt";
        String tput="tput";
        String mType = rtt;
        int message_size=10;
        int server_delay=0;
        String sp=" ";
        String newLine ="\n";
        int count=1;
        int probes=10;
        String mp_message;
        String payload;
        String ctp_init;
        int[] rttArray = {1, 100, 200, 400, 800, 1000};
        int[] tputArray = {1000, 2000, 4000, 8000, 16000, 32000};
        int[]  sizeArray;

        String clientInput;
        String serverResponse;

        System.out.println("Client Starting");

        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter host name for server : ");
        String host = userInput.readLine();
        System.out.print("Enter port number for server : ");
        int port = Integer.parseInt(userInput.readLine());
        System.out.print("Enter server delay in milliseconds : ");
        server_delay = Integer.parseInt(userInput.readLine());
        System.out.println("Connecting to server : "+host+" : "+port);

        for(int type=1;type<=2;type++) {

            // Set measurement type
            if (type == 1) {
                mType = rtt;
                sizeArray = rttArray;
                System.out.println("RTT Measurement");
            } else {
                mType = tput;
                sizeArray = tputArray;
                System.out.println("Throughput Measurement");
            }

            // Begin connection with Server
            for (int k : sizeArray) {

                Socket clientSocket = new Socket(host, port);

                // TCP Connection
                DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
                BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                // CSP
                phase = "s";
                message_size = k;
                count = 1;

                System.out.println("\n\n\nMeasurement for Message Size : " + message_size + " Bytes");
                String csp_init = phase + sp + mType + sp + probes + sp + message_size + sp + server_delay + newLine;
                System.out.println("\nCSP : " + csp_init);
                outToServer.writeBytes(csp_init);
                serverResponse = inFromServer.readLine();
                System.out.println("Server Response : " + serverResponse + "\n");

                if (!serverResponse.equals("200 OK: Ready")) clientSocket.close();

                // MP
                phase = "m";
                payload = "";
                long rttMeasure = 0;
                long rttSum = 0;
                float tputMeasure = 0;
                float tputSum = 0;
                float rttAverage = 0;
                float tputAverage = 0;

                for (int j = 0; j < message_size; j++)
                    payload += "0";

                while (count <= probes) {
                    mp_message = phase + sp + count + sp + payload + newLine;
                    System.out.print("MP : " + phase + sp + count + sp + message_size + "Bytes" + newLine);
                    long time_send_millis = System.currentTimeMillis();
                    outToServer.writeBytes(mp_message);
                    serverResponse = inFromServer.readLine();
                    long time_receive_millis = System.currentTimeMillis();
                    System.out.println("Server Response : Echo message");
                    rttMeasure = time_receive_millis - time_send_millis;
                    tputMeasure = ((float) message_size*8)/rttMeasure;
                    rttSum += rttMeasure;
                    tputSum += tputMeasure;

                    if (!serverResponse.equals((phase + sp + count + sp + payload))) {
                        System.out.println("Incorrect response from server for MP");
                        clientSocket.close();
                    }

                    if(type==1) System.out.println("RTT : " + rttMeasure + " ms\n");
                    else System.out.println("TPUT : " + tputMeasure +" Kbps = " +(tputMeasure/1000) + " Mbps\n");
                    count++;
                }
                rttAverage = (float) rttSum / probes;
                tputAverage = (float) tputSum / probes;
                if (type==1)
                    System.out.println("RTT Average " + message_size + " Bytes : " + rttAverage + " ms");
                else {
                    System.out.println("TPUT Average "+message_size+" Bytes : "+tputAverage+" Kbps = "+(tputAverage/1000)+" Mbps");
                }

                // CTP
                phase = "t";
                ctp_init = phase + newLine;
                System.out.println("CTP : " + ctp_init);
                outToServer.writeBytes(ctp_init);
                serverResponse = inFromServer.readLine();
                System.out.println("Server Response : " + serverResponse);

                System.out.println("Connection Close\n\n\n");
                clientSocket.close();
            }
        }
    }
}
