import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author: Yumeng
 * @date: 2022/05/13
 */
public class GcovResolver {
    public String PATH = "";
    public Map<Integer, Integer> coverByLine = new LinkedHashMap<>();
    public String METHOD = "";
    public String TESTRESULT = ""; // PASS OR FAIL

    public GcovResolver(String path) {
        PATH = path;
    }

    public GcovResolver(String path, String method) {
        PATH = path;
        METHOD = method;
    }

    public void setMethod(String method) {
        METHOD = method;
    }

    public String getMethod() {
        return METHOD;
    }

    public String getResult() {
        return TESTRESULT;
    }

    public void setResult(String res) {
        TESTRESULT = res;
    }

    public Map<Integer, Integer> loadAllLines() {
        File input = new File(PATH);
        if (!input.exists()) {
            System.out.println("Input file not found : " + PATH);
            return null;
        }
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(input));
            String line;
            while((line = reader.readLine()) != null) {
                if (line.split(":").length >= 3) {
                    String cover = line.split(":")[0].trim();
                    int lineNo = Integer.parseInt(line.split(":")[1].trim());
                    if (lineNo > 0) {
                        if (cover.endsWith("*")) {
                            cover = cover.substring(0, cover.indexOf('*'));
                        } else if (cover.equals("-") || cover.equals("#####")) {
                            cover = "0";
                        }
                        int covercount = Integer.parseInt(cover);
                        coverByLine.put(lineNo, covercount);
                        //System.out.println("line " + lineNo + " executed " + covercount);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Finished resolving coverage " + PATH);
        return coverByLine;
    }

    public int getCoverageByLine(int line) {
        if (coverByLine.containsKey(line)) {
            return coverByLine.get(line);
        } else {
            System.out.println("[WARN] No coverage for line " + line + " found in " + PATH);
            return -1;
        }
    }
}
