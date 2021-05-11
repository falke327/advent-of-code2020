package day11

List<String> testInput = new File("testEleven.txt").readLines()
assert 37 == countOccupiedSeatsInStableStatus(testInput)

List<String> input = new File("inputEleven.txt").readLines()
int result1 = countOccupiedSeatsInStableStatus(input)
println("There are $result1 occupied seats in the stable state")

static int countOccupiedSeatsInStableStatus(List<String> seatArrangement) {
    WaitingArea waitingArea = new WaitingArea(seatArrangement)

    return calculateStableStatusOccupiedSeats(waitingArea)
}

static int calculateStableStatusOccupiedSeats(WaitingArea waitingArea) {
    List<String> oldStatus = []
    List<String> currentIteration = waitingArea.getCurrentStatus()

    while (oldStatus != currentIteration) {
        oldStatus = currentIteration
        waitingArea.updateAllSeatsByNeighbor()
        currentIteration = waitingArea.getCurrentStatus()
    }

    return waitingArea.countOccupiedSeats()
}
