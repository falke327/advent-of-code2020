package day11

import groovy.transform.Field

@Field static final char EMPTY_SEAT = 'L'
@Field static final char FLOOR = '.'
@Field static final char OCCUPIED_SEAT = '#'
@Field static final char INVALID_VALUE = 'X'

List<String> testInput = new File("testEleven.txt").readLines()
assert 37 == countOccupiedSeatsInStableStatus(testInput)

//List<String> input = new File("inputEleven.txt").readLines()
//int result1 = countOccupiedSeatsInStableStatus(input)
//println("There are $result1 occupied seats in the stable state")

// TODO: extract into Class

static int countOccupiedSeatsInStableStatus(List<String> seatArrangement) {
    List<String> stableStatus = calculateStableStatus(seatArrangement)

    return countOverallOccupiedSeats(stableStatus)
}

static List<String> calculateStableStatus(List<String> seatArrangement) {
    List<String> oldStatus = []
    List<String> currentIteration = seatArrangement

    while (oldStatus != currentIteration) {
        oldStatus = currentIteration
        currentIteration = updateAllSeats(currentIteration)
    }

    return currentIteration
}

static int countOverallOccupiedSeats(List<String> currentStatus) {
    int counter = 0

    currentStatus.each { seatRow ->
        counter += seatRow.count(OCCUPIED_SEAT as String)
    }

    return counter
}

static List<String> updateAllSeats(List<String> currentStatus) {
    List<String> statusCopy = new ArrayList<>(currentStatus)
    final int xSize = currentStatus[0].length()

    for (int yPosition = 0; yPosition < statusCopy.size(); yPosition++) {
        for (int xPosition = 0; xPosition < xSize; xPosition++) {
            char[] line = statusCopy[yPosition] as char[]
            line[xPosition] = updateSeatStatus(xPosition, yPosition, currentStatus)
            statusCopy[yPosition] = line as String
        }
    }

    return statusCopy
}

static char updateSeatStatus(int xPosition, int yPosition, List<String> currentStatus) {
    char currentSeat = currentStatus[yPosition].charAt(xPosition)
    switch (currentSeat) {
        case EMPTY_SEAT:
            return handleEmptySeat(xPosition, yPosition, currentStatus)
        case FLOOR:
            return FLOOR
        case OCCUPIED_SEAT:
            return handleOccupiedSeat(xPosition, yPosition, currentStatus)
    }

    return INVALID_VALUE
}

static char handleEmptySeat(int xPosition, int yPosition, List<String> currentStatus) {
    if (countOccupiedNeighbors(xPosition, yPosition, currentStatus) == 0) {
        return OCCUPIED_SEAT
    }
    return EMPTY_SEAT
}

static char handleOccupiedSeat(int xPosition, int yPosition, List<String> currentStatus) {
    if (countOccupiedNeighbors(xPosition, yPosition, currentStatus) >= 4) {
        return EMPTY_SEAT
    }
    return OCCUPIED_SEAT
}

static int countOccupiedNeighbors(int xPosition, int yPosition, List<String> currentStatus) {
    int counter = 0
    final int ySize = currentStatus.size()

    if (yPosition - 1 >= 0) {
        counter += countLineOccurrences(xPosition, currentStatus[yPosition - 1])
    }

    counter += countLineOccurrences(xPosition, currentStatus[yPosition])
    // don't count Position itself
    if (currentStatus[yPosition].charAt(xPosition) == OCCUPIED_SEAT) {
        counter--
    }

    if (yPosition + 1 < ySize) {
        counter += countLineOccurrences(xPosition, currentStatus[yPosition + 1])
    }

    return counter
}

static int countLineOccurrences(int xPosition, String line) {
    final int xSize = line.length()
    final int xMin = (xPosition - 1 >= 0) ? (xPosition - 1) : 0
    final int xMax = (xPosition + 1 < xSize) ? (xPosition + 1) : xPosition

    return line.substring(xMin, xMax + 1).count(OCCUPIED_SEAT as String)
}
