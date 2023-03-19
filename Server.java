import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static void main(String[] args) throws SocketException {
        try (ServerSocket serverSocket = new ServerSocket(1234)) {
            System.out.println("Сервер запущен, ожидаем подключение...");
            Socket socket = serverSocket.accept();
            System.out.println("Клиент подключился");
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            while (true) {
                String clientRequest = dataInputStream.readUTF();
                if (clientRequest.equals("end"))
                    break;
                StringBuilder requestSort = getNumber(clientRequest);
                String temp = String.format("%s", requestSort);
                String[] resultC = temp.split(" ");
                double a = Double.parseDouble(resultC[0]);
                String sign = resultC[1];
                double b = Double.parseDouble(resultC[2]);
                double result;
                switch (sign) {
                    case "+":
                        result = a + b;
                        if (result % 1 > 0) {
                            dataOutputStream.writeUTF(String.format("Сумма чисел равна: %.2f", result));
                            break;
                        } else {
                            dataOutputStream.writeUTF(String.format("Сумма чисел равна: %.0f", result));
                            break;
                        }
                    case "-":
                        result = a - b;
                        if (result % 1 > 0) {
                            dataOutputStream.writeUTF(String.format("Разница чисел равна: %.2f", result));
                            break;
                        } else {
                            dataOutputStream.writeUTF(String.format("Разница чисел равна: %.0f", result));
                            break;
                        }
                    case "*":
                        result = a * b;
                        if (result % 1 > 0) {
                            dataOutputStream.writeUTF(String.format("Умножение чисел равно: %.2f", result));
                            break;
                        } else {
                            dataOutputStream.writeUTF(String.format("Умножение чисел равно: %.0f", result));
                            break;
                        }
                    case "/":
                        result = a / b;
                        if (result % 1 > 0) {
                            dataOutputStream.writeUTF(String.format("Деление чисел равно: %.2f", result));
                            break;
                        } else {
                            dataOutputStream.writeUTF(String.format("Деление чисел равно: %.0f", result));
                            break;
                        }
                }
                System.out.println("Клиент сказал " + clientRequest);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param client
     * @return
     */
    static StringBuilder getNumber(String client) {
        String finds = "qwertyuiop[]asdfghjkl;'zxcvbnm/йцукенгшщзхъфывапрол=джэячсмитьбю!\"№%:?()ё~`@#$^&{}><";
        String[] getString = client.split(" ");
        List<String> list = new ArrayList<>();
        for (String item : getString) {
            list.add(item);
        }
        List<String> newList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String[] find = list.get(i).split("");
            for (String s : find) {
                newList.add(s);
            }
            for (int nl = 0; nl < newList.size(); nl++) {
                if (finds.contains(newList.get(nl))) {
                    newList.remove(nl);
                    nl = 0;
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int nl = 0; nl < newList.size(); nl++) {
            if (newList.get(nl).equals("+") || newList.get(nl).equals("-")
                    || newList.get(nl).equals("/") || newList.get(nl).equals("*")) {
                sb.append(" ");
                sb.append(newList.get(nl));
                sb.append(" ");
            } else {
                sb.append(newList.get(nl));
            }
        }
        return sb;
    }
}
