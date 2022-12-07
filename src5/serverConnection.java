import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class serverConnection implements Runnable{
    private Socket server ;
    private BufferedReader in;
    private PrintWriter out;
    int ChoosenClients;

    int NumberOfClientsActive;
    String Password ;
    int counter = 0 ;
    boolean PasswordFound = false;
    public serverConnection(Socket server) throws IOException {
        this.server = server ;
        in = new BufferedReader(new InputStreamReader(server.getInputStream()));
        out =  new PrintWriter(server.getOutputStream(),true);


    }

    public void run(){
    String serverResponse = null ;

    try{
        while(true) {
            serverResponse = in.readLine();
            if(counter == 0){
            if(isNumber(serverResponse)){
                NumberOfClientsActive =Integer.parseInt(serverResponse);
            }} else if (counter == 1 ) {
                if(isNumber(serverResponse)){
                    ChoosenClients =Integer.parseInt(serverResponse);
                }
            }
            else if (counter == 1) {
                if(serverResponse!=null){
                    Password = serverResponse;
                    System.out.println("Password Found :"+serverResponse);
                }
            } else if (serverResponse.contains("Password")) {
                System.out.println("Password found : " + serverResponse);
            }


            if(serverResponse == null){
                break;
            }

            counter++;
            System.out.println("Server says :" + serverResponse);

        }


    }catch (Exception e){
        e.printStackTrace();
    }
    finally{
        try {
            in.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    }
    static boolean isNumber(String s)
    {
        for (int i = 0; i < s.length(); i++)
            if (Character.isDigit(s.charAt(i)) == false)
                return false;

        return true;
    }
}
