package math

/**
 * Created by chist on 13.04.2017.
 */
class Kolmogorov {
    private def pTable = [
            0.0 : 1.000,
            0.1 : 1.000,
            0.2 : 1.000,
            0.3 : 1.000,
            0.4 : 0.997,
            0.5 : 0.964,
            0.6 : 0.864,
            0.7 : 0.711,
            0.8 : 0.544,
            0.9 : 0.393,
            1.0 : 0.270,
            1.1 : 0.178,
            1.2 : 0.112,
            1.3 : 0.068,
            1.4 : 0.040,
            1.5 : 0.022,
            1.6 : 0.012,
            1.7 : 0.006,
            1.8 : 0.003,
            1.9 : 0.002,
            2.0 : 0.001
    ] as HashMap<Double, Double>
    private double[] lambdaTable = new double[21]
    private ArrayList<Double> statFuncVals

    Kolmogorov(){
        fillLambdaTable()
    }

    private void fillLambdaTable() {
        for (int i = 0; i < 21; i++) {
            lambdaTable[i] = 0.1 * i
        }
    }

    double getLambda(ArrayList<Double> curVals, ArrayList<Double> theorVals, int n) {
        double dMax = 0
        for (int i = 0; i < n; i++) {
            double dp = Math.abs(curVals[i] - theorVals[i])
            double dm = Math.abs(theorVals[i] - curVals[i])
            if (dp > dMax) dMax = dp
            if (dm > dMax) dMax = dm
        }
        dMax * Math.sqrt(n)
    }

    double getProb(double lambda) {
        double dl = 0
        double dr = 0
        for (int i = 1; i < 21; i++) {
            if (lambda < lambdaTable[i]) {
                dl = lambda - lambdaTable[i - 1]
                dr = lambdaTable[i] - lambda
                if (dl <= dr)
                    lambda = lambdaTable[i - 1]
                else
                    lambda = lambdaTable[i]
                break
            }
            else if (lambda == lambdaTable[i])
                lambda = lambdaTable[i]
            else if (lambda >= lambdaTable[20])
                lambda = lambdaTable[20]
        }
        pTable.get lambda.toBigDecimal()
    }

    ArrayList<Double> statFunc(ArrayList<Double> vals, long start, long stop, int n) {
        int k = n //количество разбиений
        double[] statFuncPlotVals = new double[k]
        double delta = ((double)stop - (double)start - 1) / (double) n
        for (int i = 0; i < n; i++) {
            int j = (int)((vals[i] - start) / delta)
            if (j >= k) j = k - 1
            else if (j < 0) j = 0
            statFuncPlotVals[j]++
        }
        for (int i = 0; i < k; i++)
            statFuncPlotVals[i] /= n
        //построение статистической функции
        statFuncVals = new ArrayList<>()
        statFuncVals.add(statFuncPlotVals[0])
        for (int i = 1; i < k; i++) {
            statFuncVals.add(statFuncVals[i - 1] + statFuncPlotVals[i])
        }
        statFuncVals
    }
}
