import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Worker extends Thread{
    private Socket socket;
    private Postoecka postoecka;

    public Worker(Socket socket, Postoecka postoecka) {
        this.socket = socket;
        this.postoecka = postoecka;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            Scanner scanner = new Scanner(inputStream);

            String line = scanner.nextLine();
            if(line.equals("Login")){
                outputStream.write(("Logged in " + socket.getInetAddress() + "\n" ).getBytes(StandardCharsets.UTF_8));
                outputStream.flush();

            }

            //kreirame file
            File WORD_COUNT = new File("src\\WORD_COUNT.txt");
            if (WORD_COUNT.createNewFile()) {
                System.out.println("");
            } else {
                System.out.println("");
            }
            File suma = new File("src\\suma.txt");
            if (suma.createNewFile()) {
                System.out.println("");
            } else {
                System.out.println("");
            }

            BufferedReader bufferedReader = new BufferedReader(new FileReader(WORD_COUNT));
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(WORD_COUNT,true));

            BufferedReader readSuma = new BufferedReader(new FileReader(suma));
            BufferedWriter writeSuma = new BufferedWriter(new FileWriter(suma));

            //gi citame of file podatocite i gi dodavame vo postoecka
            String post;
            while((post = bufferedReader.readLine()) != null){
                postoecka.sumStr += post;
                postoecka.sumStr += "\n";
            }
            bufferedReader.close();

            int sum;
            sum = readSuma.read();
            postoecka.sum = sum;
            readSuma.close();

            String msg = scanner.nextLine();
            int counter = 0;
            if(msg.equals("start:files")){
                while(true){
                    String msgs = scanner.next();
                    if(msgs.equals("STOP")){
                        bufferedWriter.close();
                        break;
                    }
                    //gledame vo postoecka dali go sodrzi fileot
                    if(postoecka.sumStr.contains(msgs)){
                        outputStream.write((msgs + " IMA\n").getBytes(StandardCharsets.UTF_8));
                        outputStream.flush();
                        postoecka.sumStr += msgs;
                    }
                    if(!(postoecka.sumStr.contains(msgs))){
                        outputStream.write((msgs + " NEMA\n").getBytes(StandardCharsets.UTF_8));
                        outputStream.flush();
                        postoecka.sumStr += msgs;
                        postoecka.sumStr += "\n";
                        bufferedWriter.write(msgs + "\n");
                        bufferedWriter.flush();
                        counter++;
                    }
                }
            }

            //gledame dali e broj
            Pattern pattern = Pattern.compile("\\d+");
            Matcher matcher = pattern.matcher(postoecka.sumStr);

            //ako e broj go dodavame vo postoecka
            while (matcher.find()) {
                int number = Integer.parseInt(matcher.group());
                 postoecka.tmpsum += number;
            }
            postoecka.sum += postoecka.tmpsum;
            writeSuma.write(postoecka.tmpsum + "\n");
            writeSuma.flush();
            writeSuma.close();

            //gi prakame na klient site nepoznati zborovi i sumata
            outputStream.write(("Logged out, zborovi koi ne se poznati:"+ counter + "\n").getBytes(StandardCharsets.UTF_8));
            outputStream.flush();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
