package day12

class Waypoint {
    private static final List<String> SIMPLE_MOVEMENTS = ["N", "E", "S", "W"]
    private static final List<String> TURNS = ["R", "L"]
    private static final String FORWARD = "F"
    private int eastWestDistance
    private int northSouthDistance
    private int eastWestPosition
    private int northSouthPosition

    Waypoint(int eastWestDistance, int northSouthDistance) {
        this.eastWestDistance = eastWestDistance
        this.northSouthDistance = northSouthDistance
        this.eastWestPosition = 0
        this.northSouthPosition = 0
    }

    void performMovementCommand(String movement) {
        MoveCommand moveCommand = new MoveCommand(movement)
        String currentDirection = moveCommand.getDirection()
        int currentDistance = moveCommand.getDistance()

        if (SIMPLE_MOVEMENTS.contains(currentDirection)) {
            moveWaypoint(Direction.fromShortCut(currentDirection), currentDistance)
        }
        if (FORWARD == currentDirection) {
            moveShip(currentDistance)
        }
        if (TURNS.contains(currentDirection)) {
            turnWaypoint(currentDirection, currentDistance)
        }
    }

    int getManhattenDistance() {
        return Math.abs(northSouthPosition) + Math.abs(eastWestPosition)
    }

    private void moveWaypoint(Direction direction, int distance) {
        switch (direction) {
            case Direction.NORTH:
                this.northSouthDistance -= distance
                break
            case Direction.EAST:
                this.eastWestDistance -= distance
                break
            case Direction.SOUTH:
                this.northSouthDistance += distance
                break
            case Direction.WEST:
                this.eastWestDistance += distance
                break
        }
    }

    private void moveShip(int distanceFactor) {
        this.northSouthPosition += distanceFactor * this.northSouthDistance
        this.eastWestPosition += distanceFactor * this.eastWestDistance
    }

    private void turnWaypoint(String direction, int angle) {
        if (angle == 180) {
            this.eastWestDistance = this.eastWestDistance * -1
            this.northSouthDistance = this.northSouthDistance * -1
        }
        if ((direction == "R" && angle == 90) || (direction == "L" && angle == 270)) {
            int tmp = this.northSouthDistance
            this.northSouthDistance = this.eastWestDistance * -1
            this.eastWestDistance = tmp
        }
        if ((direction == "R" && angle == 270) || (direction == "L" && angle == 90)) {
            int tmp = this.northSouthDistance
            this.northSouthDistance = this.eastWestDistance
            this.eastWestDistance = tmp * -1
        }
    }
}
