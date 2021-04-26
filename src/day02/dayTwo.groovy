package day02

import groovy.transform.Field

import java.util.regex.Matcher
import java.util.regex.Pattern

@Field static final Pattern PATTERN = ~"([0-9]+)-([0-9]+) ([a-z]): ([a-z]+)"
@Field static final int LOWER_BORDER = 1
@Field static final int UPPER_BORDER = 2
@Field static final int CHARACTER = 3
@Field static final int PASSPHRASE = 4

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

/**
 * <p>Collects {@link day02.LineGrouping} from a List of Strings.</p>
 * <p>Input entries should be formatted like "1-3 a: abcde"</p>
 *
 * @return a List of {@link day02.LineGrouping} Objects containing all valid matchings
 */
static List<LineGrouping> collectMatchingGroups(List<String> input) {
    List<LineGrouping> validGroupings = []

    for (String line : input) {
        Matcher lineMatcher = PATTERN.matcher(line)
        while (lineMatcher.find()) {
            validGroupings.add(packLineGrouping(lineMatcher))
        }
    }

    return validGroupings
}

/**
 * <p>Extracts the relevant Groups from a Matcher and packages them into {@link day02.LineGrouping}</p>
 * <p>A Matcher on "1-3 a: abcde" is resulting in the matching groups:</p>
 * <ul>
 *     <li>1 as lower border</li>
 *     <li>3 as upper border</li>
 *     <li>a as character of relevance</li>
 *     <li>abcde as the current pass phrase</li>
 * </ul>
 */
static LineGrouping packLineGrouping(Matcher lineMatcher) {
    int lowerBorder = lineMatcher.group(LOWER_BORDER) as int
    int upperBorder = lineMatcher.group(UPPER_BORDER) as int
    char character = lineMatcher.group(CHARACTER) as char
    String passPhrase = lineMatcher.group(PASSPHRASE)

    return new LineGrouping(passPhrase, character, lowerBorder, upperBorder)
}

/**
 * <p>Consumes a List of Strings and calls collectMatchingGroups() to get valid
 * groupings</p>
 * <p>Checks if the pass phrase of each line contains the exact amount of relevant
 * characters that is defined by the lower and upper border</p>
 * <p>"1-3 a: abcde" contains one a, what is between 1 and 3. This will result in true.</p>
 * <p>"1-3 a: aaaaa" contains fife a, what is over the upper border of 3. This will result in false.</p>
 *
 * @return the count of valid lines in the input List
 */
static int validPasswordsByCount(List<String> input) {
    List<LineGrouping> matcherGroups = collectMatchingGroups(input)

    return matcherGroups.count { lineGrouping ->
        lineGrouping.getLowerBorder() <= lineGrouping.getCharCount() && lineGrouping.getCharCount() <= lineGrouping.getUpperBorder()
    }
}

/**
 * <p>Consumes a List of Strings and calls collectMatchingGroups() to get valid
 * groupings</p>
 * <p>Checks if the pass phrase contains the relevant character on either the
 * lower or upper position, but never both at the same time.</p>
 * <p>"1-3 a: abcde" contains an a at position 1 and no a at position 3. This will result in true.</p>
 * <p>"1-3 a: aaaaa" contains an a at position 1 and also on position 3. This will result in false.</p>
 *
 * @return the count of valid lines in the input List
 */
static int validPasswordsByPosition(List<String> input) {
    List<LineGrouping> matcherGroups = collectMatchingGroups(input)

    return matcherGroups.count { lineGrouping ->
        lineGrouping.getPassPhrase().charAt(lineGrouping.getLowerBorder() - 1) == lineGrouping.getCharacter() &&
                lineGrouping.getPassPhrase().charAt(lineGrouping.getUpperBorder() - 1) != lineGrouping.getCharacter() ||
                lineGrouping.getPassPhrase().charAt(lineGrouping.getLowerBorder() - 1) != lineGrouping.getCharacter() &&
                lineGrouping.getPassPhrase().charAt(lineGrouping.getUpperBorder() - 1) == lineGrouping.getCharacter()
    }
}
