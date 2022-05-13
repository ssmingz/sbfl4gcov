import org.junit.Test;

import java.util.Map;

/**
 * @author: Yumeng
 * @date: 2022/05/13
 */
public class ScoreCalculatorTest extends TestCase {
    @Test
    public void test() {
        int failNum = 1, passNum = 1;
        int targetline = 236;

        GcovResolver failresolver = new GcovResolver(testResBase, "FAIL");
        failresolver.loadAllLines();
        GcovResolver succresolver = new GcovResolver(testResBase, "PASS");
        succresolver.loadAllLines();

        ScoreCalculator calculator = new ScoreCalculator(failresolver.getID(), targetline, passNum,failNum);

        calculator.addResolver(failresolver);
        calculator.addResolver(succresolver);
        // calculate score by formulas
        calculator.computeOchiai();
        calculator.computeTarantula();
        calculator.computeDstar(2);
    }

}