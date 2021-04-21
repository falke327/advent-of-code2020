package day07

import java.util.regex.Matcher
import java.util.regex.Pattern

List<String> testInput = new File("testSeven.txt").readLines()
assert countBagsForColor(testInput, "shiny gold") == 4

List<String> input = new File("inputSeven.txt").readLines()
int result1 = countBagsForColor(input, "shiny gold")
println("In the input file there are $result1 possible containers for shiny golden bags")

/**
 * <p>Counts all the color-codes in the input List when they or their children contain the expected color</p>
 */
static int countBagsForColor(List<String> input, String expectedColor) {
    Map graph = transformToGraph(input, false)

    return graph.keySet().count { node ->
        Map<String, Boolean> recursionBuffer = [:]
        containsColor(node, graph, expectedColor, recursionBuffer)
    }
}

/**
 * <p>Recursively checks if the current node or one of its children contains the expected color</p>
 */
static boolean containsColor(String currentNode, Map<String, List> graph, String expectedColor, Map<String, Boolean> recursionBuffer) {
    if (recursionBuffer.containsKey(currentNode)) {
        return recursionBuffer.get(currentNode)
    }

    boolean result
    List<String> children = graph.get(currentNode)
    if (children.isEmpty()) {
        result = false
    } else if (children.contains(expectedColor)) {
        result = true
    } else {
        result = children.collect { child ->
            containsColor(child as String, graph, expectedColor, recursionBuffer)
        }.any()
    }

    recursionBuffer.put(currentNode, result)
    return result
}

/**
 * <p>Transforms a line of text into a map of lists. The first color code in each line will be the key, all the
 * others will be passed as a List of values.</p>
 * <p>On leaf nodes this list will be empty</p>
 */
static Map<String, List> transformToGraph(List<String> input, boolean withInnerCount) {
    Pattern pattern = ~"(\\d+) (.+?) bag"
    Map<String, List> container = input.collectEntries { line ->
        List<String> content = line.split(" bags contain")
        Matcher m = pattern.matcher(content[1])
        List innerBags = []
        if (withInnerCount) {
            // TODO: implement me
        } else {
            innerBags.addAll(m.findAll().collect { groups ->
                groups[2]
            })
        }

        return [content[0], innerBags]
    }
    return container
}

