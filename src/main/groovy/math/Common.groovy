package math

import java.util.concurrent.ThreadLocalRandom

/**
 * Created by chist on 2/25/17.
 */
class Common {
    private static Random random = new Random()

    static Map<Character, Closure> operators = [
            ('+' as Character) : {x, y -> x+y},
            ('-' as Character) : {x, y -> x-y},
            ('*' as Character) : {x, y -> x*y},
            ('/' as Character) : {x, y -> x/y}
    ]

    static int computePolish(String expression) {
        LinkedList<Integer> stack = new LinkedList()
        Integer op1, op2
        expression.toCharArray().each {
            if(operators.containsKey(it)){
                op2 = stack.pop()
                op1 = stack.pop()
                stack.push operators[it](op1, op2) as Integer
            }
            else {
                stack.push(Character.getNumericValue(it))
            }
        }
        stack.pop()
    }

    static int factorial(int num){
        int result = 1
        (num+1).times {
            if(it != 0){
                result *= it
            }
        }
        result
    }

    static ArrayList<Integer> getRandoms(int count, int a, int b){
        ArrayList<Integer> list = new ArrayList<>()
        count.times{
            list.add getRandom(a, b)
        }
        list
    }

    static int getRandom(int a, int b){
        return (random.nextInt(b-a) + a) as int
    }

    static double getRandom(double a, double b){
        ThreadLocalRandom.current().nextDouble(a, b)
    }
}
