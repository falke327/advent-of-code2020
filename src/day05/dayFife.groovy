package day05

import groovy.transform.Field

import java.util.regex.Pattern

@Field static final Pattern ROW_PATTERN = ~"([F,B]+)"
@Field static final String ROW_UPPER_CHARACTER = "B"
@Field static final Pattern COL_PATTERN = ~"([L,R]+)"
@Field static final String COL_UPPER_CHARACTER = "R"
@Field static final int SEATS_PER_ROW = 8

String testInput = "FBFBBFFRLR"
assert 44 == getRow(testInput)
assert 5 == getCol(testInput)
List<String> testList = ["BFFFBBFRRR", "FFFBBBFRRR", "BBFFBBFRLL"]
List<Integer> testSeats = testList.collect { line ->
    calculateSeatNumber(line)
}
assert testSeats.contains(567)
assert testSeats.contains(119)
assert testSeats.contains(820)
assert 820 == findHighestSeatNumber(testList)

List<String> input = new File("inputFife.txt").readLines()
int result1 = findHighestSeatNumber(input)
println("Highest available seat is $result1")

int result2 = findTheOnlyFreeSeat(input)
println("My seat is $result2")

/**
 * Returns the highest value in a List of seat codings.
 */
static int findHighestSeatNumber(List<String> input) {
    return collectSeatNumbers(input).max()
}

/**
 * Subtract used seats from all available seats
 */
static int findTheOnlyFreeSeat(List<String> input) {
    List<Integer> usedSeats = collectSeatNumbers(input)
    int first = usedSeats.max()
    int last = usedSeats.min()
    List<Integer> availableSeats = (first..last).toList()

    return (availableSeats - usedSeats).first()
}

/**
 * Transforms a List of seat codings into a List of seat numbers
 */
static List<Integer> collectSeatNumbers(List<String> input) {
    return input.collect { line ->
        calculateSeatNumber(line)
    }
}

/**
 * <p>Calculates the seatNumber from given search String.</p>
 * <p>Search String must match a sequence of F and B followed by a sequence of L and R</p>
 * <p>Valid are "FFBBBLRL", "FL", "BBBLR"</p>
 * <p>Invalid are "FRLB", "ADFFRL", "fbflr"</p>
 * <p>Row coding is extracted from the FB sequence, column coding from the LR-sequence</p>
 *
 * @return decoded row * SEATS_PER_ROW + decoded column
 */
static int calculateSeatNumber(String input) {
    int row = getRow(input)
    int col = getCol(input)
    return row * SEATS_PER_ROW + col
}

/**
 * Calculates the row number from given input.
 */
static int getRow(String input) {
    String rowDefinition = extractMatch(input, ROW_PATTERN)
    int initialStepSize = calculateInitialStepsize(rowDefinition)

    return findPosition(initialStepSize, rowDefinition, ROW_UPPER_CHARACTER)
}

/**
 * Calculates the column number from given input.
 */
static int getCol(String input) {
    String colDefinition = extractMatch(input, COL_PATTERN)
    int initialStepSize = calculateInitialStepsize(colDefinition)

    return findPosition(initialStepSize, colDefinition, COL_UPPER_CHARACTER)
}

/**
 * Calculates the needed initial stepsize from the length of the definition String
 */
static int calculateInitialStepsize(String definitionString) {
    return ((2**(definitionString.length())) / 2) as int
}

/**
 * Extracts a substring from given input with regex pattern
 *
 * @return the substring or empty() if there has been no match
 */
static String extractMatch(String input, pattern) {
    def matcher = pattern.matcher(input)
    while (matcher.find()) {
        return matcher.group(1)
    }
    return ""
}

/**
 * <p>Performs a binary search for the position number from given phrase and digit.</p>
 * <p>The digit represents the logical high, where as the other character is handled as logical low.</p>
 * <p>"FBBFFBF" with "F" will be handled as "1001101" -> 77</p>
 *
 * @return the calculated position number
 */
static int findPosition(int stepsize, String phrase, String upperDigit) {
    int pos = 0

    phrase.each { digit ->
        if (digit == upperDigit) {
            pos += stepsize
        }
        stepsize = (stepsize / 2) as int
    }

    return pos
}
