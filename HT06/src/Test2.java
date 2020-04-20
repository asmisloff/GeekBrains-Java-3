import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RunWith(Parameterized.class)
public class Test2 {

    private int[] a14;
    private boolean r14;

    @Parameterized.Parameters
    public static Set<Map.Entry<int[], Boolean>> testData2() {
        HashMap<int[], Boolean> data = new HashMap<>();
        data.put(new int[] {1, 2, 4, 4, 2, 3, 4, 1, 7}, true);
        data.put(new int[] {1, 2, 4, 4, 2, 3, 4, 1, 4}, true);
        data.put(new int[] {4, 2, 11, 5, 2, 3, 0}, true);
        data.put(new int[] {13, 2, 11, 5, 2, 3, 0}, false);
        data.put(new int[] {}, false);
        return  data.entrySet();
    }

    public Test2(Map.Entry<int[], Boolean> entry) {
        a14 = entry.getKey();
        r14 = entry.getValue();
    }

    @Test
    public void test() {
        Assert.assertEquals(Arr.any14(a14), r14);
    }

}
