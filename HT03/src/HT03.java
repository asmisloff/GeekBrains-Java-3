import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class HT03 {
    public static void main(String[] args) {
        File fh = new File("HT03/src/HT03.java");
        try {
            byte[] b = readBytesFromFile(fh, 50);
            for (int i = 0; i < b.length; i++) {
                System.out.printf("(%d: 0x%x) ", i, b[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        /////////////////////////////////////////////////////////////////////////////////////////

        ArrayList<InputStream> streams = new ArrayList<>();
        try (FileOutputStream os =
                     new FileOutputStream("HT03/src/Письма римскому другу.txt", false)) {
            for (int i = 1; i <= 9; i++) {
                streams.add(new FileInputStream(String.format("HT03/src/%d.txt", i)));
            }
            chainFiles(streams, os);
        } catch (IOException e) {
            e.getStackTrace();
        }

        /////////////////////////////////////////////////////////////////////////////////////////

        String path = "HT03/src/bigFile.txt";
        try (FileWriter w = new FileWriter(path)) {
            for (int i = (int)1e6; i < 1e7; i++) { // generate big file
                w.write(Integer.toString(i));
            }
            long t0 = System.currentTimeMillis();
            bufferedReadFromFile(path, 10000);
            System.out.println("Timespan: " + (System.currentTimeMillis() - t0));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static byte[] readBytesFromFile(File fh, int size) throws IOException {
        byte[] buf = new byte[size];
        try (FileInputStream in = new FileInputStream(fh)) {
            in.read(buf);
        }
        return buf;
    }

    private static void chainFiles(ArrayList<InputStream> inStreams, OutputStream os) throws IOException {
        SequenceInputStream sis = new SequenceInputStream(Collections.enumeration(inStreams));
        byte[] buf = new byte[50];
        int len;
        while ((len = sis.read(buf)) != -1) {
            os.write(buf, 0, len);
        }
    }

    private static void bufferedReadFromFile(String path, int pageSize) throws IOException {
        char[] buf = new char[pageSize];
        int len;
        StringBuilder sb = new StringBuilder();
        try (FileReader fr = new FileReader(path)) {
            while ((len = fr.read(buf)) != -1) {
                sb.append(buf, 0, len);
            }
        }
        System.out.println(sb);
    }
}
