package day06

List<String> testInput = new File("testSix.txt").text.split(/\r\n\r\n/)
assert countLettersForGroups(testInput).sum() == 11
assert countIntersectsForGroups(testInput).sum() == 6

List<String> input = new File("inputSix.txt").text.split(/\r\n\r\n/)
int result1 = countLettersForGroups(input).sum() as int
println("The overall customs sum is $result1")
int result2 = countIntersectsForGroups(input).sum() as int
println("The overall count of intersections is $result2")

/**
 * <p>Consumes a List of answers in groups split by empty lines
 * and counts the distinct digits for each group</p>
 * <p>[ab, acd] -> ab + acd -> abcd -> 4</p>
 *
 * @return a List counts for each group
 */
static List<Integer> countLettersForGroups(List<String> lines) {
    List<Integer> groups = []
    lines.each {line ->
        Set<String> buffer = []
        List<String> personAnswers = line.split()

        personAnswers.each {answer ->
            buffer.addAll(answer.toSet())
        }
        groups.add(new HashSet(buffer).size())
    }
    return groups
}

/**
 * <p>Consumes a List of answers in groups split by empty lines
 * and counts the intersect digits for each group</p>
 * <p>[ab, acd] -> ab + acd -> a -> 1</p>
 *
 * @return a List counts for each group
 */
static List<Integer> countIntersectsForGroups(List<String> lines) {
    List<Integer> groups = []
    lines.each { line ->
        List<List> bufferedAnswers = []
        List<String> personAnswers = line.split()

        personAnswers.each {answer ->
            bufferedAnswers.add(answer.toList())
        }
        groups.add(new HashSet(getOverallIntersection(bufferedAnswers)).size())
    }
    return groups
}

/**
 * <p>Consumes a buffer of character Lists and returns the List of intersections of all</p>
 * <p>["abcd", "bcde", "cdef"] -> ["bcd", "cdef"] -> ["cd"]</p>
 *
 * @return the intersected List
 */
static Set<String> getOverallIntersection(List<List> bufferedAnswers) {
    Set<String> result = ('a'..'z').toList()
    bufferedAnswers.each {answer->
        result = result.intersect(answer)
    }
    return result
}
