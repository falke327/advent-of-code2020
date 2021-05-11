package day12

import java.util.regex.Matcher
import java.util.regex.Pattern

class MoveCommand {
    private final static Pattern COMMAND = ~"([EFLNRSW])([0-9]+)"
    private final int DIRECTION_GROUP = 1
    private final int DISTANCE_GROUP = 2
    String direction
    int distance

    MoveCommand(String movement) {
        Matcher commandMatcher = COMMAND.matcher(movement)
        if (commandMatcher.find()) {
            this.direction = commandMatcher.group(DIRECTION_GROUP)
            this.distance = commandMatcher.group(DISTANCE_GROUP) as int
        }
    }
}
