package math

/**
*  Created by chist on 13.04.2017.
*/
class CustomRandom {
    private static int numCount = 1000
    private long a = 630360016
    private long m = 2147483647
    private ArrayList<Long> randVals, _as

    ArrayList<Long> getRandomValueList(long start, long stop, int N, long seedValue) {
        randVals = new ArrayList<>()
        _as = getAs(a, N)
        randVals.add(start + (seedValue % (stop - start)))
        for (int i = 1; i < N; i++) {
            randVals.add((start + (((_as[i] * randVals[i - 1]) % m) % (stop - start))))
        }
        randVals
    }

    long getRandomValue(long start, long stop, long seedValue) {
        randVals = new ArrayList<>()
        _as = getAs(a, 1000)
        randVals.add(start + seedValue % stop)
        for (int i = 1; i < numCount; i++) {
            randVals.add((start + (((_as[i] * randVals[i - 1]) % m) % (stop - start))))
        }
        randVals.last()
    }

    private static ArrayList<Long> getAs (long a, int N) {
        ArrayList<Long> qas = new ArrayList<>()
        qas.add(a)
        for (int i = 1; i < N; i++) {
            qas.add(qas[i - 1] + 1)
        }
        return qas
    }
}
