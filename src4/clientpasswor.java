import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Scanner;
import java.util.StringJoiner;

public class clientpasswor
{
    private final static String HOSTNAME = "csa1.bu.edu";
    private final static int PORT = 58000;

    public static void main(String[] args) throws IOException
    {      Scanner Input ;
        int PacketSize;
        String Status = "Free";
        Input= new Scanner(System.in);
        double[] RTT;
        double sum = 0 ;
        double sum2 =0;
        double[] Throughput = new double[2000];
        double size_of_data;
        double meanThroughput;
        String newNumber;
        String SecondPhase;
        String newOneByte;
        String ThirdPhase;
        double meanrtt ;
        int sequenceNumber;
        RTT = new double[20];
        long startTime ;
        long elapsedTime;
        double elapsedTimeSeconds;
        long difference;
        String FirstPhase;
        long Conversion;
        try (

             Socket clientSocket = new Socket(HOSTNAME, PORT);

             InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
             BufferedReader in = new BufferedReader(isr);
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        ) {


            System.out.println("Connected to " + HOSTNAME + " on port " + PORT);
            System.out.println("Choose if you want to connect this user for ");
//            String Mode = in.readLine();
                String data = "Making First Connection";
                String SelectMode;
                System.out.println("Sending to server:\n" + data);
                out.println(data);

            String line;
//first phase
            while ((line = in.readLine()) != null) { //Making tcp Connection
                System.out.println("TCP Connection Made : Client received: " + line);
                break;
            }
            System.out.println("TCP Connection Successful now waiting For Checking Password."); // When the server replies the first time we move to First Phase for sending the message
//HASH REQUEST
            //second phase
            String hashRequest = "Hash Request";
            out.println(hashRequest);
            String ClientCredentials = in.readLine();
            int first = ClientCredentials.indexOf(' ',31);
            String Hash = ClientCredentials.substring(0,first);
            int ClientNumber = Integer.parseInt(ClientCredentials.substring(first+1,first+2));
            int NumberOfClients = Integer.parseInt(ClientCredentials.substring(first+3,ClientCredentials.length()));
            System.out.println("Hash : "+Hash+" Client Number : "+ClientNumber+" , Number of Clients : "+NumberOfClients);
            String HashGenerated = Hash ;

            System.out.println("Generating the Password from the Hash :" );


            char[] AllCharactersNeeded = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
            char [] PasswordFromHash ={'-', '-','-', '-','-'} ;
for(int i = 0 ; i < AllCharactersNeeded.length ; i+=NumberOfClients  ){
                PasswordFromHash[0] = AllCharactersNeeded[ClientNumber-1+i];
                Status = "BUSY";
                for(int j = 0 ; j < AllCharactersNeeded.length;j++){
                    PasswordFromHash[1] = AllCharactersNeeded[j];

                    for(int k = 0 ; k < AllCharactersNeeded.length;k++){
                        PasswordFromHash[2] = AllCharactersNeeded[k];

                        for(int l = 0 ; l < AllCharactersNeeded.length;l++){
                            PasswordFromHash[3]= AllCharactersNeeded[k];

                            for(int m = 0 ; m < AllCharactersNeeded.length;m++){
                                PasswordFromHash[4] = AllCharactersNeeded[m];

                                String PasswordForHash = toString(PasswordFromHash);
                                System.out.println(PasswordForHash);
                                String HashGeneratednew =  getHashValue(PasswordForHash);

                                if(HashGeneratednew.equals(HashGenerated)){
                                    System.out.println("We found the password you were looking for : ");
                                    System.out.println("Password Generated from Hash is "+PasswordForHash);
                                    out.println(PasswordForHash);
                                    System.exit(0);
                                    break;
                                }
//

                                //third phase==





                            }


                        }                }
                }}
            System.out.println(AllCharactersNeeded.length);
        }
        Status = "Free";
    }
    public static class serverClientConnection implements Runnable{
            public Socket server;
            public BufferedReader in ;
            public serverClientConnection(Socket server ) throws IOException {
                this.server = server ;
                in =new BufferedReader(
                        new InputStreamReader(
                                server.getInputStream()));
            }
        @Override
        public void run() {


                    try {
                        while(true){
                        String serverResponse = null;

                        serverResponse = in.readLine();


                        System.out.println("Server Responded : " + serverResponse);
                            break;
                    }
                    }
                catch(Exception e){
                      e.printStackTrace();
                }finally {
                        try{
                            in.close();

                        }
 catch(Exception e){
                            e.printStackTrace();
 }
                    }


        }
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

    public static String toString(char[] a){
        String string = new String(a);
        return string ;
    }
}