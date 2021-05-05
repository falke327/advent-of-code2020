package day10

List<Integer> test = new File("testTen.txt").readLines().collect { it as int }
List<Integer> sortedTest = collectSortedAdaptersWithSourceAndSink(test)
assert 7 == countByStepSize(sortedTest, 1)
assert 5 == countByStepSize(sortedTest, 3)
assert 35 == calculateAdapterProduct(test)
assert 8 == countAllSortedChains(test)

List<Integer> test2 = new File("testTen2.txt").readLines().collect { it as int }
assert 220 == calculateAdapterProduct(test2)
assert 19208 == countAllSortedChains(test2)

List<Integer> input = new File("inputTen.txt").readLines().collect { it as int }
int result1 = calculateAdapterProduct(input)
println("The product of overall adapter gaps is $result1")

BigDecimal result2 = countAllSortedChains(input)
println("There are $result2 ways to combine the adapters")

/**
 * The adapter product is calculated as product of one-step adapters by three-step-adapters
 */
static int calculateAdapterProduct(List<Integer> adapters) {
    List<Integer> sortedAdapters = collectSortedAdaptersWithSourceAndSink(adapters)
    int oneStepAdapters = countByStepSize(sortedAdapters, 1)
    int threeStepAdapters = countByStepSize(sortedAdapters, 3)

    return oneStepAdapters * threeStepAdapters
}

/**
 * Unfortunately I didn't get how this algorithm works. Adapted dynamic programming solution from TheMorpheusTutorials
 */
static BigDecimal countAllSortedChains(List<Integer> unsortedAdapters) {
    List<Integer> sortedAdapters = collectSortedAdaptersWithSourceAndSink(unsortedAdapters)

    List<BigDecimal> possibilities = [0] * sortedAdapters.max()
    possibilities[0] = 1
    // start from second entry to skip leading 0 in List
    sortedAdapters.remove(0)
    for (Integer i : sortedAdapters) {
        possibilities[i] = (possibilities[i - 1] as BigDecimal + possibilities[i - 2] as BigDecimal + possibilities[i - 3] as BigDecimal)
    }

    return possibilities.last()
}

static int countByStepSize(List<Integer> adapters, int stepSize) {
    int counter = 0

    for (int i = 0; i < adapters.size() - 1; i++) {
        if (adapters[i + 1] - adapters[i] == stepSize) {
            counter++
        }
    }

    return counter
}

static List<Integer> collectSortedAdaptersWithSourceAndSink(List<Integer> unsortedAdapters) {
    List<Integer> result = new ArrayList<>(unsortedAdapters)
    final SOURCE = 0
    final SINK = unsortedAdapters.max() + 3

    result.add(SOURCE)
    result.add(SINK)

    return result.sort()
}
