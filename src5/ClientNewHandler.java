import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.Normalizer;
import java.util.ArrayList;

public class ClientNewHandler implements Runnable{
    private Socket client ;
    private BufferedReader in ;
    private PrintWriter out ;
    String HashDetails ;
    int count = 0;
    public ArrayList<ClientNewHandler> clients;


    ArrayList<String> Hashing ;

    boolean passwordFound =false;
    int TotalClientNumber;


    public ClientNewHandler(Socket clientSocket, ArrayList<ClientNewHandler> clients) throws IOException {
        this.client = clientSocket;
        this.clients = clients;
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new PrintWriter(client.getOutputStream(),true);
        Hashing = new ArrayList<String>();

    }
    public void run(){

        try{

            while(true){
                String request = in.readLine();
                while(request != null){
                if(request.contains("name")){
                    out.println(Server.getRandomName());
                    break;
                }
                else if(request.contains("many")){
                    clients.get(clients.size()-1).out.println(clients.size()-1);
                    break;
                }
                else if(isNumber(request)){
                    TotalClientNumber = Integer.parseInt(request);
                    break;
                }
                else if(request.contains("Hash")){
                    int first = request.indexOf(' ',31);
                    String Hash = request.substring(0,first);
                    Hashing.add(Hash);
                    System.out.println("Hashing Deliver Process Starts :");
                    for(int h = 0 ; h<TotalClientNumber;h++) {
                        outToSpecific(Hashing.get(0), h, TotalClientNumber);
                        out.flush();
                    }
                    break;
                } else if (request.contains("Password")) {
                    clients.get(clients.size()-1).out.println(request);
                    for(int h = 0 ; h<TotalClientNumber;h++) {
                        outToSpecific("quit", h, TotalClientNumber);
                        out.flush();
                    }
                    out.flush();
                    break;
                } else if(request.startsWith("say")){
                    int firstSpace = request.indexOf(" ");
                    if(firstSpace != -1){
                        outToAll(request.substring(firstSpace+1));
                    }
                    break;
                }
                else{
                    out.println("Type 'tell me a name ' to get a random name." );
                    break;
                }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally{
            System.out.println("Name Sent Closing ");
//            client.close();
            out.close();
            try {
                in.close();
            } catch (IOException e) {
                throw new RuntimeException(e);

//       }   listener.close();

            }}}
    static boolean isNumber(String s)
    {
        for (int i = 0; i < s.length(); i++)
            if (Character.isDigit(s.charAt(i)) == false)
                return false;

        return true;
    }
    private void outToSpecific(String message, int index , int Total) {

        System.out.println("Message in "+message);
        String RenewedHash = message + " " + (index+1) + " " +Total + " Hash";
        System.out.println("Renewed Hash going in "+ (index+1) +" "+ RenewedHash);


        clients.get(index).out.println(RenewedHash);
        out.flush();
        System.out.println("Clients Sent to "+clients.get(index));





//        System.out.println("Hash Client :"+clients.get(index));


    }

    private void outToSpecificPassword(String message, int index , int Total) {

        System.out.println("Message in "+message);
        String RenewedHash = message ;
        System.out.println("Password Request going in "+ (index+1) +" "+ RenewedHash);


        clients.get(index).out.println(RenewedHash);
        out.flush();
        System.out.println("Clients Sent to "+clients.get(index));





//        System.out.println("Hash Client :"+clients.get(index));


    }
    private void outToAll(String message) {
        for(ClientNewHandler client : clients){
            client.out.println(message);
            out.flush();


        }

    }
}
