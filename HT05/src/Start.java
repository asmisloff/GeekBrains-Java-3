import java.util.concurrent.CountDownLatch;

public class Start extends Stage {

    private CountDownLatch cdl;
    private boolean flag = false;

    public Start(int n) {
        cdl = new CountDownLatch(n);
    }

    @Override
    public void go(Car c) throws InterruptedException {
        System.out.println(c.getName() + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(c.getName() + " готов");
            cdl.countDown();
            cdl.await();

            printOnce("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
    }

    private synchronized void printOnce(String msg) {
        if (!flag) {
            System.out.println(msg);
            flag = true;
        }
    }

}
