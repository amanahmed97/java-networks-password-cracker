import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.security.MessageDigest;
import java.util.ArrayList;

public class apiinterfaceClient {
    private static final String IP = "127.0.0.1";
    private static final int Port = 9090;
    public static boolean PasswordFound = false;

    public static void main (String[] args) throws IOException {
        Socket socket = new Socket(IP, Port);
        serverConnection serverConnection = new serverConnection(socket);

        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader keybooard = new BufferedReader(new InputStreamReader(System.in));
        new Thread(serverConnection).start();

        inner:
        while (true) {
            System.out.println("-->");
            String command = keybooard.readLine();
            System.out.println("Total Workers Active :"+serverConnection.NumberOfClientsActive);
            if(command.equals("quit")){
                break;

            }
            out.println(command);
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
