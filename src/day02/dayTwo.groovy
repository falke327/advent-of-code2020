package day02

import groovy.transform.Field

import java.util.regex.Matcher
import java.util.regex.Pattern

@Field static final Pattern PATTERN = ~"([0-9]+)-([0-9]+) ([a-z]): ([a-z]+)"

List<String> testInput = ["1-3 a: abcde", "1-3 b: cdefg", "2-9 c: ccccccccc"]
int validTest1 = validPasswordsByCount(testInput)
assert validTest1 == 2
int validTest2 = validPasswordsByPosition(testInput)
assert validTest2 == 1

List<String> input = new File("inputTwo.txt").readLines()
int result1 = validPasswordsByCount(input)
println("In the inputfile there are $result1 valid passwords for count criteria.")
int result2 = validPasswordsByPosition(input)
println("In the inputfile there are $result2 valid passwords for position criteria.")


static List<LineGrouping> collectMatchingGroups(List<String> input) {
    List<LineGrouping> result = []

    for (String line : input) {
        Matcher m = PATTERN.matcher(line)

        while (m.find()) {
            int lowerBorder = m.group(1) as int
            int upperBorder = m.group(2) as int
            char character = m.group(3) as char
            String passPhrase = m.group(4)
            result.add(new LineGrouping(passPhrase, character, lowerBorder, upperBorder))
        }
    }

    return result
}

static int validPasswordsByCount(List<String> input) {
    List<LineGrouping> matcherGroups = collectMatchingGroups(input)

    return matcherGroups.count { lineGrouping ->
        lineGrouping.getLowerBorder() <= lineGrouping.getCharCount() && lineGrouping.getCharCount() <= lineGrouping.getUpperBorder()
    }
}

static int validPasswordsByPosition(List<String> input) {
    List<LineGrouping> matcherGroups = collectMatchingGroups(input)

    return matcherGroups.count { lineGrouping ->
        lineGrouping.getPassPhrase().charAt(lineGrouping.getLowerBorder() - 1) == lineGrouping.getCharacter() &&
                lineGrouping.getPassPhrase().charAt(lineGrouping.getUpperBorder() - 1) != lineGrouping.getCharacter() ||
                lineGrouping.getPassPhrase().charAt(lineGrouping.getLowerBorder() - 1) != lineGrouping.getCharacter() &&
                lineGrouping.getPassPhrase().charAt(lineGrouping.getUpperBorder() - 1) == lineGrouping.getCharacter()
    }
}
