import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
//import java.lang.Thread;

public class ServerMain {

    public static void main(String[] args) {

        ArrayList<Server> threadList = new ArrayList<>();
        try (ServerSocket serversocket = new ServerSocket(5000)){
            while(true) {
                Socket socket = serversocket.accept();
                Server serverThread = new Server(socket, threadList);


                
                threadList.add(serverThread); 
                serverThread.start();


            }
        } catch (Exception e) {
            System.out.println("Error occured in main: " + e.getStackTrace());
        }
    }
}