package day03

import groovy.transform.Field

@Field static final String TREE = "#"

List<String> testInput = new File("testThree.txt").readLines()

int treeCounter1 = countTrees(testInput, 0, 3, 1)
assert 7 == treeCounter1
int treeCounter2 = countTrees(testInput, 0, 1, 1)
assert 2 == treeCounter2
int treeCounter3 = countTrees(testInput, 0, 5, 1)
assert 3 == treeCounter3
int treeCounter4 = countTrees(testInput, 0, 7, 1)
assert 4 == treeCounter4
int treeCounter5 = countTrees(testInput, 0, 1, 2)
assert 2 == treeCounter5
assert 336L == calculateTreeProductOfSlopes([treeCounter1, treeCounter2, treeCounter3, treeCounter4, treeCounter5])

List<String> input = new File("inputThree.txt").readLines()
int result1 = countTrees(input, 0, 3, 1)
println("In the main slope there are $result1 trees.")

List<Integer> slopes = [
        countTrees(input, 0, 1, 1),
        countTrees(input, 0, 3, 1),
        countTrees(input, 0, 5, 1),
        countTrees(input, 0, 7, 1),
        countTrees(input, 0, 1, 2)
]
long result2 = calculateTreeProductOfSlopes(slopes)
println("Product of trees on each slope is $result2")

/**
 * <p>Consumes an input List and iterates from top to bottom in a slope with
 * defined steps down and to the right. Every time the position hits the TREE
 * Symbol a counter will be incremented.</p>
 * <p>Each line will be handled as an infinite repetition of itself.</p>
 *
 * @return the count of trees on a slope
 */
static int countTrees(List<String> input, int startPosition, int right, int down) {
    int counter = 0
    int positionX = startPosition
    final int lineLength = input.get(0).length()

    for (int positionY = 0; positionY < input.size(); positionY += down) {
        if (input.get(positionY)[positionX] == TREE) {
            counter++
        }
        positionX = (positionX + right) % lineLength
    }

    return counter
}

static long calculateTreeProductOfSlopes(List<Integer> slopes) {
    long treeProduct = 1L

    slopes.each { trees ->
        treeProduct *= trees
    }

    return treeProduct
}
