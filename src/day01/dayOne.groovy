package day01

import groovy.transform.Field

@Field static final TARGET_YEAR = 2020

List<Integer> testInput = [1721, 979, 366, 299, 675, 1456]
int testResult1 = getYearProductOfTwo(testInput)
assert testResult1 == 514579
int testResult2 = getYearProductOfThree(testInput)
assert testResult2 == 241861950

List<Integer> input = new File("inputOne.txt").readLines().collect { line -> line.toInteger() }
int result1 = getYearProductOfTwo(input)
println("Solution for first Puzzle is $result1")
int result2 = getYearProductOfThree(input)
println("Solution for first Puzzle is $result2")

/**
 * <p>Collects two values from a List if the sum of these values is equal to TARGET_YEAR.</p>
 *
 * <p>Valid Example:<br/>
 * 1721 + 299 = 2020<br/>
 * 1721 * 299 = 514579</p>
 *
 * @return the Product of the values; <br/>0 if no matching has been found
 */
static int getYearProductOfTwo(List<Integer> input) {
    for (int i = 0; i < input.size(); i++) {
        for (int j = i; j < input.size(); j++) {
            int first = input.get(i)
            int second = input.get(j)

            if (first + second == TARGET_YEAR) {
                return first * second
            }
        }
    }
    return 0
}

/**
 * <p>Collects three values from a List if the sum of these values is equal to TARGET_YEAR.</p>
 *
 * <p>Valid Example:<br/>
 * 979 + 366 + 675 = 2020<br/>
 * 979 * 366 * 675 = 241861950</p>
 *
 * @return the Product of the values; <br/>0 if no matching has been found
 */
static int getYearProductOfThree(List<Integer> input) {
    for (int i = 0; i < input.size(); i++) {
        for (int j = i; j < input.size(); j++) {
            for (int k = j; k < input.size(); k++) {
                int first = input.get(i)
                int second = input.get(j)
                int third = input.get(k)

                if (first + second + third == TARGET_YEAR) {
                    return first * second * third
                }
            }
        }
    }
    return 0
}
