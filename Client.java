import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;
import java.net.UnknownHostException;

public class Client {
    /**
     * @param args
     * @throws SocketException
     */
    public static void main(String[] args) throws SocketException {
        Scanner scanner = new Scanner(System.in);
        try (Socket socket = new Socket("Localhost", 1234)) {
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            while (true) {
                System.out.println("Введите выражение через пробел: ");
                String request = scanner.nextLine();
                if (request.equals("end"))
                    break;
                if (request.contains(",")) {
                    System.out.println("Не корректный ввод");
                    continue;
                }
                dataOutputStream.writeUTF(request);
                System.out.println(dataInputStream.readUTF());
            }
            scanner.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}