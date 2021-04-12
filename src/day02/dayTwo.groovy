package day02

import java.util.regex.Matcher

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


def collectMatchingGroups(List<String> input) {
    def pattern = ~"([0-9]+)-([0-9]+) ([a-z]): ([a-z]+)"
    def result = []

    for (String line : input) {
        List<String> groups = new ArrayList<>()
        Matcher m = pattern.matcher(line)
        while (m.find()) {
            for (int i = 0; i <= m.groupCount(); i++) {
                groups.add(m.group(i))
            }
        }
        result.add(groups)
    }

    return result
}

int validPasswordsByCount(List<String> input) {
    int counter = 0
    def matcherGroups = collectMatchingGroups(input)

    for (List<String> groups : matcherGroups) {
        if ((groups[1] as int) <= groups[4].count(groups[3]) && groups[4].count(groups[3]) <= (groups[2] as int)) {
            counter++
        }
    }

    return counter
}

int validPasswordsByPosition(List<String> input) {
    int counter = 0
    def matcherGroups = collectMatchingGroups(input)

    for (List<String> groups : matcherGroups) {
        if (
        groups[4].charAt((groups[1] as int) - 1).toString() == groups[3] &&
                groups[4].charAt((groups[2] as int) - 1).toString() != groups[3] ||
                groups[4].charAt((groups[1] as int) - 1).toString() != groups[3] &&
                groups[4].charAt((groups[2] as int) - 1).toString() == groups[3]
        ) {
            counter++
        }
    }

    return counter
}
