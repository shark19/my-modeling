package math

/**
 * Created by chist on 13.04.2017.
 */
class UniformDistribution {
    private ArrayList<Double> uniDistrVals

    ArrayList<Double> getUniDistrFuncValues(ArrayList<Double> args, long start, long stop, int n) {
        uniDistrVals = new ArrayList<>()
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
}
