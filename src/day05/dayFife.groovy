package day05

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

List<String> input = new File("inputFife.txt").readLines()
List<Integer> seatList = input.collect { line ->
    calculateSeatNumber(line)
}

int highestSeat = seatList.max()
println("Highest available seat is $highestSeat")

int first = seatList.max()
int last = seatList.min()
// subtract used seats from all available seats should result in a list containing only one seat that has to be mine
int mySeat = ((first..last).toList() - seatList).first()
println("My seat is $mySeat")

/**
 * <p>Calculates the seatNumber from given search String.</p>
 * <p>Search String must match a sequence of F and B followed by a sequence of L and R</p>
 * <p>Valid are "FFBBBLRL", "FL", "BBBLR"</p>
 * <p>Invalid are "FRLB", "ADFFRL", "fbflr"</p>
 * <p>Row coding is extracted from the FB sequence, column coding from the LR-sequence</p>
 *
 * @return decoded row * 8 + decoded column
 */
static int calculateSeatNumber(String input) {
    int row = getRow(input)
    int col = getCol(input)
    return row * 8 + col
}

/**
 * Calculates the row number from given input.
 */
static int getRow(String input) {
    def pattern = ~"([F,B]+)"
    String rowDefinition = extractMatch(input, pattern)
    int stepSize = (int) ((2**(rowDefinition.length())) / 2)

    return findPosition(stepSize, rowDefinition, "B")
}

/**
 * Calculates the column number from given input.
 */
static int getCol(String input) {
    def pattern = ~"([L,R]+)"
    String colDefinition = extractMatch(input, pattern)
    int stepSize = (int) ((2**(colDefinition.length())) / 2)

    return findPosition(stepSize, colDefinition, "R")
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
        //println("Current digit is + digit)
        if (digit == upperDigit) {
            pos += stepsize
        }
        stepsize = (int) (stepsize / 2)
    }

    return pos
}
