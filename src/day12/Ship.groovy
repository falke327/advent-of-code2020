package day12

class Ship {
    private static final List<String> SIMPLE_MOVEMENTS = ["N", "E", "S", "W"]
    private static final List<String> TURNS = ["R", "L"]
    private static final String FORWARD = "F"
    private static final int FULL_CIRCLE = 360
    private Direction heading
    private int eastWestPosition
    private int northSouthPosition

    Ship(Direction heading) {
        this.heading = heading
        this.eastWestPosition = 0
        this.northSouthPosition = 0
    }

    void performMovementCommand(String movement) {
        MoveCommand moveCommand = new MoveCommand(movement)
        String currentDirection = moveCommand.getDirection()
        int currentDistance = moveCommand.getDistance()

        if (SIMPLE_MOVEMENTS.contains(currentDirection)) {
            moveShip(Direction.fromShortCut(currentDirection), currentDistance)
        }
        if (FORWARD == currentDirection) {
            moveShip(this.heading, currentDistance)
        }
        if (TURNS.contains(currentDirection)) {
            turnShip(currentDirection, currentDistance)
        }
    }

    int getManhattenDistance() {
        return Math.abs(northSouthPosition) + Math.abs(eastWestPosition)
    }

    private void moveShip(Direction direction, int distance) {
        switch (direction) {
            case Direction.NORTH:
                this.northSouthPosition -= distance
                break
            case Direction.EAST:
                this.eastWestPosition -= distance
                break
            case Direction.SOUTH:
                this.northSouthPosition += distance
                break
            case Direction.WEST:
                this.eastWestPosition += distance
                break
        }
    }

    private void turnShip(String currentDirection, int angle) {
        if (currentDirection == "R") {
            int newAngle = (this.heading.getDirection() + angle) % FULL_CIRCLE
            this.heading = Direction.fromAngle(newAngle)
        }
        if (currentDirection == "L") {
            int newAngle = (this.heading.getDirection() - angle + FULL_CIRCLE) % FULL_CIRCLE
            this.heading = Direction.fromAngle(newAngle)
        }
    }
}
