import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NewServer {
    static int AssignNumberForClients;
    int counter = 1 ;
    static int NumberofClients;
    static ArrayList<String> Hash ;
    static boolean passwordFound = false;
    private static final int PORT = 9090;
    static int Index =1 ;
    static int TotalClientNumber;
    public static ArrayList<ClientHandler> clients = new ArrayList<>();
    private static ExecutorService pool = Executors.newFixedThreadPool(4);
    public static void main(String[] args ) throws IOException{
        ServerSocket listener = new ServerSocket(PORT);
        AssignNumberForClients = clients.size();
         Hash = new ArrayList<>();

        while(true){
        System.out.println("Server Waiting for Client "+clients.size());
        Socket client = listener.accept();
        ClientHandler workerThread = new ClientHandler(client,clients);
            System.out.println("Worker Thread : "+workerThread);
        System.out.println("Server Connected to Client"+clients.size());
            clients.add(workerThread);
        pool.execute(workerThread);
            System.out.println("Pools Executions" + pool);
            System.out.println("Password boolean  : "+passwordFound);
        }

    }

}

