public class Test01 {

    @BeforeSuit
    public void before() {
        System.out.println("Before");
    }

    @AfterSuit
    public static void after() {
        System.out.println("After");
    }

    @Test(1)
    public void t1() {
        System.out.println("t1");
    }

    @Test(2)
    public void t2() {
        System.out.println("t2");
    }

    @Test(3)
    public void t3() {
        System.out.println("t3");
    }

    @Test(1)
    public void t4() {
        System.out.println("t4");
    }

}
