package math
/**
 * Created by chist on 2/25/17.
 */
class PoissonDistribution {

    static LinkedHashMap<Integer, Double> poisson(Number lambda){
        def map = new LinkedHashMap()
        double result = 0
        for(int k = 0; k < 11; k++) {
            result += poisson2(k, lambda.toDouble())
            if(result < 1 && result > 0) map.put k, result
            else break
        }
        map
    }

    static LinkedHashMap<Integer, Double> poisson1(Number lambda) {
        def map = new LinkedHashMap()
        for(int i = 0; i < 11; i++){
            def res = poisson2(i, lambda.toDouble())
            if(res < 1 && res > 0) map.put i, res
            else break
        }
        map
    }

    private static double poisson2(int i, double mean) {
        Math.pow(mean, i) * Math.exp(-mean) / Common.factorial(i)
    }
}
