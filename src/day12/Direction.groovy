package day12

enum Direction {
    NORTH(0),
    EAST(90),
    SOUTH(180),
    WEST(270),
    INVALID(-1)

    private int degrees

    Direction(int degrees) {
        this.degrees = degrees
    }

    int getDirection() {
        return this.degrees
    }

    static Direction fromAngle(int degrees) {
        switch (degrees) {
            case NORTH.getDirection():
                return NORTH
            case EAST.getDirection():
                return EAST
            case SOUTH.getDirection():
                return SOUTH
            case WEST.getDirection():
                return WEST
        }
        return INVALID
    }

    static Direction fromShortCut(String shortCut) {
        switch (shortCut) {
            case "N":
                return NORTH
            case "E":
                return EAST
            case "S":
                return SOUTH
            case "W":
                return WEST
        }
        return INVALID
    }
}
