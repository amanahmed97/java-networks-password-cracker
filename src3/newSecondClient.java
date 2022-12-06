import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.security.MessageDigest;
import java.util.ArrayList;

public class newSecondClient {
    private static final String IP = "127.0.0.1";
    private static final int Port = 9090;
    public static boolean PasswordFound = false;

    public static void main (String[] args) throws IOException{
        Socket socket = new Socket(IP,Port);
        serverConnection serverConnection = new serverConnection(socket);
        PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
        InputStreamReader isr = new InputStreamReader(socket.getInputStream());
        BufferedReader in = new BufferedReader(isr);
        new Thread(serverConnection).start();
        inner :    while(true){
            String Status = "FREE";
            System.out.println("Waiting for Hash");
            String ClientCredentials=  null;
            while((ClientCredentials= in.readLine()) != null){
                break;
            }
            System.out.println("Client" + ClientCredentials);
            int first = ClientCredentials.indexOf(' ',31);
            String Hash = ClientCredentials.substring(0,first);
            int ClientNumber = Integer.parseInt(ClientCredentials.substring(first+1,first+2));
            int NumberOfClients = Integer.parseInt(ClientCredentials.substring(first+3,ClientCredentials.length()));
            System.out.println("Hash : "+Hash+" Client Number : "+ClientNumber+" , Number of Clients : "+NumberOfClients);

//            if(NumberOfClients ==  1){
//                break;
//            }


            System.out.println("Generating the Password from the Hash :" );


            char[] AllCharactersNeeded = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
            char [] PasswordFromHash ={'-', '-','-', '-','-'} ;
            OUTER:for(int i = 0 ; i < AllCharactersNeeded.length ; i+=NumberOfClients  ){
                PasswordFromHash[0] = AllCharactersNeeded[ClientNumber-1+i];
                Status = "BUSY";
                for(int j = 0 ; j < AllCharactersNeeded.length;j++){
                    PasswordFromHash[1] = AllCharactersNeeded[j];

                    for(int k = 0 ; k < AllCharactersNeeded.length;k++){
                        PasswordFromHash[2] = AllCharactersNeeded[k];

                        for(int l = 0 ; l < AllCharactersNeeded.length;l++){
                            PasswordFromHash[3]= AllCharactersNeeded[l];

                            for(int m = 0 ; m < AllCharactersNeeded.length;m++){
                                PasswordFromHash[4] = AllCharactersNeeded[m];
//                                if(m == 5){
//                                    System.exit(0);
//                                }
                                String PasswordForHash = toString(PasswordFromHash);
                                System.out.println(PasswordForHash);
                                String HashGeneratednew =  getHashValue(PasswordForHash);

                                if((Hash).equals(HashGeneratednew)){
                                    System.out.println("We found the password you were looking for : ");
                                    System.out.println("Password Generated from Hash is "+PasswordForHash);
                                    out.println(PasswordForHash);
                                    serverConnection.PasswordFound = true;
                                    PasswordFound = true;
                                    continue inner;
                                }
//
                                //third phase==





                            }
                        }
                    }
                }
            }
            System.out.println(AllCharactersNeeded.length);
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
