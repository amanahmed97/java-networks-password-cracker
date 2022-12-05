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

    public ClientHandler(Socket clientSocket, ArrayList<ClientHandler> clients) throws IOException {
        this.client = clientSocket;
        this.clients = clients;
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new PrintWriter(client.getOutputStream(),true);


    }
    public void run(){

        try{
            while(true){
                NewServer.Index++;
                System.out.println("Client : "+NewServer.Index );
                System.out.println("Client Size :" +NewServer.clients.size());
                if(!NewServer.clients.isEmpty()){
                System.out.println("Client 1 "+NewServer.clients.get(0));}
                if(NewServer.AssignNumberForClients == 0){
                    System.out.println("Waiting For Hash From API Client");
                    NewServer.Hash.add(in.readLine());
                    System.out.println("Hash : "+NewServer.Hash.get(0));

                    NewServer.AssignNumberForClients++;
                }
                else {
                    String RenewedHash = NewServer.Hash.get(0) + " " + NewServer.AssignNumberForClients + " " + NewServer.NumberofClients;
                    outToAll(RenewedHash);
//                    outToSpecific(NewServer.Hash.get(0),NewServer.Index,NewServer.clients.size());
                    String Notification = null;
                    while ((Notification = in.readLine()) != null) {
                        System.out.println("Password For the respective Hash is the following :  " + Notification);
                        outToAll(Notification);

                        out.flush();
                        break;
                    }
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
            }
//            listener.close();
        }
    }

    private void outToSpecific(String message, int index , int Total) {

        for(int i = 1 ; i < clients.size();i++){
            String RenewedHash = message + " " + index + " " +Total;
            clients.get(i).out.println(RenewedHash);
        }

    }
    private void outToAll(String message) {
        for(ClientHandler client : clients){
            client.out.println(message);

        }

    }
}
