package day04

import java.util.regex.Pattern

/**
 * A helper class for evaluating the validity of passport lines.
 */
class Passport {
    static final List<String> RELEVANT_FIELDS = ["byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"] // cid
    static final int LOWER_BIRTHYEAR = 1920
    static final int UPPER_BIRTHYEAR = 2002
    static final int LOWER_ISSUEYEAR = 2010
    static final int UPPER_ISSUEYEAR = 2020
    static final int LOWER_EXPIRATION = 2020
    static final int UPPER_EXPIRATION = 2030
    static final Pattern VALID_HEIGHT = ~"(1[5-9][0-9]cm|[5-7][0-9]in)"
    // TODO: this is exact enough but better should be
    // If cm, the number must be at least 150 and at most 193.
    // If in, the number must be at least 59 and at most 76.
    static final Pattern VALID_HAIR_COLOR = ~"#[0-9a-f]{6}"
    static final List<String> VALID_EYE_COLORS = ["amb", "blu", "brn", "gry", "grn", "hzl", "oth"]
    static final Pattern VALID_PASSPORT_ID = ~"[0-9]{9}"

    final Map<String, String> passportFields

    Passport(String passportLine) {
        this.passportFields = parsePassport(passportLine)
    }

    /**
     * <p>Transforms a passport String into a map.</p>
     * <p>Input String has to be formatted as "key1:value1, "key2:value2, ..."</p>
     *
     * @return a Map representation for quick access of field values
     */
    private static Map<String, String> parsePassport(String passport) {
        String[] parts = passport.split(" ")

        return parts.collectEntries { pair ->
            pair.split(":")
        }
    }

    /**
     * <p>Checks passport if it contains all of the relevant fields.</p>
     * <p>"ecl:gry pid:860033327 eyr:2020 hcl:#fffffd byr:1937 iyr:2017 cid:147 hgt:183cm" will result <b>true</b>,<br/>
     * "hcl:#cfa07d eyr:2025 pid:166559648 iyr:2011 ecl:brn hgt:59in" misses "byr" and will result <b>false</b>.</p>
     *
     * @return true if the passport is complete, false if not
     */
    boolean isComplete() {
        return RELEVANT_FIELDS.every { field ->
            passportFields.keySet().contains(field)
        }
    }

    /**
     * <p>Checks Passport if it matches all validity criteria</p>
     * <p>Validation contains:
     * <ul>
     *     <li>Birth Year</li>
     *     <li>Issue Year</li>
     *     <li>Expiration Year</li>
     *     <li>Height</li>
     *     <li>Hair Color</li>
     *     <li>Eye Color</li>
     *     <li>Passport Id</li>
     * </ul>
     * </p>
     *
     * @return true if the passport is valid, false if not
     */
    boolean isValid() {
        return validBirthYear() && validIssueYear() && validExpirationYear() && validHeight() &&
                validHairColor() && validEyeColor() && validPassportId()
    }

    private boolean validBirthYear() {
        int birthYear = this.passportFields.get("byr") as int
        return birthYear >= LOWER_BIRTHYEAR && birthYear <= UPPER_BIRTHYEAR
    }

    private boolean validIssueYear() {
        int issueYear = this.passportFields.get("iyr") as int
        return issueYear >= LOWER_ISSUEYEAR && issueYear <= UPPER_ISSUEYEAR
    }

    private boolean validExpirationYear() {
        int expirationYear = this.passportFields.get("eyr") as int
        return expirationYear >= LOWER_EXPIRATION && expirationYear <= UPPER_EXPIRATION
    }

    private boolean validHeight() {
        String height = this.passportFields.get("hgt")
        return height?.matches(VALID_HEIGHT)
    }

    private boolean validHairColor() {
        String hairColor = this.passportFields.get("hcl")
        return hairColor.matches(VALID_HAIR_COLOR)
    }

    private boolean validEyeColor() {
        String eyeColor = this.passportFields.get("ecl")
        return VALID_EYE_COLORS.contains(eyeColor)
    }

    private boolean validPassportId() {
        String passportId = this.passportFields.get("pid")
        return passportId.matches(VALID_PASSPORT_ID)
    }
}
