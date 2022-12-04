import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Server class
class serverpassword {

    static int numberOfClients = 0;
    static int AssignClientNumber = 1;
    static int check = 0;
    static ArrayList<ServingClient> clients = new ArrayList<ServingClient>();
    public static void main(String[] args)
    {//Code reference for multithreading server from https://www.geeksforgeeks.org/multithreaded-servers-in-java/
        ServerSocket server = null;

        try {


            server = new ServerSocket(58000);//server listening on 58000
            server.setReuseAddress(true);

            //waiting for client request
            while (true) {


                Socket client = server.accept();
                check++;
                if(check>1){
                    AssignClientNumber++;
                }
                clients.add(new ServingClient(client));

                System.out.println("Client "+AssignClientNumber+" Connected as of now .");
                System.out.println("Total Number of Clients Connected : "+clients.size());
                System.out.println("Client Details are as follows: ");
                System.out.println( client.getInetAddress().getHostAddress());




                    ServingClient clientSocket
                            = new ServingClient(client);//Thread object created

                    new Thread(clientSocket).start();

//This Thread handles the client separately
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (server != null) {
                try {
                    server.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private static class ServingClient implements Runnable {
        private final Socket clientSocket;
        PrintWriter out;
        int clientNumber;
        // Constructor
        public ServingClient(Socket socket)
        {
            this.clientSocket = socket;
        }
//        public void writeworks(Object object){
//            try{
//                out.writeObject(object);
//            }
//            catch(Exception e){
//                e.printStackTrace();
//            }
//        }


        public void run() {

            BufferedReader in = null;
            try {
//output data stream
                out = new PrintWriter(
                        clientSocket.getOutputStream(), true);//output data stream

                in = new BufferedReader(
                        new InputStreamReader(
                                clientSocket.getInputStream()));


                String line;
                //first phase
                while ((line = in.readLine()) != null) {
                    System.out.println("Server received: " + line + ". Sending to client");
                    System.out.println(" number of clients : "+numberOfClients+"Client sequence number :"+AssignClientNumber);
                    out.println(line);
                    out.flush();

                    break;
                }
                //tcp done and now hash request
                System.out.println("CONNECTION ESTABLISHED ....");

                System.out.println("Sending Data :");
                System.out.println("Enter The Password :");
                Scanner input = new Scanner(System.in);
                String Password = input.nextLine();
                String Hash = getHashValue(Password);

                System.out.println("Number of Clients You want to choose for hashing");
                numberOfClients = input.nextInt();
                //hash request done
                //second phase
                while ((line = in.readLine()) != null) {
                    System.out.println("Hash Request By Client" + " :  " +line);


                    System.out.println(" Number of Clients :" + numberOfClients+ " Assigned Number : "+AssignClientNumber);
                    out.println((Hash+" "+AssignClientNumber+" "+numberOfClients));
                    out.flush();

                    break;
                }
                numberOfClients=0;
                AssignClientNumber =1;
                System.out.println("WAITING FOR PASSWORD TO BE CRACKED ......");
                //third phase
                while ((line = in.readLine()) != null) {
                    System.out.println("Password For the respective Hash is the following :  " +line);
                    out.println("Password Cracked");
                    out.flush();
                    break;
                }

        } catch (IOException e) {
                clients.remove(clients.size()-1);
                throw new RuntimeException(e);

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

    }
//    public void sendOne(int index, Object message){
//        clients.get(index).p(message);
//    }
}