package math
/**
 * Created by chist on 2/25/17.
 */
class PoissonDistribution {

    static LinkedHashMap<Integer, Double> poisson(Number lambda){
        def map = new LinkedHashMap()
        double e = Math.exp(-lambda.toDouble())
        int k
        double result = 0
        for(k = 0; k < 100; k++) {
            result += (e*Math.pow(lambda.toDouble(), k))/Common.factorial(k)
            if(result < 1) {
                map.put k, result
            }
            else break
        }
        map
    }
}
