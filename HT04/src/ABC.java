import java.io.ObjectInputStream;

public class ABC {
    public static void main(String[] args) {
        Printer p = new Printer();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    p.printA();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    p.printB();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    p.printC();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    static class Printer {

        private char curr = 'C';

        public synchronized void printA() throws InterruptedException {
            for (int i = 0; i < 5; i++) {
                while (curr != 'C') wait();
                System.out.println('A');
                curr = 'A';
                notifyAll();
            }
        }

        public synchronized void printB() throws InterruptedException {
            for (int i = 0; i < 5; i++) {
                while (curr != 'A') wait();
                System.out.println('B');
                curr = 'B';
                notifyAll();
            }
        }

        public synchronized void printC() throws InterruptedException {
            for (int i = 0; i < 5; i++) {
                while (curr != 'B') wait();
                System.out.println('C');
                curr = 'C';
                notifyAll();
            }
        }
    }
}
