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
        this.xSize = this.currentStatus[0].length
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
                this.currentStatus[yPosition][xPosition] = updateSeatStatusByNeighbor(xPosition, yPosition, deepCopy)
            }
        }
    }

    void updateAllSeatsByVisibility() {
        char[][] deepCopy = this.currentStatus.collect { it.collect() }

        for (int yPosition = 0; yPosition < this.ySize; yPosition++) {
            for (int xPosition = 0; xPosition < this.xSize; xPosition++) {
                this.currentStatus[yPosition][xPosition] = updateSeatStatusByVisibility(xPosition, yPosition, deepCopy)
            }
        }
    }

    List<String> getCurrentStatus() {
        return this.currentStatus.collect { it.toString() }
    }

    private char updateSeatStatusByNeighbor(int xPosition, int yPosition, char[][] oldStatus) {
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

    private char updateSeatStatusByVisibility(int xPosition, int yPosition, char[][] oldStatus) {
        char currentSeat = oldStatus[yPosition][xPosition]

        switch (currentSeat) {
            case EMPTY_SEAT:
                return (countOccupiedNeighborsInSight(xPosition, yPosition, oldStatus) == 0) ? OCCUPIED_SEAT : EMPTY_SEAT
            case FLOOR:
                return FLOOR
            case OCCUPIED_SEAT:
                return (countOccupiedNeighborsInSight(xPosition, yPosition, oldStatus) >= 5) ? EMPTY_SEAT : OCCUPIED_SEAT
        }

        return INVALID_VALUE
    }

    private int countOccupiedNeighbors(int xPosition, int yPosition, char[][] oldStatus) {
        int occupiedSeats = ((yPosition - 1)..(yPosition + 1)).collect { yCoordinate ->
            (isValidY(yCoordinate)) ? countNeighboringLineOccurrences(xPosition, oldStatus[yCoordinate]) : 0
        }.sum() as int

        // don't count Position itself
        if (oldStatus[yPosition][xPosition] == OCCUPIED_SEAT) {
            occupiedSeats--
        }

        return occupiedSeats
    }

    private int countNeighboringLineOccurrences(int xPosition, char[] line) {
        return ((xPosition - 1)..(xPosition + 1)).count { xCoordinate ->
            isValidX(xCoordinate) && line[xCoordinate] == OCCUPIED_SEAT
        }
    }

    private int countOccupiedNeighborsInSight(int xPosition, int yPosition, char[][] oldStatus) {
        List<Character> seats = []

        for (int yOffset in (-1..1)) {
            for (int xOffset in (-1..1)) {
                seats.add(findSeat(xPosition, yPosition, oldStatus, xOffset, yOffset))
            }
        }

        int occupiedSeats = seats.count { it == OCCUPIED_SEAT } as int
        // don't count Position itself
        if (oldStatus[yPosition][xPosition] == OCCUPIED_SEAT) {
            occupiedSeats--
        }

        return occupiedSeats
    }

    private char findSeat(int xPosition, int yPosition, char[][] oldStatus, int xOffset, int yOffset) {
        int newX = xPosition + xOffset
        int newY = yPosition + yOffset

        if (!isValidY(newY) || !isValidX(newX)) {
            return EMPTY_SEAT
        }
        if (oldStatus[newY][newX] != FLOOR) {
            return oldStatus[newY][newX]
        }
        return findSeat(newX, newY, oldStatus, xOffset, yOffset)
    }

    private boolean isValidY(int yPosition) {
        return yPosition >= 0 && yPosition < this.ySize
    }

    private boolean isValidX(int xPosition) {
        return xPosition >= 0 && xPosition < this.xSize
    }
}
