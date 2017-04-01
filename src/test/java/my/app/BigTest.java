package my.app;

import my.app.util.Arith;
import org.junit.Test;

/**
 * Created by hanyu on 2017/3/31 0031.
 */
public class BigTest {

    @Test
    public void testArith(){
        try {
            System.out.print(Arith.div(3,12,6));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
