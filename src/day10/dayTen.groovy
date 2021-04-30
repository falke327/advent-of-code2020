package day10

List<Integer> test = new File("testTen.txt").readLines().collect{it as int}
List<Integer> sortedTest = collectSortedAdaptersWithSourceAndSink(test)
assert 7 == countByStepSize(sortedTest, 1)
assert 5 == countByStepSize(sortedTest, 3)
assert 35 == calculateAdapterProduct(sortedTest)

List<Integer> test2 = new File("testTen2.txt").readLines().collect{it as int}
List<Integer> sortedTest2 = collectSortedAdaptersWithSourceAndSink(test2)
assert 220 == calculateAdapterProduct(sortedTest2)

List<Integer> input = new File("inputTen.txt").readLines().collect{it as int}
List<Integer> sortedInput = collectSortedAdaptersWithSourceAndSink(input)
int result1 = calculateAdapterProduct(sortedInput)
println("The product of overall adapter gaps is $result1")

/**
 * The adapter product is calculated as product of one-step adapters by three-step-adapters
 */
static int calculateAdapterProduct(List<Integer> adapters) {
    int oneStepAdapters = countByStepSize(adapters, 1)
    int threeStepAdapters = countByStepSize(adapters, 3)

    return oneStepAdapters * threeStepAdapters
}

static int countByStepSize(List<Integer> adapters, int stepSize) {
    int counter = 0

    for (int i = 0; i < adapters.size() - 1; i++) {
        if (adapters[i+1] - adapters[i] == stepSize) {
            counter++
        }
    }

    return counter
}

static List<Integer> collectSortedAdaptersWithSourceAndSink(List<Integer> unsortedAdapters) {
    final SOURCE = 0
    final SINK = unsortedAdapters.max() + 3
    unsortedAdapters.add(SOURCE)
    unsortedAdapters.add(SINK)
    return unsortedAdapters.sort()
}
