package math

/**
 * Created by chist on 2/25/17.
 */
class NewtonPolynomialTest extends GroovyTestCase {
    def nodeCount = 4
    def x = [0, 1, 2, 3]
    def y = [0, 1, 8, 27]
    def coord = 2.1
    def step = 1

    void testCompute() {
        def res = NewtonPolynomial.compute(coord, nodeCount, x, y, step)
        assertEquals(res.toString().substring(0, 5) as double, 9.261)
    }

    void testComputeResult() {
        def array = NewtonPolynomial.createArray(nodeCount, x, y)
        def dy = NewtonPolynomial.createDy(nodeCount, array)
        def xn = NewtonPolynomial.createXn(coord, array, nodeCount)
        def res = NewtonPolynomial.computeResult(dy, nodeCount, xn, 1)
        assertEquals(res.toString().substring(0, 5) as double, 9.261)
    }

    void testCreateXn() {
        assertEquals(NewtonPolynomial.createXn(coord, NewtonPolynomial.createArray(nodeCount, x, y), nodeCount) as int[], [2, 2, 0, 0])
    }

    void testCreateDy() {
        assertEquals(NewtonPolynomial.createDy(nodeCount, NewtonPolynomial.createArray(nodeCount, x, y)), [0.0, 1.0, 6.0, 6.0, 0.0])
    }

    void testCreateArray() {
        double[][] array = NewtonPolynomial.createArray(nodeCount, x, y)
        double[][] fakeArray = [[0.0, 1.0, 2.0, 3.0, 0.0],
                                [0.0, 1.0, 8.0, 27.0, 0.0],
                                [1.0, 7.0, 19.0, -27.0, 0.0],
                                [6.0, 12.0, -46.0, 0.0, 0.0],
                                [6.0, -58.0, 0.0, 0.0, 0.0],
                                [0.0, 0.0, 0.0, 0.0, 0.0]] as double[][]
        assertEquals(array, fakeArray)
    }
}
