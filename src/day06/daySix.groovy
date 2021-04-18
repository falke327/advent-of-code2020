package day06

List<String> testInput = new File("testSix.txt").readLines()
assert countLettersForGroups(testInput).sum() == 11
assert countIntersectsForGroups(testInput).sum() == 6

List<String> input = new File("inputSix.txt").readLines()
int result1 = countLettersForGroups(input).sum() as int
println("The overall customs sum is $result1")
int result2 = countIntersectsForGroups(input).sum() as int
println("The overall count of intersections is $result2")

/**
 * <p>Consumes a List of answers in groups separated by empty lines
 * and counts the distinct digits for each group</p>
 * <p>[ab, acd] -> ab + acd -> abcd -> 4</p>
 *
 * @return a List counts for each group
 */
static List<Integer> countLettersForGroups(List<String> lines) {
    Set<String> buffer = []
    List<Integer> groups = []

    lines.each { line ->
        if (line.isEmpty()) {
            groups.add(new HashSet(buffer).size())
            buffer.clear()
        } else {
            buffer.addAll(line.toSet())
        }
    }
    // since a group List doesn't end with an empty line we have to add the last entry manually like this
    if (!buffer.isEmpty()) {
        groups.add(new HashSet(buffer).size())
    }

    return groups
}

/**
 * <p>Consumes a List of answers in groups separated by empty lines
 * and counts the intersect digits for each group</p>
 * <p>[ab, acd] -> ab + acd -> a -> 1</p>
 *
 * @return a List counts for each group
 */
static List<Integer> countIntersectsForGroups(List<String> lines) {
    List<List> buffer = []
    List<Integer> groups = []

    lines.each { line ->
        if (line.isEmpty()) {
            groups.add(new HashSet(getOverallIntersection(buffer)).size())
            buffer.clear()
        } else {
            buffer.add(line.toList())
        }
    }
    // since a group List doesn't end with an empty line we have to add the last entry manually like this
    if (!buffer.isEmpty()) {
        groups.add(new HashSet(getOverallIntersection(buffer)).size())
    }

    return groups
}

/**
 * <p>Consumes a buffer of character Lists and returns the List of intersections of all</p>
 * <p>["abcd", "bcde", "cdef"] -> ["bcd", "cdef"] -> ["cd"]</p>
 *
 * @return the intersected List
 */
static Set<String> getOverallIntersection(List<List> buffer) {
    Set<String> result = ('a'..'z').toList()
    buffer.each {
        result = result.intersect(it)
    }
    return result
}
