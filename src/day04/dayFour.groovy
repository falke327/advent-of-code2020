package day04

import groovy.transform.Field

@Field static final List<String> RELEVANT_FIELDS = ["byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"] // cid

List<String> testInput = new File("testFour.txt").readLines()
int counter1 = countCompleteEntries(testInput)
assert counter1 == 2

List<String> input = new File("inputFour.txt").readLines()
int result1 = countCompleteEntries(input)
println( "The passport List contains $result1 complete passports")

/**
 * <p>Checks every passport line if it contains all of the relevant fields.</p>
 * <p>"ecl:gry pid:860033327 eyr:2020 hcl:#fffffd byr:1937 iyr:2017 cid:147 hgt:183cm" will result <b>true</b>,<br/>
 * "hcl:#cfa07d eyr:2025 pid:166559648 iyr:2011 ecl:brn hgt:59in" misses "byr" and will result <b>false</b>.</p>
 *
 * @return the count of passports containing all relevant fields
 */
static int countCompleteEntries(List<String> input) {
    List<String> passports = packLinesToPassports(input)

    return passports.count { passport ->
        RELEVANT_FIELDS.every { field ->
            passport.contains(field)
        }
    }
}

/**
 * <p>Consumes a List of possibly multiline passports separated by empty lines
 * and transforms it to a List of passport oneliners.</p>
 *
 * @return a List of passport oneliners
 */
static List<String> packLinesToPassports(List<String> lines) {
    List<String> buffer = []
    List<String> passports = []

    lines.each { line ->
        if (line.isEmpty()) {
            passports.add(bufferToString(buffer))
            buffer.clear()
        } else {
            buffer.add(line)
        }
    }
    // since a passport List doesn't end with an empty line we have to add the last entry manually like this
    if (!buffer.isEmpty()) {
        passports.add(bufferToString(buffer))
    }

    return passports
}

/**
 * <p>Flattens a List of passport lines into one single String where all key-value pairs are separated by a space.</p>
 * <p>[ "hcl:#ae17e1 iyr:2013",<br/>
 * "eyr:2024",<br/>
 * "ecl:brn pid:760753108 byr:1931",</br>
 * "hgt:179cm" ]<br/>
 * will result in "hcl:#ae17e1 iyr:2013 eyr:2024 ecl:brn pid:760753108 byr:1931 hgt:179cm"</p>
 *
 * @return A String representation of a passport
 */
static String bufferToString(List<String> buffer) {
    return buffer.toString()
            .replace(",", "")
            .replace("[", "")
            .replace("]", "")
}
