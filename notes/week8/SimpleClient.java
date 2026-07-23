import java.io.OutputStream;
import java.net.Socket;

public class SimpleClient {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 27931;
        try (Socket socket = new Socket(host, port);
             OutputStream out = socket.getOutputStream()) {

            String msg = "Hello RCRS Server!";
            out.write(msg.getBytes());
            out.flush();
            System.out.println("消息发送完成：" + msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
