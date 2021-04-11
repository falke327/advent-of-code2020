package day02

import java.util.regex.Matcher

List<String> testInput = ["1-3 a: abcde", "1-3 b: cdefg", "2-9 c: ccccccccc"]
int validTest1 = validPasswordsByCount(testInput)
assert validTest1 == 2

List<String> input = new File("inputTwo.txt").readLines()
int result1 = validPasswordsByCount(input)
println("In the inputfile there are $result1 valid passwords for count criteria.")

int validPasswordsByCount(List<String> input) {
    def pattern = ~"([0-9]+)-([0-9]+) ([a-z]): ([a-z]+)"
    int counter = 0

    for (String line : input) {
        List<String> groups = new ArrayList<>()
        Matcher m = pattern.matcher(line)
        while (m.find()) {
            int tmp = m.groupCount()
            for (int i = 0; i <= tmp; i++) {
                groups.add(m.group(i))
            }
        }
        if ((groups[1] as int) <= groups[4].count(groups[3]) && groups[4].count(groups[3]) <= (groups[2] as int)) {
            counter++
        }
    }

    return counter
}

int validPasswordsByPosition(List<String> input) {
    return 0
}