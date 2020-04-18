public class Finish extends Stage {

    private int cnt = 0;

    @Override
    public synchronized void go(Car c) {
        System.out.printf(">>>>>>>>>>>>>>> %s финишировал <<<<<<<<<<<<<< -- %d место\n", c.getName(), ++cnt);
    }

}
