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

public class Server {
    private static String[] names = {"Wily","Felix","Carl","Hobo"};
    private static String[] adjs = {"the gentle","un gentle","overwrought"};

    private static final int PORT = 9090;
    public static ArrayList<ClientNewHandler> clients = new ArrayList<>();
    private static ExecutorService pool = Executors.newFixedThreadPool(4);
    public static void main(String[] args ) throws IOException{
        ServerSocket listener = new ServerSocket(PORT);

        while(true){
            System.out.println("Server Waiting for Client "+clients.size());
            Socket client = listener.accept();
            ClientNewHandler workerThread = new ClientNewHandler(client,clients);
            System.out.println("Worker Thread : "+workerThread);
            System.out.println("Server Connected to Client"+clients.size());
            clients.add(workerThread);
            pool.execute(workerThread);
            System.out.println("Pools Executions" + pool);

        }


    }
    public  static String getRandomName(){
        String name = names[(int)(Math.random()*names.length)];
        String adj = adjs[(int)(Math.random()*adjs.length)];
        return name + " " + adj;
    }

}

