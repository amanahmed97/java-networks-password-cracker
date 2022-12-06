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
            int counter = 0 ;
            //waiting for client request
            while (true) {
                counter++;

                Socket client = server.accept();

                check++;
                if(check>1){
                    AssignClientNumber++;
                }

                System.out.println("Client "+AssignClientNumber+" Connected as of now .");
                System.out.println("Total Number of Clients Connected : "+clients.size());
                System.out.println("Client Details are as follows: ");
                System.out.println( client.getInetAddress().getHostAddress());




                    ServingClient clientSocket
                            = new ServingClient(client,clients);//Thread object created
                System.out.println("Clients ********** size : " +clients.size());


                    new Thread(clientSocket).start();
                if(clients.size() == 0 ){
                    clients.add(clientSocket);
                }
                for(int i = 0 ; i <clients.size();i++){
                    if((clients.get(i).clientSocket.getInetAddress() == client.getInetAddress())){
//                            clients.add(clientSocket);
//
                            System.out.println("It is adding");
                            System.out.println("clients : "+clients.get(i).clientSocket.getInetAddress());

                            System.out.println("clients : "+client.getInetAddress());
                        System.out.println("It shouldn't add");
                    }
                    else{
                        System.out.println("It should add");


                    }
                }

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
        public ArrayList<ServingClient> clients;
        // Constructor
        public ServingClient(Socket socket,ArrayList<ServingClient> clients)
        {
            this.clientSocket = socket;
            this.clients = clients;
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
                    System.out.println(" number of clients : "+numberOfClients+"Client sequence number :"+clients.size());
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
                    System.out.println(" Number of Clients :" + numberOfClients+ " Assigned Number : "+clients.size());
                    out.println((Hash+" "+clients.size()+" "+numberOfClients));
                    out.flush();

                    break;
                }
                numberOfClients=0;
                AssignClientNumber --;
                System.out.println("WAITING FOR PASSWORD TO BE CRACKED ......");
                //third phase
                while ((line = in.readLine()) != null) {
                    System.out.println("Password For the respective Hash is the following :  " +line);
                    outToAll("Password Cracked");
                    out.flush();
                    break;
                }

        } catch (IOException e) {
                throw new RuntimeException(e);

            }
        }
        //use array list for the clients :: hint
        public void outToAll(String message){
            for(ServingClient client:clients){
               client.out.println(message);
            }
        }
        public void DisplayClients(int index)
        {

                System.out.println("Number of Clients connected : "+clients.size());
                System.out.println("("+clients.get(index)+")");

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