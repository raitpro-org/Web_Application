import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;


public class Server extends Thread {
    private Socket socket;
    private ArrayList<Server> threadList;
    private PrintWriter output;

    public Server(Socket socket, ArrayList<Server> threads) {
        this.socket = socket;
        this.threadList = threads;
    }

    @Override
    public void run() {
        try {

            BufferedReader input = new BufferedReader( new InputStreamReader(socket.getInputStream()));

             output = new PrintWriter(socket.getOutputStream(),true);

            while(true) {
                String outputString = input.readLine();

                if(outputString.equals("exit")) {
                    break;
                }
                printToALlClients(outputString);
                //output.println(outputString);
                System.out.println("Server received " + outputString);

            }


        } catch (Exception e) {
            System.out.println("Error occured " +e.getStackTrace());
        }
    }

    private void printToALlClients(String outputString) {
        for( Server sT: threadList) {
            sT.output.println(outputString);
        }
    }
}