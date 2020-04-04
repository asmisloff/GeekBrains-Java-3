import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println("------------ test swap() ------------");
        Integer[] ar = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        printArray(ar);
        swap(ar, 0,5);
        System.out.print(" --> "); printArray(ar);
        System.out.println();

        System.out.println("------------ test arrayToArrayList ------------");
        Double[] ar1 = {0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0};
        System.out.println(arrayToArrayList(ar));
        System.out.println(arrayToArrayList(ar1));
        System.out.println();

        System.out.println("------------ test fruit boxes ------------");
        Box<Apple> ba = new Box<>();
        Box<Apple> baa = new Box<>();
        Box<Orange> bo = new Box<>();
        for (int i = 0; i < 10; i++) {
            ba.add(new Apple());
            bo.add(new Orange());
        }
        System.out.print("ba: "); ba.println();
        System.out.print("baa: "); baa.println();
        System.out.print("bo: "); bo.println();
        System.out.printf("'ba' is %s equal to 'bo'\n", (ba.compare(bo) == true) ? "" : "not");
        System.out.println("Tilt out fruits from 'ba' to 'baa'");
        ba.tiltOutTo(baa);
        System.out.print("ba: "); ba.println();
        System.out.print("baa: "); baa.println();
    }

    static void swap(Object[] ar, int i, int j) {
        Object tmp = ar[i];
        ar[i] = ar[j];
        ar[j] = tmp;
    }

    static <T> ArrayList<T> arrayToArrayList(T[] ar) {
        return new ArrayList<T>(Arrays.asList(ar));
    }

    static void printArray(Object[] ar) {
        System.out.print("{ ");
        for (Object elt : ar) {
            System.out.printf("%s, ", elt.toString());
        }
        System.out.print("\b\b }");
    }
}
