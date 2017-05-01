package math

/**
 * Created by chist on 13.04.2017.
 */
class UniformDistribution {

    static ArrayList<Double> getUniDistrFuncValues(ArrayList<Double> args, long start, long stop, int n) {
        ArrayList<Double> uniDistrVals = new ArrayList<>()
        long temp = 0
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1; j++) {
                if (args[j] > args[j + 1]) {
                    temp = args[j]
                    args[j] = args[j + 1]
                    args[j + 1] = temp
                }
            }
        }
        for (int i = 0; i < n; i++) {
            if (args[i] < start)
                uniDistrVals.add 0
            else if ((args[i] >= start) && (args[i] <= stop))
                uniDistrVals.add (((args[i] - start) as double) / ((stop - start) as double))
            else
                uniDistrVals.add 1
        }
        uniDistrVals
    }

    static Map<Integer, Double> getDistr(double a, double b){
        def map = new HashMap()
        for (double i = a - Math.abs(a - b); i <= b + Math.abs(a - b); i++) {
            map.put(i, distr(i, a, b))
        }
        map
    }

    static Map<Integer, Double> getProb(double a, double b){
        def map = new HashMap()
        for (double i = a - Math.abs(a - b); i <= b + Math.abs(a - b); i++) {
            map.put(i, prob(i, a, b))
        }
        map
    }

    static double mWaiting(double a, double b) {
        (a + b) / 2
    }
    static double mDisp(double a, double b) {
        Math.pow(b - a, 2) / 12
    }

    private static double distr(double x, double a, double b) {
        double f
        if (x < a)
            f = 0
        else if (a <= x && x < b)
            f = (x - a) / (b - a)
        else
            f = 1
        f
    }

    private static double prob(double x, double a, double b) {
        double f
        if (a <= x && x <= b)
            f = 1 / (b - a)
        else
            f = 0
        f
    }
}
