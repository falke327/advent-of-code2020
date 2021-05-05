package day02

/**
 * A helper class for giving the pattern groups a naming.
 * Each String is matched to a Regex Pattern. This contains four groups representing a lower and upper border,
 * a validation character and the passPhrase itself.
 */
class LineGrouping {
    final String passPhrase
    final char character
    final int lowerBorder
    final int upperBorder

    LineGrouping(String passPhrase, char character, int lowerBorder, int upperBorder) {
        this.passPhrase = passPhrase
        this.character = character
        this.lowerBorder = lowerBorder
        this.upperBorder = upperBorder
    }

    int countCharsInPassphrase() {
        return passPhrase.count(character as String)
    }
}
