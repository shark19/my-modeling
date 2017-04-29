package math

/**
 * Created by chist on 19.04.2017.
 */
class StateGraphMath {
    private static final int Nmax = 1000
    private static final double Xmax = 10.0
    private static final double eps = 0.01

    static ArrayList getPAndT(ArrayList<ArrayList<Integer>> randoms) {
        int size = randoms.size()
        double[][] lambda = new double[size][size]
        double[][] pointOfTime = new double[size][Nmax]
        double[][] coefficients = new double[size][size]
        double[][] coefficients2 = new double[size][size]
        double[] derivative = new double[size]
        double[] solve = new double[size]
        double[] time = new double[size]

        //add element to column
        derivative[0] = 1
        for (int i = 1; i < size; i++) {
            derivative[i] = 0
        }

        //table of lambda
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++)
            {
                if (i != j)
                    lambda[j][i] = randoms[i][j]
                else
                    lambda[j][i] = 0
            }
        }

        //table of coefficients
        for (int i = 0; i < size; i++) {
            coefficients[i][i] = 0

            for (int j = 0; j < size; j++) {
                coefficients[i][i] -= lambda[j][i]

                if (i != j)
                    coefficients[j][i] = lambda[j][i]
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                coefficients2[j][i] = coefficients[j][i]
            }
        }

        //solve system
        (derivative, solve) = Analyze(coefficients, derivative, solve, size)

        //solve time
        (solve, pointOfTime) = Times(coefficients2, derivative, solve, pointOfTime, size)

        //search time
        (pointOfTime, time) = Search(pointOfTime, time, size)

        [solve, time, pointOfTime]
    }

    private static ArrayList Analyze(double[][] coefficients, double[] derivative, double[] solve, int size) {
        double[][] E = new double[size][size]

        //identity matrix
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                if (i == j)
                    E[i][j] = 1
                else
                    E[i][j] = 0
            }
        }

        //add row to original matrix
        for (int i = 0; i < size; i++) {
            coefficients[0][i] = 1
        }

        //forward
        for(int t = 0; t < size; t++) {
            double a_v = coefficients[t][t]

            for(int s = 0; s < size; s++) {
                coefficients[t][s] /= a_v
                E[t][s] /= a_v
            }

            for(int i = t + 1; i < size; i++) {
                a_v = coefficients[i][t]

                for(int j = 0; j < size; j++)
                {
                    coefficients[i][j] -= coefficients[t][j] * a_v
                    E[i][j] -= E[t][j] * a_v
                }
            }
        }

        //backward
        for(int i = size - 2; i >= 0; i--) {
            for(int j = i + 1; j < size; j++) {
                double a_v = coefficients[i][j]

                for(int t = 0; t < size; t++) {
                    coefficients[i][t] -= coefficients[j][t] * a_v
                    E[i][t] -= E[j][t] * a_v
                }
            }
        }

        //solve
        for(int i = 0; i < size; i++) {
            double t = 0

            for(int j = 0; j < size; j++)
                t += E[i][j] * derivative[j]

            solve[i] = t
        }
        [derivative, solve]
    }

    private static double Mult(double[][] coefficients2, double[] solve, int size, double sw, int n_sw) {
        double sum = 0

        for (int i = 0; i < size; i++) {
            if (i != n_sw)
                sum += coefficients2[n_sw][i] * solve[i]
            else
                sum += coefficients2[n_sw][i] * sw
        }
        sum
    }

    private static ArrayList Times(double[][] coefficients2, double[] derivative, double[] solve, double[][] pointOfTime, int size) {
        double h = Xmax / Nmax

        for (int i = 0; i < size; i++) {
            pointOfTime[i][0] = derivative[i]
        }

        for (int i = 0; i < size; i++) {
            for (int j = 1; j < Nmax; j++) {
                pointOfTime[i][j] = pointOfTime[i][j - 1] + h * Mult(coefficients2, solve, size, pointOfTime[i][j - 1], i)
            }
        }
        [solve, pointOfTime]
    }

    private static ArrayList Search(double[][] pointOfTime, double[] time, int size) {
        for (int i = 0; i < size; i++) {
            double ref = pointOfTime[i][Nmax - 1]

            for (int j = Nmax - 2; j > 0; j--) {
                if (Math.abs(ref - pointOfTime[i][j]) > eps) {
                    time[i] = (j / (double) Nmax) * Xmax
                    break
                }
            }
        }
        [pointOfTime, time]
    }
}
