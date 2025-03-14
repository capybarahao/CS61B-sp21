package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeGetLast();
    }

    public static void timeGetLast() {
        // TODO: YOUR CODE HERE
        int basicN = 1000;
        int basicM = 1000;
        SLList<Integer> list = new SLList<>();
        AList<Integer> Ns = new AList<>();
        AList<Double> times = new AList<>();
        AList<Integer> opCounts = new AList<>();

        for (int i = 1; i < Math.pow(2, 8); i *= 2) {

            for (int j = 0; j < basicN * i; j += 1) {
                list.addLast(0);
            }

            Stopwatch sw = new Stopwatch();
            for (int k = 0; k < basicM; k += 1) {
                int n = list.getLast();
            }
            double timeInSeconds = sw.elapsedTime();

            Ns.addLast(basicN * i);
            times.addLast(timeInSeconds);
            opCounts.addLast(basicM);
        }
        printTimingTable(Ns, times, opCounts);
    }

}
