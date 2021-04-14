package day03

import groovy.transform.Field

@Field static final String TREE = "#"

List<String> testInput = [
        "..##.......",
        "#...#...#..",
        ".#....#..#.",
        "..#.#...#.#",
        ".#...##..#.",
        "..#.##.....",
        ".#.#.#....#",
        ".#........#",
        "#.##...#...",
        "#...##....#",
        ".#..#...#.#"
]

int treeCounter1 = countTrees(testInput, 0, 3, 1)
assert treeCounter1 == 7

List<String> input = new File("inputThree.txt").readLines()
int result1 = countTrees(input, 0, 3 ,1)
println("In the main slope there are $result1 trees.")

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
    int lineLength = input.get(0).length()

    for (int positionY = 0; positionY < input.size(); positionY += down) {
        if (input.get(positionY)[positionX] == TREE) {
            counter++
        }
        positionX = (positionX + right) % lineLength
    }

    return counter
}
