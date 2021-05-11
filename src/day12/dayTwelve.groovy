package day12

List<String> testInput = new File("testTwelve.txt").readLines()
assert 25 == calculateManhattenDistance(testInput)

List<String> input = new File("inputTwelve.txt").readLines()
int result1 = calculateManhattenDistance(input)
println("The manhatten distance for given command set is $result1")

static int calculateManhattenDistance(List<String> commands) {
    Ship ship = new Ship(Direction.EAST)

    commands.each {command->
        ship.performMovementCommand(command)
    }

    return ship.getManhattenDistance()
}