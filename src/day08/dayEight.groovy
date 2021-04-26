package day08

import groovy.transform.Field

@Field static final String JUMP_OPERATION = "jmp"
@Field static final String NO_OPERATION = "nop"

List<String> testInput = new File("testEight.txt").readLines()
assert 5 == getLastAccBeforeLoop(testInput)

assert 8 == lastAccWithFixOnTheFly(testInput)

List<String> input = new File("inputEight.txt").readLines()
int result1 = getLastAccBeforeLoop(input)
println("Last Value before loop was $result1")

int result2 = lastAccWithFixOnTheFly(input)
println("Last Value in the fixed run was $result2")

/**
 * <p>Performs the given Instructions until the first loop and returns the acc value.</p>
 */
static int getLastAccBeforeLoop(List<String> input) {
    BootRunner bootRunner = new BootRunner()

    bootRunner.run(input)
    return bootRunner.getAcc()
}

/**
 * <p>Bruteforces the given instructions with fixing one jmp or nop in each run.
 * Returns the acc value for the valid run.</p>
 */
static int lastAccWithFixOnTheFly(List<String> input) {
    for (int i = 0; i < input.size(); i++) {
        if (!(input[i].startsWith(JUMP_OPERATION) || input[i].startsWith(NO_OPERATION))) {
            continue
        }
        List<String> modifiedInput = new ArrayList<>(input)
        modifiedInput[i] = switchLineOperation(modifiedInput[i])

        BootRunner bootRunner = new BootRunner()
        bootRunner.run(modifiedInput)

        if (bootRunner.getPointer() == input.size()) {
            return bootRunner.getAcc()
        }
    }
    return -1
}

/**
 * Switches the Operation in given line from jump to no op or from no op to jump
 */
static String switchLineOperation(String line) {
    if (line.startsWith(JUMP_OPERATION)) {
        return line.replace(JUMP_OPERATION, NO_OPERATION)
    } else {
        return line.replace(NO_OPERATION, JUMP_OPERATION)
    }
}
