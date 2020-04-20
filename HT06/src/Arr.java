import java.util.Arrays;

public class Arr {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(
                getTailAfterFour(new int[] {})));
    }

    public static int[] getTailAfterFour(int[] arr) {
        if (arr == null) throw new NullPointerException();

        int indexOf4 = -1;
        for (int i = arr.length - 1; i >= 0; i--) {
            if (arr[i] == 4) {
                indexOf4 = i;
                break;
            }
        }

        if (indexOf4 == -1) throw new RuntimeException();

        int[] res = new int[arr.length - indexOf4 - 1];
        for (int i = 0; i < res.length; i++) {
            res[i] = arr[i + indexOf4 + 1];
        }

        return res;
    }

    public static boolean any14(int[] arr) {
        for (int elt: arr) {
            if (elt == 1 || elt == 4) {
                return true;
            }
        }
        return false;
    }

}
