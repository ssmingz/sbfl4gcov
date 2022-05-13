import java.util.LinkedHashSet;
import java.util.Set;

public class ScoreCalculator {
    public String METHOD = "";
    public int LINE;
    public Set<GcovResolver> RESOLVERs = new LinkedHashSet<>();

    double Np = 0.0, Nf = 0.0, Ncp = 0.0, Ncf = 0.0, Nup = 0.0, Nuf = 0.0;

    public ScoreCalculator(String method, int line) {
        METHOD = method;
        LINE = line;
    }

    public void addResolver(GcovResolver resolver) {
        RESOLVERs.add(resolver);
        // load resolver
        int cover = resolver.getCoverageByLine(LINE);
        if (resolver.getResult().equals("PASS")) {
            Np += cover;

        } else if (resolver.getResult().equals("FAIL")) {
            Nf += cover;
        }
    }

    public double computeOchiai() {
        double score = Ncf / Math.pow(Ncf * (Ncf + Ncp), 0.5);
        return score;
    }

    public double computeTarantula() {
        double up = Ncf / Nf;
        double down = Ncp / Np + Ncf / Nf;
        double score = up / down;
        return score;
    }

    public double computeDstar(int star) {
        double up = Math.pow(Ncf, star);
        double down = Nuf + Ncp;
        double score = up / down;
        return score;
    }

    public void setMETHOD(String method) {
        METHOD = method;
    }

    public String getMethod() {
        return METHOD;
    }

    public void setLINE(int line) {
        LINE = line;
    }

    public int getLine() {
        return LINE;
    }
}
