package day11

List<String> testInput = new File("testEleven.txt").readLines()
WaitingArea testWaitingArea = new WaitingArea(testInput)
WaitingArea testWaitingArea2 = new WaitingArea(testInput)
assert 37 == countOccupiedSeatsInStableStatusByNeighborRule(testWaitingArea)
assert 26 == countOccupiedSeatsInStableStatusByVisibleRule(testWaitingArea2)

List<String> input = new File("inputEleven.txt").readLines()
WaitingArea waitingArea = new WaitingArea(input)
int result1 = countOccupiedSeatsInStableStatusByNeighborRule(waitingArea)
println("There are $result1 occupied seats for neighbor rule")
WaitingArea waitingArea2 = new WaitingArea(input)
int result2 = countOccupiedSeatsInStableStatusByVisibleRule(waitingArea2)
println("There are $result2 occupied seats for visible rule")

/**
 * In Neighbor rule every seat is only affected by its adjacent seats
 */
static int countOccupiedSeatsInStableStatusByNeighborRule(WaitingArea waitingArea) {
    List<String> oldStatus = []
    List<String> currentIteration = waitingArea.getCurrentStatus()

    while (oldStatus != currentIteration) {
        oldStatus = currentIteration
        waitingArea.updateAllSeatsByNeighbor()
        currentIteration = waitingArea.getCurrentStatus()
    }

    return waitingArea.countOccupiedSeats()
}

static int countOccupiedSeatsInStableStatusByVisibleRule(WaitingArea waitingArea) {
    List<String> oldStatus = []
    List<String> currentIteration = waitingArea.getCurrentStatus()

    while (oldStatus != currentIteration) {
        oldStatus = currentIteration
        waitingArea.updateAllSeatsByVisibility()
        currentIteration = waitingArea.getCurrentStatus()
    }

    return waitingArea.countOccupiedSeats()
}
