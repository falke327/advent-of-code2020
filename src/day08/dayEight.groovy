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

static int getLastAccBeforeLoop(List<String> instructions) {
    BootRunner bootRunner = new BootRunner()

    bootRunner.run(instructions)
    return bootRunner.getAcc()
}

/**
 * Bruteforces the given instructions with fixing one jmp or nop in each run.
 *
 * @return the acc value for the valid run.<br/>-1 if no valid run has been found
 */
static int lastAccWithFixOnTheFly(List<String> instructions) {
    for (int i = 0; i < instructions.size(); i++) {
        if (!(instructions[i].startsWith(JUMP_OPERATION) || instructions[i].startsWith(NO_OPERATION))) {
            continue
        }
        List<String> modifiedInstructions = new ArrayList<>(instructions)
        modifiedInstructions[i] = switchLineOperation(modifiedInstructions[i])

        BootRunner bootRunner = new BootRunner()
        bootRunner.run(modifiedInstructions)

        if (bootRunner.getPointer() == instructions.size()) {
            return bootRunner.getAcc()
        }
    }
    return -1
}

static String switchLineOperation(String line) {
    if (line.startsWith(JUMP_OPERATION)) {
        return line.replace(JUMP_OPERATION, NO_OPERATION)
    } else {
        return line.replace(NO_OPERATION, JUMP_OPERATION)
    }
}
