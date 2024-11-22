import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread{
    private int port;
    private Postoecka postoecka;

    public Server(int port, Postoecka postoecka) {
        this.port = port;
        this.postoecka = postoecka;
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while(true){
                Socket socket = serverSocket.accept();
                Worker worker = new Worker(socket,postoecka);
                worker.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Postoecka postoecka = new Postoecka();
        Server server = new Server(8080,postoecka);
        server.start();
    }
}
