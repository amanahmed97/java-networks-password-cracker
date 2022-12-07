import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.Normalizer;
import java.util.ArrayList;

public class ClientHandler implements Runnable{
    private Socket client ;
    private BufferedReader in ;
    private PrintWriter out ;
    public ArrayList<ClientHandler> clients;
    ArrayList<String> Hash ;
    boolean passwordFound =false;
    int TotalClientNumber;
    String HashDetails = null;

    public ClientHandler(Socket clientSocket, ArrayList<ClientHandler> clients) throws IOException {
        this.client = clientSocket;
        this.clients = clients;
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new PrintWriter(client.getOutputStream(),true);


    }
    public void run(){

        try{
        inner:    while(true){
//                System.out.println("New server Index :" + NewServer.Index);
//                if(NewServer.Index == 0 ){
//                    NewServer.passwordFound = false;
//
////                    System.out.println("It enters Here : ");
//                    while((HashDetails = in.readLine()) != null){
////                        System.out.println("Hash Details" + HashDetails);
//                        break;
//                    }
////                    System.out.println("Cannot move out .");
//                    try{
//                    System.out.println("Length "+HashDetails.length()+" String "+HashDetails);
//                    int first = HashDetails.indexOf(' ',31);
//                    String Hash = HashDetails.substring(0,first);
//                    NewServer.TotalClientNumber = Integer.parseInt(HashDetails.substring(first+1,first+2));
//                    NewServer.Hash.add(Hash);
//                    System.out.println("Hash : "+NewServer.Hash.get(0));
//                    String RenewedHash = NewServer.Hash.get(0) + " " + NewServer.AssignNumberForClients + " " + NewServer.NumberofClients;
////                    outToAll(RenewedHash);
//                    if(NewServer.clients.size()>1){
//                        for(int j = 1 ; j < NewServer.TotalClientNumber ; j++){
//                        System.out.println("Message out :" +NewServer.Hash.get(0)+"Index : "+NewServer.Index+" clients size : "+NewServer.clients.size());
//                        outToSpecific(NewServer.Hash.get(0),j,NewServer.TotalClientNumber);
//                        }
//                    }
//                    String Notification = null;
//                    while ((Notification = in.readLine()) != null) {
//                        System.out.println("Password For the respective Hash is the following :  " + Notification);
//                        outToAll(Notification);
//                        NewServer.passwordFound = true;
//                        out.flush();
//                        NewServer.Index = 0;
//                        for(int i = 0 ; i < NewServer.Hash.size();i++){
//                            NewServer.Hash.remove(i);
//                        }
//                        System.out.println("Hash size :"+NewServer.Hash.size());
//                        break;
//                }} catch (Exception e){
//
//                    }
//                }
//                else{
                System.out.println("Assigned Number :"+NewServer.AssignNumberForClients);
                NewServer.Index++;
                System.out.println("Client : "+NewServer.Index );
                System.out.println("Client Size :" +NewServer.clients.size());

            System.out.println("How Many clients?");
                while(in.readLine() != null){
                    clients.get(clients.size()-1).out.println(clients.size()-1);
                    System.out.println("How Many clients?");
                    break;
                }
                if(!NewServer.clients.isEmpty()){
                    for(int m = 0 ; m < NewServer.clients.size() ; m++){
                        System.out.println("Clients "+m +" "+NewServer.clients.get(m));

                    }                }
                    System.out.println("Assigned Number :"+NewServer.AssignNumberForClients);
                    System.out.println("Waiting For Connection From Workers");
            System.out.println("Checking for Password");
            boolean d = in.ready();
            System.out.println("A :"+d);
                    while((HashDetails = in.readLine()) != null){
                        System.out.println("Hash Details Waiting" + HashDetails);
                        break;
                    }
            System.out.println("Checking for Password");
            boolean c = in.ready();
            System.out.println("A :"+c);
                   try {
                       System.out.println("Length " + HashDetails.length() + " String " + HashDetails);
                                     int first = HashDetails.indexOf(' ',31);
                    String Hash = HashDetails.substring(0,first);
                    NewServer.TotalClientNumber = Integer.parseInt(HashDetails.substring(first+1,first+2));
                    NewServer.Hash.add(Hash);
                    System.out.println("Hash : "+NewServer.Hash.get(0));

                }  catch(Exception e){


                   }

                    String RenewedHash = NewServer.Hash.get(0) + " " + NewServer.AssignNumberForClients + " " + NewServer.NumberofClients;
//                    outToAll(RenewedHash);
                        out.flush();
            System.out.println("Checking for Password");
            boolean b = in.ready();
            System.out.println("A :"+b);
                        System.out.println("Message out :" +NewServer.Hash.get(0)+"Index : "+NewServer.Index+" clients size : "+NewServer.clients.size());
                        for(int h = 0 ; h< NewServer.TotalClientNumber;h++) {
                            outToSpecific(NewServer.Hash.get(0), h, NewServer.TotalClientNumber);
                            out.flush();
                            System.out.println("New server Total Client :" +NewServer.TotalClientNumber );
                        }
                        for(int n = 0 ; n < 1000 ; n ++){
                            out.flush();
                        }
            System.out.println("Checking for Password");
                        boolean a = in.ready();
            System.out.println("A :"+a);
                        String Password = in.readLine();
            System.out.println(Password);
                        while((Password ) !=null){
                            System.out.println("Getting in (1)");
                            while(Password.length() == 5){
                                System.out.println("Password : "+Password);
                                System.out.println("Password found .");
                                passwordFound = true;

                                continue inner;
                            }
                            break;
                        }
                        while((in.readLine() )!=null ){
                            System.out.println("Getting in (2)");
                                outToSpecific(new String(String.valueOf(passwordFound)), 1, NewServer.TotalClientNumber);
                                out.flush();
                                System.out.println(" Password Found : "+new String(String.valueOf(passwordFound)));

                            break;
                        }

                        String Notification ;
                    while((in.readLine()) != null)
                    {
                        System.out.println("Getting in (3)");
                        System.out.println("Notification :");
                    }
            }


        }catch(Exception e){
            e.printStackTrace();
        }
        finally{
            System.out.println("Name Sent Closing ");
//            client.close();
            out.close();
            try {
                in.close();
            } catch (IOException e) {
                throw new RuntimeException(e);

//       }   listener.close();

    }}}

    private void outToSpecific(String message, int index , int Total) {

        System.out.println("Message in "+message);
            String RenewedHash = message + " " + (index+1) + " " +Total;
        System.out.println("Renewed Hash going in "+ (index+1) +" "+ RenewedHash);


                clients.get(index).out.println(RenewedHash);
                 out.flush();
        System.out.println("Clients Sent to "+clients.get(index));





//        System.out.println("Hash Client :"+clients.get(index));


    }
    private void outToAll(String message) {
        for(ClientHandler client : clients){
            client.out.println(message);
            out.flush();


        }

    }
}
