package math
/**
 * Created by chist on 2/18/17.
 */
class NewtonPolynomial {
    /**
     *
     * @param coord point, in which we want to find function value
     * @param nodeCount count of nodes
     * @param x x tabulate list
     * @param y y tabulate list
     * @param step function step
     * @return function value
     * For example: if we need f(x) = x^3 func value in 2.1 point with step = 1:
     * compute(2.1, 4, [0, 1, 2, 3], [0, 1, 8, 27], 1)
     */
    static double compute(double coord, int nodeCount, List<Double> x, List<Double> y, double step){
        def array = createArray nodeCount, x, y
        def dy = createDy nodeCount, array
        def xn = createXn coord, array, nodeCount
        computeResult dy, nodeCount, xn, step
    }

    static double computeResult(ArrayList<Double> dy, int nodeCount, List<Double> xn, double step) {
        def res = 0
        (nodeCount + 1).times{
            if(it == 0){
                res += dy.first()
            }
            else {
                res += dy.get(it) * xn.get(it-1) / (Common.factorial(it) * Math.pow(step, it))
            }
        }
        res
    }

    static List<Double> createXn(double coord, double[][] array, int nodeCount) {
        List<Double> xn = new ArrayList<>()
        nodeCount.times {
            if(it == 0){
                xn.add coord - array[0][0]
            }
            else {
                xn.add xn.get(it - 1) * (coord - array[0][it])
            }
        }
        xn
    }

    static ArrayList<Double> createDy(int nodeCount, double[][] array) {
        def dy = new ArrayList<>()
        (nodeCount + 1).times {
            dy.add array[it+1][0]
        }
        dy
    }

    static double[][] createArray(int nodeCount, List<Double> x, List<Double> y){
        def array = new double[nodeCount+2][nodeCount+1]
        nodeCount.times {
            array[0][it] = x.get it
            array[1][it] = y.get it
        }
        def tmp = nodeCount
        for (int i = 2; i < nodeCount + 1; i++) {
            tmp.times {
                array[i][it] = array[i-1][it+1] - array[i-1][it]
            }
            tmp--
        }
        array
    }
}
