import org.junit.Test;

import java.util.Map;

/**
 * @author: Yumeng
 * @date: 2022/05/13
 */
public class GcovResolverTest extends TestCase {
    @Test
    public void test() {
        GcovResolver resolver = new GcovResolver(testResBase);
        Map<Integer,Integer> coverByLines = resolver.loadAllLines();
        for (Integer line : coverByLines.keySet()) {
            System.out.println("line " + line + " : " + resolver.getCoverageByLine(line));
        }
    }

}
