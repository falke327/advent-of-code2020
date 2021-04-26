package day07

import java.util.regex.Matcher
import java.util.regex.Pattern

List<String> testInput = new File("testSeven.txt").readLines()
assert 4 == countBagsForColor(testInput, "shiny gold")

assert 32 == countInnerBags(testInput, "shiny gold")

List<String> input = new File("inputSeven.txt").readLines()
int result1 = countBagsForColor(input, "shiny gold")
println("In the input file there are $result1 possible containers for shiny golden bags")

int result2 = countInnerBags(input, "shiny gold")
println("One bag in shiny gold has to contain $result2 inner bags")

/**
 * <p>Counts all the color-codes in the input List when they or their children contain the expected color</p>
 */
static int countBagsForColor(List<String> input, String expectedColor) {
    Map<String, Map> graph = transformToGraph(input)

    return graph.keySet().count { node ->
        Map<String, Boolean> recursionBuffer = [:]
        containsColor(node, graph, expectedColor, recursionBuffer)
    }
}

/**
 * <p>Recursively checks if the current node or one of its children contains the expected color</p>
 */
static boolean containsColor(String currentNode, Map<String, Map> graph, String expectedColor, Map<String, Boolean> recursionBuffer) {
    if (recursionBuffer.containsKey(currentNode)) {
        return recursionBuffer.get(currentNode)
    }

    boolean result
    Set<String> children = graph.get(currentNode).keySet()
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
 * <p>Uses the recursive count on graph to get all inner bags and subtracts the most outer bag.</p>
 */
static int countInnerBags(List<String> input, String outerBagColor) {
    Map<String, Map> graph = transformToGraph(input)

    return countInnerBagsForGraph(graph, outerBagColor) - 1
}

/**
 * <p>Recursively counts the number of inner bags in the graph of bags for given outer Bag</p>
 */
static int countInnerBagsForGraph(Map<String, Map> graph, String outerBagColor) {
    Map<String, Integer> children = graph.get(outerBagColor)

    if (children.isEmpty()) {
        return 1
    } else {
        return 1 + (children.keySet().sum{ key ->
            int count = children.get(key) as int
            return count * countInnerBagsForGraph(graph, key as String)
        } as int)
    }
}

/**
 * <p>Transforms a line of text into a Map with node name as key and child nodes with count as values.
 * The first color code in each line will be the key, all the
 * others will be passed as a Map of color codes and inner counts.</p>
 * <p>On leaf nodes this inner Map will be empty</p>
 */
static Map<String, Map> transformToGraph(List<String> input) {
    Pattern pattern = ~"(\\d+) (.+?) bag"
    Map<String, Map> container = input.collectEntries { line ->
        List<String> content = line.split(" bags contain")
        Matcher m = pattern.matcher(content[1])
        Map<String, Integer> innerBags = [:]
        innerBags.putAll(m.findAll().collectEntries { groups ->
            [groups[2], groups[1]]
        })

        return [content[0], innerBags]
    }
    return container
}
