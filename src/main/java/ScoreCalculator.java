import com.sun.xml.internal.bind.v2.model.core.ID;

import java.util.LinkedHashSet;
import java.util.Set;

public class ScoreCalculator {
    public String IDENTIFIER = "";
    public int LINE;
    public Set<GcovResolver> RESOLVERs = new LinkedHashSet<>();

    double Np = 0.0, Nf = 0.0, Ncp = 0.0, Ncf = 0.0, Nup = 0.0, Nuf = 0.0;

    public ScoreCalculator(String iden, int line, double passNum, double failNum) {    // total number of succ and fail tests
        IDENTIFIER = iden;
        LINE = line;
        Np = passNum;
        Nf = failNum;
    }

    public void addResolver(GcovResolver resolver) {    // a GcovResolver corresponds to a .gcov file
        RESOLVERs.add(resolver);
        // load resolver
        if (!resolver.isLoad()) {
            resolver.loadAllLines();
        }
        int cover = resolver.getCoverageByLine(LINE);
        if (resolver.getResult().equals("PASS")) {
            Ncp += cover;
            Nup = Np - Ncp;
        } else if (resolver.getResult().equals("FAIL")) {
            Ncf += cover;
            Nuf = Nf - Ncf;
        }
        System.out.println("Finished adding resolver to calculator for line " + LINE + " : " + resolver.getID());
    }

    public double computeOchiai() {
        double score = Ncf / Math.pow(Ncf * (Ncf + Ncp), 0.5);
        System.out.println("ochiai," + IDENTIFIER + "," + LINE + "," + score);
        return score;
    }

    public double computeTarantula() {
        double up = Ncf / Nf;
        double down = Ncp / Np + Ncf / Nf;
        double score = up / down;
        System.out.println("tarantula," + IDENTIFIER + "," + LINE + "," + score);
        return score;
    }

    public double computeDstar(int star) {
        double up = Math.pow(Ncf, star);
        double down = Nuf + Ncp;
        double score = up / down;
        System.out.println("dstar," + IDENTIFIER + "," + LINE + "," + score);
        return score;
    }

    public void setID(String method) {
        IDENTIFIER = method;
    }

    public String getID() {
        return IDENTIFIER;
    }

    public void setLine(int line) {
        LINE = line;
    }

    public int getLine() {
        return LINE;
    }
}
