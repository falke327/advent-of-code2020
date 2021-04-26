package day09

List<String> testInput = new File("testNine.txt").readLines()
assert 127 == findFailingXmasCode(testInput, 5)

List<String> reducedTest = testInput.subList(0, testInput.indexOf(127 as String))
List partialTest2 = findSumList(reducedTest, 127)
assert 62 == getEncryptionWeakness(partialTest2)

List<String> input = new File("inputNine.txt").readLines()
int result1 = findFailingXmasCode(input, 25)
println("Failing Xmas code found. Value is $result1")

List<String> reducedInput = input.subList(0, input.indexOf(result1 as String))
List partialResult2 = findSumList(reducedInput, result1)
println(partialResult2)
int result2 = getEncryptionWeakness(partialResult2)
println("Found weakness with value $result2")


/**
 * <p>Checks each entry in a List after the offset if it is in xmas code.</p>
 *
 * @return the value that doesn't match, or -1 if not found
 */
static int findFailingXmasCode(List input, int offset) {
    int startIndex = 0
    int endIndex = offset
    for (int i = offset; i < input.size(); i++) {
        int checkValue = input[i] as int
        if (!isXmasCode(input.subList(startIndex, endIndex), checkValue)) {
            return checkValue
        }
        startIndex++
        endIndex++
    }

    return -1
}

/**
 * <p>Checks if checkValue can be build as sum of two List entries</p>
 */
static boolean isXmasCode(List subList, int checkValue) {
    for (int a = 0; a < subList.size() - 1; a++) {
        for (int b = a + 1; b < subList.size(); b ++) {
            int firstSummand = subList[a] as int
            int secondSummand = subList[b] as int
            if (firstSummand + secondSummand == checkValue) {
                return true
            }
        }
    }
    return false
}

/**
 * <p>Searches for a sublist that sums to the checkValue.</p>
 * <p>Increases sublist size after each iteration, starting with 3</p>
 *
 * @return a matching sublist or empty List if not found
 */
static List findSumList(List input, int checkValue) {
    int sublistSize = 3
    while(sublistSize < input.size()) {
        for (int i = 0; i + sublistSize < input.size(); i++) {
            List currentSublist = input.subList(i, i + sublistSize)
            if (currentSublist.sum { it as int } == checkValue) {
                return currentSublist
            }
        }
        sublistSize++
    }
    return []
}

/**
 * Adds the List minimum to the List maximum
 */
static int getEncryptionWeakness(List input) {
    int minimum = input.min() as int
    int maximum = input.max() as int
    return minimum + maximum
}
