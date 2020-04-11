import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static Socket s;
    private static ObjectOutputStream oos;
    private static  DataInputStream dis;

    public static void main(String[] args) {
        try (ServerSocket ss = new ServerSocket(9999)) {
            System.out.println("Ожидание подключения");
            s = ss.accept();
            System.out.println("Клиент подключился");

            InputStream in = s.getInputStream();
            OutputStream out = s.getOutputStream();
            oos = new ObjectOutputStream(out);
            dis = new DataInputStream(in);

            Package p = new Package();
            p.setContent(13);
            sendPackage(p);
            System.out.println(waitForClientsResponse());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                oos.close();
                dis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void sendPackage(Package p) throws IOException {
        long start = System.currentTimeMillis();
        oos.writeObject(p);
        oos.flush();
        System.out.printf("Отправка завершена. Время отправки: %.1f с\n", (System.currentTimeMillis() - start) / 1e3);
    }

    private static String waitForClientsResponse() throws IOException, InterruptedException {
        String resp;
        while (! ((resp = dis.readUTF()).equals("OK")) ) {
            Thread.sleep(100);
        }
        return resp;
    }
}
