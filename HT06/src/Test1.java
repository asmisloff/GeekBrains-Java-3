import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

@RunWith(Parameterized.class)
public class Test1 {

    private int[] srcArr;
    private int[] expectedArr;

    @Parameterized.Parameters
    public static Collection<int[][]> testData1() {
        return Arrays.asList(new int[][][] {
                { {1, 2, 4, 4, 2, 3, 4, 1, 7}, {1, 7} },
                { {1, 2, 4, 4, 2, 3, 4, 1, 4}, {} },
                { {4, 2, 11, 5, 2, 3, 0}, {2, 11, 5, 2, 3, 0} },
                { {13, 2, 11, 5, 2, 3, 0}, {} },
                { {}, {} },
                { null, null }
        });
    }

    public Test1(int[] srcArr, int[] expectedArr) {
        this.srcArr = srcArr;
        this.expectedArr = expectedArr;
    }

    @Test()
    public void test() {
        try {
            Assert.assertArrayEquals(expectedArr, Arr.getTailAfterFour(srcArr));
        } catch (NullPointerException npe) {
                Assert.assertNull(srcArr);
        } catch (RuntimeException rte) {
            Assert.assertArrayEquals(expectedArr, new int[0]);
        } catch (Exception e) {
            Assert.fail("Unexpected exception");
        }
    }

}