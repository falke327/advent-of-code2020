package day11

class WaitingArea {
    static final char EMPTY_SEAT = 'L'
    static final char FLOOR = '.'
    static final char OCCUPIED_SEAT = '#'
    static final char INVALID_VALUE = 'X'

    private char[][] currentStatus
    private final int xSize
    private final int ySize

    WaitingArea(List<String> seatArrangement) {
        this.currentStatus = seatArrangement.collect { line ->
            line.toCharArray()
        } as char[][]
        this.xSize = this.currentStatus[0].size()
        this.ySize = this.currentStatus.length
    }

    int countOccupiedSeats() {
        return this.currentStatus.collect { row ->
            row.count(OCCUPIED_SEAT)
        }.sum() as int
    }

    void updateAllSeatsByNeighbor() {
        char[][] deepCopy = this.currentStatus.collect { it.collect() }

        for (int yPosition = 0; yPosition < this.ySize; yPosition++) {
            for (int xPosition = 0; xPosition < this.xSize; xPosition++) {
                this.currentStatus[yPosition][xPosition] = updateSeatStatus(xPosition, yPosition, deepCopy)
            }
        }
    }

    private char updateSeatStatus(int xPosition, int yPosition, char[][] oldStatus) {
        char currentSeat = oldStatus[yPosition][xPosition]

        switch (currentSeat) {
            case EMPTY_SEAT:
                return (countOccupiedNeighbors(xPosition, yPosition, oldStatus) == 0) ? OCCUPIED_SEAT : EMPTY_SEAT
            case FLOOR:
                return FLOOR
            case OCCUPIED_SEAT:
                return (countOccupiedNeighbors(xPosition, yPosition, oldStatus) >= 4) ? EMPTY_SEAT : OCCUPIED_SEAT
        }

        return INVALID_VALUE
    }

    private int countOccupiedNeighbors(int xPosition, int yPosition, char[][] oldStatus) {
        int occupiedSeats = ((yPosition - 1)..(yPosition + 1)).collect { yCoordinate ->
            (isValidY(yCoordinate)) ? countLineOccurrences(xPosition, oldStatus[yCoordinate]) : 0
        }.sum() as int

        // don't count Position itself
        if (oldStatus[yPosition][xPosition] == OCCUPIED_SEAT) {
            occupiedSeats--
        }

        return occupiedSeats
    }

    private boolean isValidY(int yPosition) {
        return yPosition >= 0 && yPosition < this.ySize
    }

    private int countLineOccurrences(int xPosition, char[] line) {
        return ((xPosition - 1)..(xPosition + 1)).count { xCoordinate ->
            isValidX(xCoordinate) && line[xCoordinate] == OCCUPIED_SEAT
        }
    }

    private boolean isValidX(int xPosition) {
        return xPosition >= 0 && xPosition < this.xSize
    }

    List<String> getCurrentStatus() {
        return this.currentStatus.collect { it.toString() }
    }

}
