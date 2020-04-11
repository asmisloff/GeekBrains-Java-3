import java.io.*;
import java.net.Socket;

public class Client {

    private static ObjectInputStream ois;
    private static DataOutputStream dos;

    public static void main(String[] args) {
        try {
            Socket s = new Socket("127.0.0.1", 9999);
            InputStream in = s.getInputStream();
            OutputStream out = s.getOutputStream();
            ois = new ObjectInputStream(in);
            dos = new DataOutputStream(out);

            long start = System.currentTimeMillis();
            Package p = (Package) ois.readObject();
            System.out.printf("Объект получен. Время скачаивания: %.1f с\n", (System.currentTimeMillis() - start) / 1e3);

            System.out.printf("p.getContent() -> %d", p.getContent());

            dos.writeUTF("OK");
            dos.flush();
            Thread.sleep(5000);
        } catch (ClassNotFoundException | InterruptedException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ois.close();
                dos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
