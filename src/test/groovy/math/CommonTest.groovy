package math

/**
 * Created by chist on 2/25/17.
 */
class CommonTest extends GroovyTestCase {
    void testFactorial() {
        assertEquals(Common.factorial(5), 120)
    }

    void testComputePolish() {
        assertEquals(Common.computePolish("452*+92-/"), 2)
    }
}
