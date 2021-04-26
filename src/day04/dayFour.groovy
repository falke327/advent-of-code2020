package day04

import groovy.transform.Field

@Field static final String SINGLE_NEWLINE = "\r\n"
@Field static final String ENTRY_SEPARATOR = /\r\n\r\n/

List<String> testInput = new File("testFour.txt").text.split(ENTRY_SEPARATOR)
int counter1 = countCompleteEntries(testInput)
assert 2 == counter1

List<String> testInput2 = new File("testFour2.txt").text.split(ENTRY_SEPARATOR)
int counter2 = countValidEntries(testInput2)
assert 4 == counter2

List<String> input = new File("inputFour.txt").text.split(ENTRY_SEPARATOR)
int result1 = countCompleteEntries(input)
println("The passport List contains $result1 complete passports")

int result2 = countValidEntries(input)
println("The passport List contains $result2 valid passports")

/**
 * <p>Checks every passport if it contains all of the relevant fields.</p>
 * <p>"ecl:gry pid:860033327 eyr:2020 hcl:#fffffd byr:1937 iyr:2017 cid:147 hgt:183cm" will result <b>true</b>,<br/>
 * "hcl:#cfa07d eyr:2025 pid:166559648 iyr:2011 ecl:brn hgt:59in" misses "byr" and will result <b>false</b>.</p>
 *
 * @return the count of passports containing all relevant fields
 */
static int countCompleteEntries(List<String> input) {
    List<Passport> passports = parsePassports(input)

    return passports.count { passport ->
        passport.isComplete()
    }
}

/**
 * <p>Checks every passport if it matches all validity criteria.</p>
 *
 * @return the count of passports that are valid by criteria
 */
static int countValidEntries(List<String> lines) {
    List<Passport> passports = parsePassports(lines)

    return passports.count { passport ->
        passport.isComplete() && passport.isValid()
    }
}

/**
 * <p>Parses a passport String into a {@link day04.Passport} Object</p>
 *
 * @return a List of Passports
 */
static List<Passport> parsePassports(List<String> lines) {
    List<Passport> passports = []
    lines.each {line ->
        passports.add(
                new Passport(line.replace(SINGLE_NEWLINE," "))
        )
    }
    return passports
}
