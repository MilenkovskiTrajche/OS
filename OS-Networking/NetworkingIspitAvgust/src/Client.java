import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client extends Thread {
    private int port;
    private String ipAdress;

    public Client(int port, String ipAdress) {
        this.port = port;
        this.ipAdress = ipAdress;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket(ipAdress, port);
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            Scanner scanner = new Scanner(inputStream);

            outputStream.write(("Login\n").getBytes(StandardCharsets.UTF_8));
            outputStream.flush();

            String line = scanner.nextLine();
            System.out.println(line);
            //Login info gore

            outputStream.write(("start:files\n").getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
            Scanner scanner1 = new Scanner(System.in);

            while(true){
                String msg = scanner1.next();
                outputStream.write((msg + "\n").getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
                if(msg.equals("STOP")){
                    break;
                }
                String poraka = scanner.nextLine();
                System.out.println(poraka);
            }

            String lg = scanner.nextLine();
            System.out.println(lg);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        Client client = new Client(8080, "localhost");
        client.start();
    }
}
