package day06

import groovy.transform.Field

@Field static final String ENTRY_SEPARATOR = /\r\n\r\n/

List<String> testInput = new File("testSix.txt").text.split(ENTRY_SEPARATOR)
assert 11 == sumUp(collectDistinctLettersCountForAnswerGroups(testInput))

assert 6 == sumUp(collectIntersectionCountForAnswerGroups(testInput))

List<String> input = new File("inputSix.txt").text.split(ENTRY_SEPARATOR)
int result1 = sumUp(collectDistinctLettersCountForAnswerGroups(input))
println("The overall customs sum is $result1")

int result2 = sumUp(collectIntersectionCountForAnswerGroups(input))
println("The overall count of intersections is $result2")

static int sumUp(List<Integer> input) {
    return input.sum() as int
}

/**
 * <p>Consumes a List of answers in groups split by empty lines
 * and counts the distinct letters for each group</p>
 * <p>[ab, acd] -> ab + acd -> abcd -> 4</p>
 *
 * @return a List counts for each group
 */
static List<Integer> collectDistinctLettersCountForAnswerGroups(List<String> lines) {
    List<Integer> groups = []

    lines.each { groupString ->
        Set<String> distinctAnswers = collectDistinctAnswers(groupString)
        groups.add(distinctAnswers.size())
    }

    return groups
}

/**
 * Transforms a String representation of an answer group into a Set of distinct answers.
 */
static Set<String> collectDistinctAnswers(String groupString) {
    List<String> personAnswers = groupString.split()
    Set<String> distinctAnswers = []

    personAnswers.each { answer ->
        distinctAnswers.addAll(answer.toSet())
    }

    return distinctAnswers
}

/**
 * <p>Consumes a List of answers in groups split by empty lines
 * and counts the intersect digits for each group</p>
 * <p>[ab, acd] -> ab + acd -> a -> 1</p>
 *
 * @return a List counts for each group
 */
static List<Integer> collectIntersectionCountForAnswerGroups(List<String> lines) {
    List<Integer> groups = []

    lines.each { line ->
        List<Set> individualAnswers = collectIndividualAnswerGroups(line)
        groups.add(collectOverallIntersection(individualAnswers).size())
    }

    return groups
}

/**
 * Transforms a String representation of an answer group into a List of individual answer Sets
 */
static List<Set> collectIndividualAnswerGroups(String groupString) {
    List<String> personAnswers = groupString.split()
    List<Set> individualAnswers = []

    personAnswers.each { answer ->
        individualAnswers.add(answer.toSet())
    }

    return individualAnswers
}

/**
 * <p>Consumes a buffer of character Lists and returns the Set of intersections of all</p>
 * <p>["abcd", "bcde", "cdef"] -> ["bcd", "cdef"] -> ["cd"]</p>
 *
 * @return the intersected List
 */
static Set<String> collectOverallIntersection(List<Set> individualAnswers) {
    Set<String> result = ('a'..'z').toList()

    individualAnswers.each { answer ->
        result = result.intersect(answer)
    }

    return result
}
