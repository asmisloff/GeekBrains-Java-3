import java.util.concurrent.Semaphore;

public class HT05 {
    public static final int CARS_COUNT = 4;
    public static void main(String[] args) {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(
                new Start(CARS_COUNT),
                new Road(60),
                new Tunnel(new Semaphore(CARS_COUNT / 2)),
                new Road(40),
                new Finish());
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
        }

        Thread[] threads = new Thread[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            threads[i] = new Thread(cars[i]);
            threads[i].start();
        }

        for (int i = 0; i < CARS_COUNT; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("<<<<<<<<<<<<<<<<<<<<< --- >>>>>>>>>>>>>>>>>>>>>");
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
        System.out.println("<<<<<<<<<<<<<<<<<<<<< --- >>>>>>>>>>>>>>>>>>>>>");
    }
}




