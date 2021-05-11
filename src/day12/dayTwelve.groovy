package day12

List<String> testInput = new File("testTwelve.txt").readLines()
assert 25 == calculateManhattenDistanceForShip(testInput)
assert 286 == calculateManhattenDistanceForWaypoint(testInput)

List<String> input = new File("inputTwelve.txt").readLines()
int result1 = calculateManhattenDistanceForShip(input)
println("The manhatten distance for given command set by ship is $result1")

int result2 = calculateManhattenDistanceForWaypoint(input)
println("The manhatten distance for given command set by waypoint is $result2")

static int calculateManhattenDistanceForShip(List<String> commands) {
    Ship ship = new Ship(Direction.EAST)

    commands.each { command ->
        ship.performMovementCommand(command)
    }

    return ship.getManhattenDistance()
}

static int calculateManhattenDistanceForWaypoint(List<String> commands) {
    Waypoint waypoint = new Waypoint(-10, -1)

    commands.each { command ->
        waypoint.performMovementCommand(command)
    }

    return waypoint.getManhattenDistance()
}
